import SwiftUI
import flywheel

struct ContentView: View {
    
    @ObservedObject var viewModel = CounterViewModel()
    
    var body: some View {
        VStack(alignment: .center)    {
            HStack( content: {
                Spacer()
                Button("DECREMENT")  {viewModel.decrement()}
                Spacer()
                Text("\(viewModel.state.count)")
                Spacer()
                Button("INCREMENT")  {viewModel.increment()}
                Spacer()
            })
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

class CounterState : Equatable,Hashable  {
    
    var count :  Int
    
    init() {
        count = 0
    }
    
    static func == (lhs: CounterState, rhs: CounterState) -> Bool {
        debugPrint("equality check")
        return lhs.count == rhs.count
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(count)
    }
}

class CounterIncrementAction : Action {}
class CounterDecrementAction : Action {}
class CounterResetAction : Action {}


class CounterViewModel : ObservableObject {
    
    private lazy var stateReserveHolder = StateReserveHolder(initialState:  CounterState(), reduce: reduce.self, config: nil)
    
    @Published var  state : CounterState = CounterState()
    
    
    init() {
        
        stateReserveHolder.states.collect(collector: Collector<CounterState> { counterState in
            self.state = counterState
            
        }) { (unit, error) in
            // code which is executed if the Flow object completed
        }
        
        stateReserveHolder.coldActions.collect(collector: Collector<Action> { action in
            debugPrint("action = \(action) | state = \(self.state.count)")
            if(self.state.count ==  -10) {
                self.dispatch(action: CounterResetAction())
            }
            
        }) { (unit, error) in
            
        }
        
        CounterSideEffect.init(stateReserve: stateReserveHolder.stateReserve as! StateReserve<AnyObject>, dispatchers: DispatcherProviderImpl.init())
    }
    
    func reduce(action:Action,currentState :CounterState)  -> CounterState {
        let newState = currentState
        
        switch action {
        case _ as CounterIncrementAction:
            newState.count += 1
        case _ as CounterDecrementAction:
            newState.count -= 1
        case _ as CounterResetAction:
            newState.count = 0
        default:
            break
        }
        
        return newState
    }
    
    private func dispatch(action : Action)  {
        stateReserveHolder.dispatch(action: action)
    }
    
    func increment()  {
        dispatch(action: CounterIncrementAction())
    }
    
    func decrement()  {
        dispatch(action: CounterDecrementAction())
    }
}



class CounterSideEffect: BaseSideEffectCold {
    
    override func handle(action: Action) {
        let counterState = state() as! CounterState
        debugPrint("count = \(counterState.count)")
        if(counterState.count ==  10) {
            self.dispatch(action: CounterResetAction())
        }
    }
}
