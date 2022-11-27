#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class FlywheelActionStateAlways<__covariant A, __covariant S>, FlywheelActionStateOnEnter<__covariant A, __covariant S>, FlywheelActionStateOnExit<__covariant A, __covariant S>, FlywheelKotlinException, FlywheelInitialStateCompanion, FlywheelInitialState<S>, FlywheelStateReserveConfig, FlywheelKotlinUnit, FlywheelTransitionInValid<__covariant A, __covariant S>, FlywheelTransitionNothing<__covariant A, __covariant S>, FlywheelTransitionValid<__covariant A, __covariant FS, __covariant TS>, FlywheelKotlinThrowable, FlywheelKotlinArray<T>, FlywheelMutableStateCheckerStateWrapper<S>, FlywheelKotlinx_coroutines_coreCoroutineDispatcher, FlywheelDispatcherProviderImpl, FlywheelStateReserve<S>, FlywheelMutableStateChecker<S>, FlywheelKotlinCancellationException, FlywheelKotlinRuntimeException, FlywheelKotlinIllegalStateException, FlywheelKotlinAbstractCoroutineContextElement, FlywheelKotlinx_coroutines_coreCoroutineDispatcherKey, FlywheelKotlinAbstractCoroutineContextKey<B, E>, FlywheelKotlinx_coroutines_coreAtomicDesc, FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp, FlywheelKotlinx_coroutines_coreAtomicOp<__contravariant T>, FlywheelKotlinx_coroutines_coreOpDescriptor, FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode, FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc, FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeAddLastDesc<T>, FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeRemoveFirstDesc<T>;

@protocol FlywheelAction, FlywheelState, FlywheelActionState, FlywheelKotlinx_coroutines_coreCompletableDeferred, FlywheelKotlinx_coroutines_coreFlow, FlywheelKotlinx_coroutines_coreCoroutineScope, FlywheelTransition, FlywheelDispatcherProvider, FlywheelKotlinSuspendFunction1, FlywheelKotlinx_coroutines_coreSelectClause1, FlywheelKotlinx_coroutines_coreChildHandle, FlywheelKotlinx_coroutines_coreChildJob, FlywheelKotlinx_coroutines_coreDisposableHandle, FlywheelKotlinx_coroutines_coreJob, FlywheelKotlinSequence, FlywheelKotlinx_coroutines_coreSelectClause0, FlywheelKotlinCoroutineContextKey, FlywheelKotlinCoroutineContextElement, FlywheelKotlinCoroutineContext, FlywheelKotlinx_coroutines_coreDeferred, FlywheelKotlinx_coroutines_coreFlowCollector, FlywheelKotlinIterator, FlywheelKotlinContinuation, FlywheelKotlinContinuationInterceptor, FlywheelKotlinx_coroutines_coreRunnable, FlywheelKotlinFunction, FlywheelKotlinx_coroutines_coreSelectInstance, FlywheelKotlinx_coroutines_coreParentJob, FlywheelKotlinSuspendFunction0;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wincompatible-property-type"
#pragma clang diagnostic ignored "-Wnullability"

#pragma push_macro("_Nullable_result")
#if !__has_feature(nullability_nullable_result)
#undef _Nullable_result
#define _Nullable_result _Nullable
#endif

__attribute__((swift_name("KotlinBase")))
@interface FlywheelBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end

@interface FlywheelBase (FlywheelBaseCopying) <NSCopying>
@end

__attribute__((swift_name("KotlinMutableSet")))
@interface FlywheelMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end

__attribute__((swift_name("KotlinMutableDictionary")))
@interface FlywheelMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end

@interface NSError (NSErrorFlywheelKotlinException)
@property (readonly) id _Nullable kotlinException;
@end

__attribute__((swift_name("KotlinNumber")))
@interface FlywheelNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end

__attribute__((swift_name("KotlinByte")))
@interface FlywheelByte : FlywheelNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end

__attribute__((swift_name("KotlinUByte")))
@interface FlywheelUByte : FlywheelNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end

__attribute__((swift_name("KotlinShort")))
@interface FlywheelShort : FlywheelNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end

__attribute__((swift_name("KotlinUShort")))
@interface FlywheelUShort : FlywheelNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end

__attribute__((swift_name("KotlinInt")))
@interface FlywheelInt : FlywheelNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end

__attribute__((swift_name("KotlinUInt")))
@interface FlywheelUInt : FlywheelNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end

__attribute__((swift_name("KotlinLong")))
@interface FlywheelLong : FlywheelNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end

__attribute__((swift_name("KotlinULong")))
@interface FlywheelULong : FlywheelNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end

__attribute__((swift_name("KotlinFloat")))
@interface FlywheelFloat : FlywheelNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end

__attribute__((swift_name("KotlinDouble")))
@interface FlywheelDouble : FlywheelNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end

__attribute__((swift_name("KotlinBoolean")))
@interface FlywheelBoolean : FlywheelNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end

__attribute__((swift_name("Action")))
@protocol FlywheelAction
@required
@end

__attribute__((swift_name("ActionState")))
@protocol FlywheelActionState
@required
@property (readonly) id<FlywheelAction> action __attribute__((swift_name("action")));
@property (readonly) id<FlywheelState> state __attribute__((swift_name("state")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ActionStateAlways")))
@interface FlywheelActionStateAlways<__covariant A, __covariant S> : FlywheelBase <FlywheelActionState>
- (instancetype)initWithAction:(A)action state:(S)state __attribute__((swift_name("init(action:state:)"))) __attribute__((objc_designated_initializer));
- (A)component1 __attribute__((swift_name("component1()"))) __attribute__((deprecated("use corresponding property instead")));
- (S)component2 __attribute__((swift_name("component2()"))) __attribute__((deprecated("use corresponding property instead")));
- (FlywheelActionStateAlways<A, S> *)doCopyAction:(A)action state:(S)state __attribute__((swift_name("doCopy(action:state:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) A action __attribute__((swift_name("action")));
@property (readonly) S state __attribute__((swift_name("state")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ActionStateOnEnter")))
@interface FlywheelActionStateOnEnter<__covariant A, __covariant S> : FlywheelBase <FlywheelActionState>
- (instancetype)initWithAction:(A)action state:(S)state __attribute__((swift_name("init(action:state:)"))) __attribute__((objc_designated_initializer));
- (A)component1 __attribute__((swift_name("component1()"))) __attribute__((deprecated("use corresponding property instead")));
- (S)component2 __attribute__((swift_name("component2()"))) __attribute__((deprecated("use corresponding property instead")));
- (FlywheelActionStateOnEnter<A, S> *)doCopyAction:(A)action state:(S)state __attribute__((swift_name("doCopy(action:state:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) A action __attribute__((swift_name("action")));
@property (readonly) S state __attribute__((swift_name("state")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ActionStateOnExit")))
@interface FlywheelActionStateOnExit<__covariant A, __covariant S> : FlywheelBase <FlywheelActionState>
- (instancetype)initWithAction:(A)action state:(S)state __attribute__((swift_name("init(action:state:)"))) __attribute__((objc_designated_initializer));
- (A)component1 __attribute__((swift_name("component1()"))) __attribute__((deprecated("use corresponding property instead")));
- (S)component2 __attribute__((swift_name("component2()"))) __attribute__((deprecated("use corresponding property instead")));
- (FlywheelActionStateOnExit<A, S> *)doCopyAction:(A)action state:(S)state __attribute__((swift_name("doCopy(action:state:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) A action __attribute__((swift_name("action")));
@property (readonly) S state __attribute__((swift_name("state")));
@end

__attribute__((swift_name("ErrorAction")))
@protocol FlywheelErrorAction <FlywheelAction>
@required
@property (readonly) FlywheelKotlinException *exception __attribute__((swift_name("exception")));
@end

__attribute__((swift_name("EventAction")))
@protocol FlywheelEventAction <FlywheelAction>
@required
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("InitialState")))
@interface FlywheelInitialState<S> : FlywheelBase
@property (class, readonly, getter=companion) FlywheelInitialStateCompanion *companion __attribute__((swift_name("companion")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCompletableDeferred> _Nullable deferredState __attribute__((swift_name("deferredState")));
@property (readonly) S _Nullable state __attribute__((swift_name("state")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("InitialStateCompanion")))
@interface FlywheelInitialStateCompanion : FlywheelBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) FlywheelInitialStateCompanion *shared __attribute__((swift_name("shared")));
- (FlywheelInitialState<id<FlywheelState>> *)deferredSet __attribute__((swift_name("deferredSet()")));
- (FlywheelInitialState<id<FlywheelState>> *)setState:(id<FlywheelState>)state __attribute__((swift_name("set(state:)")));
@end

__attribute__((swift_name("NavigateAction")))
@protocol FlywheelNavigateAction <FlywheelAction>
@required
@end

__attribute__((swift_name("SkipReducer")))
@protocol FlywheelSkipReducer <FlywheelAction>
@required
@end

__attribute__((swift_name("State")))
@protocol FlywheelState
@required
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("StateReserve")))
@interface FlywheelStateReserve<S> : FlywheelBase
- (instancetype)initWithConfig:(FlywheelStateReserveConfig *)config initialState:(FlywheelInitialState<S> *)initialState reduce:(S (^)(id<FlywheelAction>, S))reduce middlewares:(NSArray<FlywheelKotlinUnit *(^(^(^)(FlywheelKotlinUnit *(^)(id<FlywheelAction>), S (^)(void)))(FlywheelKotlinUnit *(^)(id<FlywheelAction>)))(id<FlywheelAction>)> * _Nullable)middlewares __attribute__((swift_name("init(config:initialState:reduce:middlewares:)"))) __attribute__((objc_designated_initializer));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)awaitStateWithCompletionHandler:(void (^)(S _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("awaitState(completionHandler:)")));
- (void)dispatchAction:(id<FlywheelAction>)action __attribute__((swift_name("dispatch(action:)")));
- (BOOL)restoreStateState:(S)state __attribute__((swift_name("restoreState(state:)")));
- (S)state __attribute__((swift_name("state()")));
- (void)terminate __attribute__((swift_name("terminate()")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> actionStates __attribute__((swift_name("actionStates")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> actions __attribute__((swift_name("actions")));
@property (readonly) FlywheelStateReserveConfig *config __attribute__((swift_name("config")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> states __attribute__((swift_name("states")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> transitions __attribute__((swift_name("transitions")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("StateReserveConfig")))
@interface FlywheelStateReserveConfig : FlywheelBase
- (instancetype)initWithScope:(id<FlywheelKotlinx_coroutines_coreCoroutineScope>)scope debugMode:(BOOL)debugMode ignoreDuplicateState:(BOOL)ignoreDuplicateState enhancedStateMachine:(BOOL)enhancedStateMachine assertStateValues:(BOOL)assertStateValues checkMutableState:(BOOL)checkMutableState __attribute__((swift_name("init(scope:debugMode:ignoreDuplicateState:enhancedStateMachine:assertStateValues:checkMutableState:)"))) __attribute__((objc_designated_initializer));
@property (readonly) BOOL assertStateValues __attribute__((swift_name("assertStateValues")));
@property (readonly) BOOL checkMutableState __attribute__((swift_name("checkMutableState")));
@property (readonly) BOOL debugMode __attribute__((swift_name("debugMode")));
@property (readonly) BOOL enhancedStateMachine __attribute__((swift_name("enhancedStateMachine")));
@property (readonly) BOOL ignoreDuplicateState __attribute__((swift_name("ignoreDuplicateState")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@end

__attribute__((swift_name("Transition")))
@protocol FlywheelTransition
@required
@property (readonly) id<FlywheelAction> action __attribute__((swift_name("action")));
@property (readonly) id<FlywheelState> fromState __attribute__((swift_name("fromState")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("TransitionInValid")))
@interface FlywheelTransitionInValid<__covariant A, __covariant S> : FlywheelBase <FlywheelTransition>
- (instancetype)initWithAction:(A)action fromState:(S)fromState exception:(FlywheelKotlinException *)exception __attribute__((swift_name("init(action:fromState:exception:)"))) __attribute__((objc_designated_initializer));
- (A)component1 __attribute__((swift_name("component1()"))) __attribute__((deprecated("use corresponding property instead")));
- (S)component2 __attribute__((swift_name("component2()"))) __attribute__((deprecated("use corresponding property instead")));
- (FlywheelKotlinException *)component3 __attribute__((swift_name("component3()"))) __attribute__((deprecated("use corresponding property instead")));
- (FlywheelTransitionInValid<A, S> *)doCopyAction:(A)action fromState:(S)fromState exception:(FlywheelKotlinException *)exception __attribute__((swift_name("doCopy(action:fromState:exception:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) A action __attribute__((swift_name("action")));
@property (readonly) FlywheelKotlinException *exception __attribute__((swift_name("exception")));
@property (readonly) S fromState __attribute__((swift_name("fromState")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("TransitionNothing")))
@interface FlywheelTransitionNothing<__covariant A, __covariant S> : FlywheelBase <FlywheelTransition>
- (instancetype)initWithAction:(A)action fromState:(S)fromState __attribute__((swift_name("init(action:fromState:)"))) __attribute__((objc_designated_initializer));
- (A)component1 __attribute__((swift_name("component1()"))) __attribute__((deprecated("use corresponding property instead")));
- (S)component2 __attribute__((swift_name("component2()"))) __attribute__((deprecated("use corresponding property instead")));
- (FlywheelTransitionNothing<A, S> *)doCopyAction:(A)action fromState:(S)fromState __attribute__((swift_name("doCopy(action:fromState:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) A action __attribute__((swift_name("action")));
@property (readonly) S fromState __attribute__((swift_name("fromState")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("TransitionValid")))
@interface FlywheelTransitionValid<__covariant A, __covariant FS, __covariant TS> : FlywheelBase <FlywheelTransition>
- (instancetype)initWithAction:(A)action fromState:(FS)fromState toState:(TS)toState __attribute__((swift_name("init(action:fromState:toState:)"))) __attribute__((objc_designated_initializer));
- (A)component1 __attribute__((swift_name("component1()"))) __attribute__((deprecated("use corresponding property instead")));
- (FS)component2 __attribute__((swift_name("component2()"))) __attribute__((deprecated("use corresponding property instead")));
- (TS)component3 __attribute__((swift_name("component3()"))) __attribute__((deprecated("use corresponding property instead")));
- (FlywheelTransitionValid<A, FS, TS> *)doCopyAction:(A)action fromState:(FS)fromState toState:(TS)toState __attribute__((swift_name("doCopy(action:fromState:toState:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) A action __attribute__((swift_name("action")));
@property (readonly) FS fromState __attribute__((swift_name("fromState")));
@property (readonly) TS toState __attribute__((swift_name("toState")));
@end

__attribute__((swift_name("KotlinThrowable")))
@interface FlywheelKotlinThrowable : FlywheelBase
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (FlywheelKotlinArray<NSString *> *)getStackTrace __attribute__((swift_name("getStackTrace()")));
- (void)printStackTrace __attribute__((swift_name("printStackTrace()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) FlywheelKotlinThrowable * _Nullable cause __attribute__((swift_name("cause")));
@property (readonly) NSString * _Nullable message __attribute__((swift_name("message")));
- (NSError *)asError __attribute__((swift_name("asError()")));
@end

__attribute__((swift_name("KotlinException")))
@interface FlywheelKotlinException : FlywheelKotlinThrowable
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UnsupportedStateTransition")))
@interface FlywheelUnsupportedStateTransition : FlywheelKotlinException
- (instancetype)initWithMsg:(NSString *)msg __attribute__((swift_name("init(msg:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (readonly) NSString *msg __attribute__((swift_name("msg")));
@end

__attribute__((swift_name("Cancellable")))
@protocol FlywheelCancellable
@required
- (void)cancel __attribute__((swift_name("cancel()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MutableStateChecker")))
@interface FlywheelMutableStateChecker<S> : FlywheelBase
- (instancetype)initWithInitialState:(S)initialState __attribute__((swift_name("init(initialState:)"))) __attribute__((objc_designated_initializer));
- (void)onStateChangedNewState:(S)newState __attribute__((swift_name("onStateChanged(newState:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MutableStateCheckerStateWrapper")))
@interface FlywheelMutableStateCheckerStateWrapper<S> : FlywheelBase
- (instancetype)initWithState:(S)state __attribute__((swift_name("init(state:)"))) __attribute__((objc_designated_initializer));
- (S)component1 __attribute__((swift_name("component1()"))) __attribute__((deprecated("use corresponding property instead")));
- (FlywheelMutableStateCheckerStateWrapper<S> *)doCopyState:(S)state __attribute__((swift_name("doCopy(state:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
- (void)validate __attribute__((swift_name("validate()")));
@property (readonly) S state __attribute__((swift_name("state")));
@end

__attribute__((swift_name("StateType")))
@protocol FlywheelStateType <FlywheelState>
@required
@end

__attribute__((swift_name("BaseMiddleware")))
@interface FlywheelBaseMiddleware<S> : FlywheelBase
- (instancetype)initWithScope:(id<FlywheelKotlinx_coroutines_coreCoroutineScope>)scope dispatcherProvider:(id<FlywheelDispatcherProvider>)dispatcherProvider __attribute__((swift_name("init(scope:dispatcherProvider:)"))) __attribute__((objc_designated_initializer));
- (FlywheelKotlinUnit *(^(^(^)(FlywheelKotlinUnit *(^)(id<FlywheelAction>), S (^)(void)))(FlywheelKotlinUnit *(^)(id<FlywheelAction>)))(id<FlywheelAction>))get __attribute__((swift_name("get()")));
- (void)handleAction:(id<FlywheelAction>)action state:(S (^)(void))state next:(void (^)(id<FlywheelAction>))next dispatch:(void (^)(id<FlywheelAction>))dispatch __attribute__((swift_name("handle(action:state:next:dispatch:)")));
@property (readonly) NSString *TAG __attribute__((swift_name("TAG")));
@property (readonly) id<FlywheelDispatcherProvider> dispatcherProvider __attribute__((swift_name("dispatcherProvider")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@end

__attribute__((swift_name("BaseReducer")))
@protocol FlywheelBaseReducer
@required
- (id<FlywheelState>)reduceAction:(id<FlywheelAction>)action state:(id<FlywheelState>)state __attribute__((swift_name("reduce(action:state:)")));
@end

__attribute__((swift_name("DispatcherProvider")))
@protocol FlywheelDispatcherProvider
@required
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Default __attribute__((swift_name("Default")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *IO __attribute__((swift_name("IO")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Main __attribute__((swift_name("Main")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Unconfined __attribute__((swift_name("Unconfined")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DispatcherProviderImpl")))
@interface FlywheelDispatcherProviderImpl : FlywheelBase <FlywheelDispatcherProvider>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)dispatcherProviderImpl __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) FlywheelDispatcherProviderImpl *shared __attribute__((swift_name("shared")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Default __attribute__((swift_name("Default")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *IO __attribute__((swift_name("IO")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Main __attribute__((swift_name("Main")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Unconfined __attribute__((swift_name("Unconfined")));
@end

__attribute__((swift_name("SideEffect")))
@interface FlywheelSideEffect<S> : FlywheelBase
- (instancetype)initWithStateReserve:(FlywheelStateReserve<S> *)stateReserve dispatchers:(id<FlywheelDispatcherProvider>)dispatchers __attribute__((swift_name("init(stateReserve:dispatchers:)"))) __attribute__((objc_designated_initializer));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)awaitStateWithCompletionHandler:(void (^)(S _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("awaitState(completionHandler:)")));
- (void)dispatchAction:(id<FlywheelAction>)action __attribute__((swift_name("dispatch(action:)")));
- (S)state_ __attribute__((swift_name("state()")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> actionStates __attribute__((swift_name("actionStates")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> actions __attribute__((swift_name("actions")));
@property (readonly) id<FlywheelDispatcherProvider> dispatchers __attribute__((swift_name("dispatchers")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> transitions __attribute__((swift_name("transitions")));
@end

__attribute__((swift_name("StateReserveHolder")))
@interface FlywheelStateReserveHolder<S> : FlywheelBase
- (instancetype)initWithInitialState:(S)initialState reduce:(S (^)(id<FlywheelAction>, S))reduce config:(FlywheelStateReserveConfig * _Nullable)config __attribute__((swift_name("init(initialState:reduce:config:)"))) __attribute__((objc_designated_initializer));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)awaitStateWithCompletionHandler:(void (^)(S _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("awaitState(completionHandler:)")));
- (void)dispatchAction:(id<FlywheelAction>)action __attribute__((swift_name("dispatch(action:)")));
- (void)onCleared __attribute__((swift_name("onCleared()")));
- (S)state_ __attribute__((swift_name("state()")));
@property (readonly) NSString *TAG __attribute__((swift_name("TAG")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> actionStates __attribute__((swift_name("actionStates")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> actions __attribute__((swift_name("actions")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> eventActions __attribute__((swift_name("eventActions")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> navigateActions __attribute__((swift_name("navigateActions")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@property (readonly) FlywheelStateReserve<S> *stateReserve __attribute__((swift_name("stateReserve")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> states __attribute__((swift_name("states")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("FlywheelKt")))
@interface FlywheelFlywheelKt : FlywheelBase
+ (id<FlywheelState> (^)(id<FlywheelAction>, id<FlywheelState>))combineReducersReducers:(FlywheelKotlinArray<id<FlywheelState> (^)(id<FlywheelAction>, id<FlywheelState>)> *)reducers __attribute__((swift_name("combineReducers(reducers:)")));
+ (void)reduceError __attribute__((swift_name("reduceError()")));
+ (id<FlywheelState> (^)(id<FlywheelAction>, id<FlywheelState>))reducerForActionReducer:(id<FlywheelState> (^)(id<FlywheelAction>, id<FlywheelState>))reducer __attribute__((swift_name("reducerForAction(reducer:)")));
+ (id<FlywheelKotlinx_coroutines_coreFlow>)inValidTransition:(id<FlywheelKotlinx_coroutines_coreFlow>)receiver __attribute__((swift_name("inValidTransition(_:)")));
+ (id<FlywheelKotlinx_coroutines_coreFlow>)inValidTransitionWithAction:(id<FlywheelKotlinx_coroutines_coreFlow>)receiver __attribute__((swift_name("inValidTransitionWithAction(_:)")));
+ (id<FlywheelKotlinx_coroutines_coreFlow>)onEnter:(id<FlywheelKotlinx_coroutines_coreFlow>)receiver __attribute__((swift_name("onEnter(_:)")));
+ (id<FlywheelKotlinx_coroutines_coreFlow>)onExit:(id<FlywheelKotlinx_coroutines_coreFlow>)receiver __attribute__((swift_name("onExit(_:)")));
+ (id<FlywheelKotlinx_coroutines_coreFlow>)onlyActions:(id<FlywheelKotlinx_coroutines_coreFlow>)receiver __attribute__((swift_name("onlyActions(_:)")));
+ (id _Nullable (^)(id<FlywheelAction>, id _Nullable))plus:(id _Nullable (^)(id<FlywheelAction>, id _Nullable))receiver other:(id _Nullable (^)(id<FlywheelAction>, id _Nullable))other __attribute__((swift_name("plus(_:other:)")));
+ (id<FlywheelKotlinx_coroutines_coreFlow>)specificActions:(id<FlywheelKotlinx_coroutines_coreFlow>)receiver __attribute__((swift_name("specificActions(_:)")));
+ (id<FlywheelKotlinx_coroutines_coreFlow>)specificStates:(id<FlywheelKotlinx_coroutines_coreFlow>)receiver transform:(id<FlywheelKotlinSuspendFunction1>)transform __attribute__((swift_name("specificStates(_:transform:)")));
+ (id<FlywheelKotlinx_coroutines_coreFlow>)validTransitionWithAction:(id<FlywheelKotlinx_coroutines_coreFlow>)receiver __attribute__((swift_name("validTransitionWithAction(_:)")));
+ (id<FlywheelKotlinx_coroutines_coreFlow>)validTransitions:(id<FlywheelKotlinx_coroutines_coreFlow>)receiver __attribute__((swift_name("validTransitions(_:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("FlywheelUtilitiesKt")))
@interface FlywheelFlywheelUtilitiesKt : FlywheelBase
+ (id<FlywheelKotlinx_coroutines_coreCoroutineScope>)getDefaultScope __attribute__((swift_name("getDefaultScope()")));
+ (FlywheelStateReserveConfig *)getDefaultStateReserveConfigScope:(id<FlywheelKotlinx_coroutines_coreCoroutineScope>)scope debugMode:(BOOL)debugMode __attribute__((swift_name("getDefaultStateReserveConfig(scope:debugMode:)")));
+ (id<FlywheelKotlinx_coroutines_coreCoroutineScope>)getMainScope __attribute__((swift_name("getMainScope()")));
+ (NSString *)name:(id<FlywheelAction>)receiver __attribute__((swift_name("name(_:)")));
@property (class, readonly) FlywheelKotlinUnit *(^(^(^skipMiddleware)(FlywheelKotlinUnit *(^)(id<FlywheelAction>), id<FlywheelState> (^)(void)))(FlywheelKotlinUnit *(^)(id<FlywheelAction>)))(id<FlywheelAction>) __attribute__((swift_name("skipMiddleware")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ValidationKt")))
@interface FlywheelValidationKt : FlywheelBase
+ (void)assertStateValuesAction:(id<FlywheelAction>)action currentState:(id<FlywheelState>)currentState reduce:(id<FlywheelState> (^)(id<FlywheelAction>, id<FlywheelState>))reduce mutableStateChecker:(FlywheelMutableStateChecker<id<FlywheelState>> * _Nullable)mutableStateChecker __attribute__((swift_name("assertStateValues(action:currentState:reduce:mutableStateChecker:)")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
*/
__attribute__((swift_name("KotlinCoroutineContext")))
@protocol FlywheelKotlinCoroutineContext
@required
- (id _Nullable)foldInitial:(id _Nullable)initial operation:(id _Nullable (^)(id _Nullable, id<FlywheelKotlinCoroutineContextElement>))operation __attribute__((swift_name("fold(initial:operation:)")));
- (id<FlywheelKotlinCoroutineContextElement> _Nullable)getKey:(id<FlywheelKotlinCoroutineContextKey>)key __attribute__((swift_name("get(key:)")));
- (id<FlywheelKotlinCoroutineContext>)minusKeyKey:(id<FlywheelKotlinCoroutineContextKey>)key __attribute__((swift_name("minusKey(key:)")));
- (id<FlywheelKotlinCoroutineContext>)plusContext:(id<FlywheelKotlinCoroutineContext>)context __attribute__((swift_name("plus(context:)")));
@end

__attribute__((swift_name("KotlinCoroutineContextElement")))
@protocol FlywheelKotlinCoroutineContextElement <FlywheelKotlinCoroutineContext>
@required
@property (readonly) id<FlywheelKotlinCoroutineContextKey> key __attribute__((swift_name("key")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreJob")))
@protocol FlywheelKotlinx_coroutines_coreJob <FlywheelKotlinCoroutineContextElement>
@required
- (id<FlywheelKotlinx_coroutines_coreChildHandle>)attachChildChild:(id<FlywheelKotlinx_coroutines_coreChildJob>)child __attribute__((swift_name("attachChild(child:)")));
- (void)cancelCause:(FlywheelKotlinCancellationException * _Nullable)cause __attribute__((swift_name("cancel(cause:)")));
- (FlywheelKotlinCancellationException *)getCancellationException __attribute__((swift_name("getCancellationException()")));
- (id<FlywheelKotlinx_coroutines_coreDisposableHandle>)invokeOnCompletionOnCancelling:(BOOL)onCancelling invokeImmediately:(BOOL)invokeImmediately handler:(void (^)(FlywheelKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(onCancelling:invokeImmediately:handler:)")));
- (id<FlywheelKotlinx_coroutines_coreDisposableHandle>)invokeOnCompletionHandler:(void (^)(FlywheelKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(handler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)joinWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("join(completionHandler:)")));
- (id<FlywheelKotlinx_coroutines_coreJob>)plusOther:(id<FlywheelKotlinx_coroutines_coreJob>)other __attribute__((swift_name("plus(other:)"))) __attribute__((unavailable("Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")));
- (BOOL)start __attribute__((swift_name("start()")));
@property (readonly) id<FlywheelKotlinSequence> children __attribute__((swift_name("children")));
@property (readonly) BOOL isActive __attribute__((swift_name("isActive")));
@property (readonly) BOOL isCancelled __attribute__((swift_name("isCancelled")));
@property (readonly) BOOL isCompleted __attribute__((swift_name("isCompleted")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreSelectClause0> onJoin __attribute__((swift_name("onJoin")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreDeferred")))
@protocol FlywheelKotlinx_coroutines_coreDeferred <FlywheelKotlinx_coroutines_coreJob>
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)awaitWithCompletionHandler:(void (^)(id _Nullable_result, NSError * _Nullable))completionHandler __attribute__((swift_name("await(completionHandler:)")));

/**
 * @note annotations
 *   kotlinx.coroutines.ExperimentalCoroutinesApi
*/
- (id _Nullable)getCompleted __attribute__((swift_name("getCompleted()")));

/**
 * @note annotations
 *   kotlinx.coroutines.ExperimentalCoroutinesApi
*/
- (FlywheelKotlinThrowable * _Nullable)getCompletionExceptionOrNull __attribute__((swift_name("getCompletionExceptionOrNull()")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreSelectClause1> onAwait __attribute__((swift_name("onAwait")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreCompletableDeferred")))
@protocol FlywheelKotlinx_coroutines_coreCompletableDeferred <FlywheelKotlinx_coroutines_coreDeferred>
@required
- (BOOL)completeValue:(id _Nullable)value __attribute__((swift_name("complete(value:)")));
- (BOOL)completeExceptionallyException:(FlywheelKotlinThrowable *)exception __attribute__((swift_name("completeExceptionally(exception:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinUnit")))
@interface FlywheelKotlinUnit : FlywheelBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)unit __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) FlywheelKotlinUnit *shared __attribute__((swift_name("shared")));
- (NSString *)description __attribute__((swift_name("description()")));
@end

__attribute__((swift_name("KotlinRuntimeException")))
@interface FlywheelKotlinRuntimeException : FlywheelKotlinException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((swift_name("KotlinIllegalStateException")))
@interface FlywheelKotlinIllegalStateException : FlywheelKotlinRuntimeException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.4")
*/
__attribute__((swift_name("KotlinCancellationException")))
@interface FlywheelKotlinCancellationException : FlywheelKotlinIllegalStateException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreFlow")))
@protocol FlywheelKotlinx_coroutines_coreFlow
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)collectCollector:(id<FlywheelKotlinx_coroutines_coreFlowCollector>)collector completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("collect(collector:completionHandler:)")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineScope")))
@protocol FlywheelKotlinx_coroutines_coreCoroutineScope
@required
@property (readonly) id<FlywheelKotlinCoroutineContext> coroutineContext __attribute__((swift_name("coroutineContext")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface FlywheelKotlinArray<T> : FlywheelBase
+ (instancetype)arrayWithSize:(int32_t)size init:(T _Nullable (^)(FlywheelInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (T _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<FlywheelKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(T _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
*/
__attribute__((swift_name("KotlinAbstractCoroutineContextElement")))
@interface FlywheelKotlinAbstractCoroutineContextElement : FlywheelBase <FlywheelKotlinCoroutineContextElement>
- (instancetype)initWithKey:(id<FlywheelKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer));
@property (readonly) id<FlywheelKotlinCoroutineContextKey> key __attribute__((swift_name("key")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
*/
__attribute__((swift_name("KotlinContinuationInterceptor")))
@protocol FlywheelKotlinContinuationInterceptor <FlywheelKotlinCoroutineContextElement>
@required
- (id<FlywheelKotlinContinuation>)interceptContinuationContinuation:(id<FlywheelKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (void)releaseInterceptedContinuationContinuation:(id<FlywheelKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineDispatcher")))
@interface FlywheelKotlinx_coroutines_coreCoroutineDispatcher : FlywheelKotlinAbstractCoroutineContextElement <FlywheelKotlinContinuationInterceptor>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithKey:(id<FlywheelKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly, getter=companion) FlywheelKotlinx_coroutines_coreCoroutineDispatcherKey *companion __attribute__((swift_name("companion")));
- (void)dispatchContext:(id<FlywheelKotlinCoroutineContext>)context block:(id<FlywheelKotlinx_coroutines_coreRunnable>)block __attribute__((swift_name("dispatch(context:block:)")));
- (void)dispatchYieldContext:(id<FlywheelKotlinCoroutineContext>)context block:(id<FlywheelKotlinx_coroutines_coreRunnable>)block __attribute__((swift_name("dispatchYield(context:block:)")));
- (id<FlywheelKotlinContinuation>)interceptContinuationContinuation:(id<FlywheelKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (BOOL)isDispatchNeededContext:(id<FlywheelKotlinCoroutineContext>)context __attribute__((swift_name("isDispatchNeeded(context:)")));

/**
 * @note annotations
 *   kotlinx.coroutines.ExperimentalCoroutinesApi
*/
- (FlywheelKotlinx_coroutines_coreCoroutineDispatcher *)limitedParallelismParallelism:(int32_t)parallelism __attribute__((swift_name("limitedParallelism(parallelism:)")));
- (FlywheelKotlinx_coroutines_coreCoroutineDispatcher *)plusOther_:(FlywheelKotlinx_coroutines_coreCoroutineDispatcher *)other __attribute__((swift_name("plus(other_:)"))) __attribute__((unavailable("Operator '+' on two CoroutineDispatcher objects is meaningless. CoroutineDispatcher is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The dispatcher to the right of `+` just replaces the dispatcher to the left.")));
- (void)releaseInterceptedContinuationContinuation:(id<FlywheelKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@end

__attribute__((swift_name("KotlinFunction")))
@protocol FlywheelKotlinFunction
@required
@end

__attribute__((swift_name("KotlinSuspendFunction1")))
@protocol FlywheelKotlinSuspendFunction1 <FlywheelKotlinFunction>
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)invokeP1:(id _Nullable)p1 completionHandler:(void (^)(id _Nullable_result, NSError * _Nullable))completionHandler __attribute__((swift_name("invoke(p1:completionHandler:)")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreSelectClause1")))
@protocol FlywheelKotlinx_coroutines_coreSelectClause1
@required
- (void)registerSelectClause1Select:(id<FlywheelKotlinx_coroutines_coreSelectInstance>)select block:(id<FlywheelKotlinSuspendFunction1>)block __attribute__((swift_name("registerSelectClause1(select:block:)")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreDisposableHandle")))
@protocol FlywheelKotlinx_coroutines_coreDisposableHandle
@required
- (void)dispose __attribute__((swift_name("dispose()")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreChildHandle")))
@protocol FlywheelKotlinx_coroutines_coreChildHandle <FlywheelKotlinx_coroutines_coreDisposableHandle>
@required
- (BOOL)childCancelledCause:(FlywheelKotlinThrowable *)cause __attribute__((swift_name("childCancelled(cause:)")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreJob> _Nullable parent __attribute__((swift_name("parent")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreChildJob")))
@protocol FlywheelKotlinx_coroutines_coreChildJob <FlywheelKotlinx_coroutines_coreJob>
@required
- (void)parentCancelledParentJob:(id<FlywheelKotlinx_coroutines_coreParentJob>)parentJob __attribute__((swift_name("parentCancelled(parentJob:)")));
@end

__attribute__((swift_name("KotlinSequence")))
@protocol FlywheelKotlinSequence
@required
- (id<FlywheelKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreSelectClause0")))
@protocol FlywheelKotlinx_coroutines_coreSelectClause0
@required
- (void)registerSelectClause0Select:(id<FlywheelKotlinx_coroutines_coreSelectInstance>)select block:(id<FlywheelKotlinSuspendFunction0>)block __attribute__((swift_name("registerSelectClause0(select:block:)")));
@end

__attribute__((swift_name("KotlinCoroutineContextKey")))
@protocol FlywheelKotlinCoroutineContextKey
@required
@end

__attribute__((swift_name("Kotlinx_coroutines_coreFlowCollector")))
@protocol FlywheelKotlinx_coroutines_coreFlowCollector
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)emitValue:(id _Nullable)value completionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("emit(value:completionHandler:)")));
@end

__attribute__((swift_name("KotlinIterator")))
@protocol FlywheelKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
*/
__attribute__((swift_name("KotlinContinuation")))
@protocol FlywheelKotlinContinuation
@required
- (void)resumeWithResult:(id _Nullable)result __attribute__((swift_name("resumeWith(result:)")));
@property (readonly) id<FlywheelKotlinCoroutineContext> context __attribute__((swift_name("context")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
 *   kotlin.ExperimentalStdlibApi
*/
__attribute__((swift_name("KotlinAbstractCoroutineContextKey")))
@interface FlywheelKotlinAbstractCoroutineContextKey<B, E> : FlywheelBase <FlywheelKotlinCoroutineContextKey>
- (instancetype)initWithBaseKey:(id<FlywheelKotlinCoroutineContextKey>)baseKey safeCast:(E _Nullable (^)(id<FlywheelKotlinCoroutineContextElement>))safeCast __attribute__((swift_name("init(baseKey:safeCast:)"))) __attribute__((objc_designated_initializer));
@end


/**
 * @note annotations
 *   kotlin.ExperimentalStdlibApi
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineDispatcher.Key")))
@interface FlywheelKotlinx_coroutines_coreCoroutineDispatcherKey : FlywheelKotlinAbstractCoroutineContextKey<id<FlywheelKotlinContinuationInterceptor>, FlywheelKotlinx_coroutines_coreCoroutineDispatcher *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithBaseKey:(id<FlywheelKotlinCoroutineContextKey>)baseKey safeCast:(id<FlywheelKotlinCoroutineContextElement> _Nullable (^)(id<FlywheelKotlinCoroutineContextElement>))safeCast __attribute__((swift_name("init(baseKey:safeCast:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)key __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) FlywheelKotlinx_coroutines_coreCoroutineDispatcherKey *shared __attribute__((swift_name("shared")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreRunnable")))
@protocol FlywheelKotlinx_coroutines_coreRunnable
@required
- (void)run __attribute__((swift_name("run()")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreSelectInstance")))
@protocol FlywheelKotlinx_coroutines_coreSelectInstance
@required
- (void)disposeOnSelectHandle:(id<FlywheelKotlinx_coroutines_coreDisposableHandle>)handle __attribute__((swift_name("disposeOnSelect(handle:)")));
- (id _Nullable)performAtomicTrySelectDesc:(FlywheelKotlinx_coroutines_coreAtomicDesc *)desc __attribute__((swift_name("performAtomicTrySelect(desc:)")));
- (void)resumeSelectWithExceptionException:(FlywheelKotlinThrowable *)exception __attribute__((swift_name("resumeSelectWithException(exception:)")));
- (BOOL)trySelect __attribute__((swift_name("trySelect()")));
- (id _Nullable)trySelectOtherOtherOp:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp * _Nullable)otherOp __attribute__((swift_name("trySelectOther(otherOp:)")));
@property (readonly) id<FlywheelKotlinContinuation> completion __attribute__((swift_name("completion")));
@property (readonly) BOOL isSelected __attribute__((swift_name("isSelected")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreParentJob")))
@protocol FlywheelKotlinx_coroutines_coreParentJob <FlywheelKotlinx_coroutines_coreJob>
@required
- (FlywheelKotlinCancellationException *)getChildJobCancellationCause __attribute__((swift_name("getChildJobCancellationCause()")));
@end

__attribute__((swift_name("KotlinSuspendFunction0")))
@protocol FlywheelKotlinSuspendFunction0 <FlywheelKotlinFunction>
@required

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)invokeWithCompletionHandler:(void (^)(id _Nullable_result, NSError * _Nullable))completionHandler __attribute__((swift_name("invoke(completionHandler:)")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreAtomicDesc")))
@interface FlywheelKotlinx_coroutines_coreAtomicDesc : FlywheelBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeOp:(FlywheelKotlinx_coroutines_coreAtomicOp<id> *)op failure:(id _Nullable)failure __attribute__((swift_name("complete(op:failure:)")));
- (id _Nullable)prepareOp:(FlywheelKotlinx_coroutines_coreAtomicOp<id> *)op __attribute__((swift_name("prepare(op:)")));
@property FlywheelKotlinx_coroutines_coreAtomicOp<id> *atomicOp __attribute__((swift_name("atomicOp")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreOpDescriptor")))
@interface FlywheelKotlinx_coroutines_coreOpDescriptor : FlywheelBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (BOOL)isEarlierThanThat:(FlywheelKotlinx_coroutines_coreOpDescriptor *)that __attribute__((swift_name("isEarlierThan(that:)")));
- (id _Nullable)performAffected:(id _Nullable)affected __attribute__((swift_name("perform(affected:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) FlywheelKotlinx_coroutines_coreAtomicOp<id> * _Nullable atomicOp __attribute__((swift_name("atomicOp")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNode.PrepareOp")))
@interface FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp : FlywheelKotlinx_coroutines_coreOpDescriptor
- (instancetype)initWithAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)next desc:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc *)desc __attribute__((swift_name("init(affected:next:desc:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (void)finishPrepare __attribute__((swift_name("finishPrepare()")));
- (id _Nullable)performAffected:(id _Nullable)affected __attribute__((swift_name("perform(affected:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *affected __attribute__((swift_name("affected")));
@property (readonly) FlywheelKotlinx_coroutines_coreAtomicOp<id> *atomicOp __attribute__((swift_name("atomicOp")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc *desc __attribute__((swift_name("desc")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *next __attribute__((swift_name("next")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreAtomicOp")))
@interface FlywheelKotlinx_coroutines_coreAtomicOp<__contravariant T> : FlywheelKotlinx_coroutines_coreOpDescriptor
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeAffected:(T _Nullable)affected failure:(id _Nullable)failure __attribute__((swift_name("complete(affected:failure:)")));
- (id _Nullable)decideDecision:(id _Nullable)decision __attribute__((swift_name("decide(decision:)")));
- (id _Nullable)performAffected:(id _Nullable)affected __attribute__((swift_name("perform(affected:)")));
- (id _Nullable)prepareAffected:(T _Nullable)affected __attribute__((swift_name("prepare(affected:)")));
@property (readonly) FlywheelKotlinx_coroutines_coreAtomicOp<id> *atomicOp __attribute__((swift_name("atomicOp")));
@property (readonly) id _Nullable consensus __attribute__((swift_name("consensus")));
@property (readonly) BOOL isDecided __attribute__((swift_name("isDecided")));
@property (readonly) int64_t opSequence __attribute__((swift_name("opSequence")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNode")))
@interface FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode : FlywheelBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)addLastNode:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)node __attribute__((swift_name("addLast(node:)")));
- (BOOL)addLastIfNode:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)node condition:(FlywheelBoolean *(^)(void))condition __attribute__((swift_name("addLastIf(node:condition:)")));
- (BOOL)addLastIfPrevNode:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)node predicate:(FlywheelBoolean *(^)(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *))predicate __attribute__((swift_name("addLastIfPrev(node:predicate:)")));
- (BOOL)addLastIfPrevAndIfNode:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)node predicate:(FlywheelBoolean *(^)(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *))predicate condition:(FlywheelBoolean *(^)(void))condition __attribute__((swift_name("addLastIfPrevAndIf(node:predicate:condition:)")));
- (BOOL)addOneIfEmptyNode:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)node __attribute__((swift_name("addOneIfEmpty(node:)")));
- (FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeAddLastDesc<FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *> *)describeAddLastNode:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)node __attribute__((swift_name("describeAddLast(node:)")));
- (FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeRemoveFirstDesc<FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *> *)describeRemoveFirst __attribute__((swift_name("describeRemoveFirst()")));
- (void)helpRemove __attribute__((swift_name("helpRemove()")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)nextIfRemoved __attribute__((swift_name("nextIfRemoved()")));
- (BOOL)remove __attribute__((swift_name("remove()")));
- (id _Nullable)removeFirstIfIsInstanceOfOrPeekIfPredicate:(FlywheelBoolean *(^)(id _Nullable))predicate __attribute__((swift_name("removeFirstIfIsInstanceOfOrPeekIf(predicate:)")));
- (FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)removeFirstOrNull __attribute__((swift_name("removeFirstOrNull()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) BOOL isRemoved __attribute__((swift_name("isRemoved")));
@property (readonly, getter=next_) id _Nullable next __attribute__((swift_name("next")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *nextNode __attribute__((swift_name("nextNode")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *prevNode __attribute__((swift_name("prevNode")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNode.AbstractAtomicDesc")))
@interface FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc : FlywheelKotlinx_coroutines_coreAtomicDesc
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeOp:(FlywheelKotlinx_coroutines_coreAtomicOp<id> *)op failure:(id _Nullable)failure __attribute__((swift_name("complete(op:failure:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (id _Nullable)failureAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)affected __attribute__((swift_name("failure(affected:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (void)finishOnSuccessAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("finishOnSuccess(affected:next:)")));
- (void)finishPreparePrepareOp:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp *)prepareOp __attribute__((swift_name("finishPrepare(prepareOp:)")));
- (id _Nullable)onPreparePrepareOp:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp *)prepareOp __attribute__((swift_name("onPrepare(prepareOp:)")));
- (void)onRemovedAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected __attribute__((swift_name("onRemoved(affected:)")));
- (id _Nullable)prepareOp:(FlywheelKotlinx_coroutines_coreAtomicOp<id> *)op __attribute__((swift_name("prepare(op:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (BOOL)retryAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(id)next __attribute__((swift_name("retry(affected:next:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)takeAffectedNodeOp:(FlywheelKotlinx_coroutines_coreOpDescriptor *)op __attribute__((swift_name("takeAffectedNode(op:)")));
- (id)updatedNextAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("updatedNext(affected:next:)")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable affectedNode __attribute__((swift_name("affectedNode")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable originalNext __attribute__((swift_name("originalNext")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNodeAddLastDesc")))
@interface FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeAddLastDesc<T> : FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc
- (instancetype)initWithQueue:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)queue node:(T)node __attribute__((swift_name("init(queue:node:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (void)finishOnSuccessAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("finishOnSuccess(affected:next:)")));
- (void)finishPreparePrepareOp:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp *)prepareOp __attribute__((swift_name("finishPrepare(prepareOp:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (BOOL)retryAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(id)next __attribute__((swift_name("retry(affected:next:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)takeAffectedNodeOp:(FlywheelKotlinx_coroutines_coreOpDescriptor *)op __attribute__((swift_name("takeAffectedNode(op:)")));
- (id)updatedNextAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("updatedNext(affected:next:)")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable affectedNode __attribute__((swift_name("affectedNode")));
@property (readonly) T node __attribute__((swift_name("node")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *originalNext __attribute__((swift_name("originalNext")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *queue __attribute__((swift_name("queue")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNodeRemoveFirstDesc")))
@interface FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeRemoveFirstDesc<T> : FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc
- (instancetype)initWithQueue:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)queue __attribute__((swift_name("init(queue:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (id _Nullable)failureAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)affected __attribute__((swift_name("failure(affected:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (void)finishOnSuccessAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("finishOnSuccess(affected:next:)")));
- (void)finishPreparePrepareOp:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp *)prepareOp __attribute__((swift_name("finishPrepare(prepareOp:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (BOOL)retryAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(id)next __attribute__((swift_name("retry(affected:next:)")));

/**
 * @note This method has protected visibility in Kotlin source and is intended only for use by subclasses.
*/
- (FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)takeAffectedNodeOp:(FlywheelKotlinx_coroutines_coreOpDescriptor *)op __attribute__((swift_name("takeAffectedNode(op:)")));
- (id)updatedNextAffected:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("updatedNext(affected:next:)")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable affectedNode __attribute__((swift_name("affectedNode")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable originalNext __attribute__((swift_name("originalNext")));
@property (readonly) FlywheelKotlinx_coroutines_coreLockFreeLinkedListNode *queue __attribute__((swift_name("queue")));
@property (readonly) T _Nullable result __attribute__((swift_name("result")));
@end

#pragma pop_macro("_Nullable_result")
#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
