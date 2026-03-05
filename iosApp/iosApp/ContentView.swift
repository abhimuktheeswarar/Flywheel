import SwiftUI
import Foundation
import flywheel

struct ContentView: View {

    @StateObject private var viewModel = CounterViewModel()

    var body: some View {
        VStack(spacing: 24) {
            HStack {
                Spacer()
                Button("DECREMENT") { viewModel.decrement() }
                Spacer()
                Text("\(viewModel.state.count)")
                    .font(.system(size: 44, weight: .bold))
                Spacer()
                Button("INCREMENT") { viewModel.increment() }
                Spacer()
            }

            HStack(spacing: 16) {
                Button("RESET") { viewModel.reset() }
                Button("FORCE 7") { viewModel.forceUpdate(count: 7) }
            }

            Button("SHOW TOAST") { viewModel.showToastNow() }

            VStack(alignment: .leading, spacing: 8) {
                Text("Time taken")
                Text(viewModel.lastDispatchTimeText)
                Text("Average time taken")
                Text(viewModel.averageDispatchTimeText)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding()
        .alert("Flywheel", isPresented: $viewModel.isAlertPresented) {
            Button("OK", role: .cancel) { }
        } message: {
            Text(viewModel.alertMessage)
        }
        .onDisappear {
            viewModel.onCleared()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

final class CounterState: Equatable, Hashable {

    let count: Int

    init(count: Int = 0) {
        self.count = count
    }

    static func == (lhs: CounterState, rhs: CounterState) -> Bool {
        return lhs.count == rhs.count
    }

    func hash(into hasher: inout Hasher) {
        hasher.combine(count)
    }
}

final class CounterIncrementAction: Action { }
final class CounterDecrementAction: Action { }
final class CounterResetAction: Action { }
final class CounterForceUpdateAction: Action {
    let count: Int

    init(count: Int) {
        self.count = count
    }
}

final class ShowToastAction: EventAction, SkipReducer {
    let message: String

    init(message: String) {
        self.message = message
    }
}


@MainActor
final class CounterViewModel: ObservableObject {

    private let stateReserveHolder: StateReserveHolder<CounterState>
    private let sideEffect: CounterSideEffect

    private var statesTask: Task<Void, Never>?
    private var actionsTask: Task<Void, Never>?
    private var dispatchStartTime: Date?
    private var dispatchTimes: [TimeInterval] = []

    @Published private(set) var state: CounterState = CounterState()
    @Published private(set) var lastDispatchTimeText = "NA"
    @Published private(set) var averageDispatchTimeText = "NA"
    @Published var isAlertPresented = false
    @Published var alertMessage = ""

    init() {
        stateReserveHolder = StateReserveHolder(initialState: CounterState(), reduce: CounterViewModel.reduce, config: nil)
        sideEffect = CounterSideEffect(
            stateReserve: stateReserveHolder.stateReserve,
            dispatchers: DispatcherProviderImpl()
        )

        statesTask = consumeFlow(stateReserveHolder.states, as: CounterState.self) { [weak self] counterState in
            guard let self else { return }
            self.state = counterState
            self.updateDispatchTimingIfNeeded()
        }

        actionsTask = consumeFlow(stateReserveHolder.actions, as: Action.self) { [weak self] action in
            guard let self else { return }
            if let toastAction = action as? ShowToastAction {
                self.presentToast(toastAction.message)
            }
        }
    }

    deinit {
        stateReserveHolder.onCleared()
    }

    static func reduce(action: Action, currentState: CounterState) -> CounterState {
        switch action {
        case _ as CounterIncrementAction:
            return CounterState(count: currentState.count + 1)
        case _ as CounterDecrementAction:
            return CounterState(count: currentState.count - 1)
        case _ as CounterResetAction:
            return CounterState(count: 0)
        case let forceUpdate as CounterForceUpdateAction:
            return CounterState(count: forceUpdate.count)
        default:
            return currentState
        }
    }

    private func dispatch(action: Action) {
        stateReserveHolder.dispatch(action: action)
    }

    private func dispatchWithTiming(_ action: Action) {
        dispatchStartTime = Date()
        dispatch(action: action)
    }

    private func updateDispatchTimingIfNeeded() {
        guard let dispatchStartTime else { return }
        let elapsed = Date().timeIntervalSince(dispatchStartTime) * 1000
        dispatchTimes.append(elapsed)
        lastDispatchTimeText = String(format: "%.2fms", elapsed)
        averageDispatchTimeText = String(format: "%.2fms", dispatchTimes.reduce(0, +) / Double(dispatchTimes.count))
        self.dispatchStartTime = nil
    }

    private func presentToast(_ message: String) {
        alertMessage = message
        isAlertPresented = true
    }

    func increment() {
        dispatchWithTiming(CounterIncrementAction())
    }

    func decrement() {
        dispatchWithTiming(CounterDecrementAction())
    }

    func reset() {
        dispatchStartTime = nil
        dispatchTimes.removeAll()
        lastDispatchTimeText = "NA"
        averageDispatchTimeText = "NA"

        if state.count == 0 {
            dispatch(action: ShowToastAction(message: "Counter value is already 0"))
            return
        }
        dispatch(action: CounterResetAction())
    }

    func forceUpdate(count: Int) {
        dispatch(action: CounterForceUpdateAction(count: count))
    }

    func showToastNow() {
        dispatch(action: ShowToastAction(message: "\(Date().timeIntervalSince1970)"))
    }

    func onCleared() {
        statesTask?.cancel()
        actionsTask?.cancel()
        statesTask = nil
        actionsTask = nil
        stateReserveHolder.onCleared()
    }
}


final class CounterSideEffect: SideEffect<CounterState> {

    override init(stateReserve: StateReserve<CounterState>, dispatchers: DispatcherProvider) {
        super.init(stateReserve: stateReserve, dispatchers: dispatchers)
        stateReserve.actionStates.collect(collector: TypedCollector<ActionStateAlways<Action, CounterState>>(onValue: { actionState in
            self.handle(action: actionState.action, state: actionState.state)
        }, onError: { error in
            debugPrint("CounterSideEffect collection error: \(error)")
        })) { _ in
        }
    }

    func handle(action: Action, state: CounterState) {
        if action is CounterResetAction, state.count == 0 {
            self.dispatch(action: ShowToastAction(message: "Reset complete"))
        }
    }
}
