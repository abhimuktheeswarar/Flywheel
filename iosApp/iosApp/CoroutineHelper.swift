//
//  CoroutineHelper.swift
//  iosApp
//
//  Created by Abhi Muktheeswarar on 27/11/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Combine
import Foundation
import flywheel

typealias OnEach<Output> = (Output) -> Void
typealias OnCompletion<Failure> = (Failure?) -> Void

typealias OnCollect<Output, Failure> = (@escaping OnEach<Output>, @escaping OnCompletion<Failure>) -> flywheel.Cancellable

/**
 Creates a `Publisher` that collects output from a flow wrapper function emitting values from an underlying
 instance of `Flow<T>`.
 */
func collect<Output, Failure>(_ onCollect: @escaping OnCollect<Output, Failure>) -> Publishers.Flow<Output, Failure> {
    return Publishers.Flow(onCollect: onCollect)
}

typealias OnCollect1<T1, Output, Failure> = (T1, @escaping OnEach<Output>, @escaping OnCompletion<Failure>) -> flywheel.Cancellable

/**
 Creates a `Publisher` that collects output from a flow wrapper function emitting values from an underlying
 instance of `Flow<T>`.
 */
func collect<T1, Output, Failure>(_ onCollect: @escaping OnCollect1<T1, Output, Failure>, with arg1: T1) -> Publishers.Flow<Output, Failure> {
    return Publishers.Flow { onCollect(arg1, $0, $1) }
}

/**
 Wraps a KMM `Cancellable` in a Combine `Subscription`
 */
class SharedCancellableSubscription: Subscription {
    private var isCancelled: Bool = false

    var cancellable: flywheel
        .Cancellable? {
        didSet {
            if isCancelled {
                cancellable?.cancel()
            }
        }
    }

    func request(_ demand: Subscribers.Demand) {
        // Not supported
    }

    func cancel() {
        isCancelled = true
        cancellable?.cancel()
    }
}

extension Publishers {
    class Flow<Output, Failure: Error>: Publisher {
        private let onCollect: OnCollect<Output, Failure>

        init(onCollect: @escaping OnCollect<Output, Failure>) {
            self.onCollect = onCollect
        }

        func receive<S>(subscriber: S) where S: Subscriber, Failure == S.Failure, Output == S.Input {
            let subscription = SharedCancellableSubscription()
            subscriber.receive(subscription: subscription)

            let cancellable = onCollect({ input in _ = subscriber.receive(input) }) { failure in
                if let failure = failure {
                    subscriber.receive(completion: .failure(failure))
                } else {
                    subscriber.receive(completion: .finished)
                }
            }

            subscription.cancellable = cancellable
        }
    }
}

class Collector<T>: Kotlinx_coroutines_coreFlowCollector {

    let callback:(T) -> Void

    init(callback: @escaping (T) -> Void) {
        self.callback = callback
    }


    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        // do whatever you what with the emitted value
        callback(value as! T)

        // after you finished your work you need to call completionHandler to
        // tell that you consumed the value and the next value can be consumed,
        // otherwise you will not receive the next value
        //
        // i think first parameter can be always nil or KotlinUnit()
        // second parameter is for an error which occurred while consuming the value
        // passing an error object will throw a NSGenericException in kotlin code, which can be handled or your app will crash
        completionHandler(nil)
    }
}
