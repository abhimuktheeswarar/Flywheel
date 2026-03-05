import Foundation
import flywheel

final class TypedCollector<T>: Kotlinx_coroutines_coreFlowCollector {

    private let onValue: (T) -> Void
    private let onError: (Error) -> Void

    init(
        onValue: @escaping (T) -> Void,
        onError: @escaping (Error) -> Void
    ) {
        self.onValue = onValue
        self.onError = onError
    }

    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        guard let typedValue = value as? T else {
            let error = NSError(
                domain: "flywheel.async.collection",
                code: 1,
                userInfo: [NSLocalizedDescriptionKey: "Unexpected flow value type: \(String(describing: value))"]
            )
            onError(error)
            completionHandler(error)
            return
        }

        onValue(typedValue)
        completionHandler(nil)
    }
}

private final class CollectorRetainer {
    var collector: Kotlinx_coroutines_coreFlowCollector?
}

func flowStream<T>(
    from flow: Kotlinx_coroutines_coreFlow,
    as _: T.Type = T.self
) -> AsyncThrowingStream<T, Error> {
    AsyncThrowingStream { continuation in
        let retainer = CollectorRetainer()
        let collector = TypedCollector<T>(
            onValue: { value in
                continuation.yield(value)
            },
            onError: { error in
                continuation.finish(throwing: error)
            }
        )

        retainer.collector = collector
        flow.collect(collector: collector) { error in
            if let error {
                continuation.finish(throwing: error)
            } else {
                continuation.finish()
            }
            retainer.collector = nil
        }

        continuation.onTermination = { _ in
            retainer.collector = nil
        }
    }
}

@MainActor
func consumeFlow<T>(
    _ flow: Kotlinx_coroutines_coreFlow,
    as _: T.Type = T.self,
    onValue: @escaping (T) -> Void
) -> Task<Void, Never> {
    Task {
        do {
            for try await value in flowStream(from: flow, as: T.self) {
                onValue(value)
            }
        } catch {
            debugPrint("Flow stream ended with error: \(error)")
        }
    }
}
