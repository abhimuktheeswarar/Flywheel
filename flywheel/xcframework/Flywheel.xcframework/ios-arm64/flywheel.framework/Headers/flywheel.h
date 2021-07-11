#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class FlywheelKotlinException, FlywheelStateReserveConfig, FlywheelKotlinUnit, FlywheelMutableStateCheckerStateWrapper<S>, FlywheelStateReserve<S>, FlywheelKotlinx_coroutines_coreCoroutineDispatcher, FlywheelKotlinArray<T>, FlywheelMutableStateChecker<S>, FlywheelKotlinThrowable, FlywheelKotlinRuntimeException, FlywheelKotlinIllegalStateException, FlywheelKotlinAbstractCoroutineContextElement;

@protocol FlywheelAction, FlywheelKotlinx_coroutines_coreFlow, FlywheelKotlinx_coroutines_coreCoroutineScope, FlywheelState, FlywheelDispatcherProvider, FlywheelSideEffect, FlywheelKotlinx_coroutines_coreFlowCollector, FlywheelKotlinCoroutineContext, FlywheelKotlinCoroutineContextKey, FlywheelKotlinCoroutineContextElement, FlywheelKotlinContinuation, FlywheelKotlinContinuationInterceptor, FlywheelKotlinx_coroutines_coreRunnable, FlywheelKotlinIterator;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wincompatible-property-type"
#pragma clang diagnostic ignored "-Wnullability"

__attribute__((swift_name("KotlinBase")))
@interface FlywheelBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end;

@interface FlywheelBase (FlywheelBaseCopying) <NSCopying>
@end;

__attribute__((swift_name("KotlinMutableSet")))
@interface FlywheelMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end;

__attribute__((swift_name("KotlinMutableDictionary")))
@interface FlywheelMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end;

@interface NSError (NSErrorFlywheelKotlinException)
@property (readonly) id _Nullable kotlinException;
@end;

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
@end;

__attribute__((swift_name("KotlinByte")))
@interface FlywheelByte : FlywheelNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end;

__attribute__((swift_name("KotlinUByte")))
@interface FlywheelUByte : FlywheelNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end;

__attribute__((swift_name("KotlinShort")))
@interface FlywheelShort : FlywheelNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end;

__attribute__((swift_name("KotlinUShort")))
@interface FlywheelUShort : FlywheelNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end;

__attribute__((swift_name("KotlinInt")))
@interface FlywheelInt : FlywheelNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end;

__attribute__((swift_name("KotlinUInt")))
@interface FlywheelUInt : FlywheelNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end;

__attribute__((swift_name("KotlinLong")))
@interface FlywheelLong : FlywheelNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end;

__attribute__((swift_name("KotlinULong")))
@interface FlywheelULong : FlywheelNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end;

__attribute__((swift_name("KotlinFloat")))
@interface FlywheelFloat : FlywheelNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end;

__attribute__((swift_name("KotlinDouble")))
@interface FlywheelDouble : FlywheelNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end;

__attribute__((swift_name("KotlinBoolean")))
@interface FlywheelBoolean : FlywheelNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end;

__attribute__((swift_name("Action")))
@protocol FlywheelAction
@required
@end;

__attribute__((swift_name("ErrorAction")))
@protocol FlywheelErrorAction <FlywheelAction>
@required
@property (readonly) FlywheelKotlinException *exception __attribute__((swift_name("exception")));
@end;

__attribute__((swift_name("EventAction")))
@protocol FlywheelEventAction <FlywheelAction>
@required
@end;

__attribute__((swift_name("NavigateAction")))
@protocol FlywheelNavigateAction <FlywheelAction>
@required
@end;

__attribute__((swift_name("SkipReducer")))
@protocol FlywheelSkipReducer <FlywheelAction>
@required
@end;

__attribute__((swift_name("State")))
@protocol FlywheelState
@required
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("StateReserve")))
@interface FlywheelStateReserve<S> : FlywheelBase
- (instancetype)initWithConfig:(FlywheelStateReserveConfig *)config initialState:(S)initialState reduce:(S (^)(id<FlywheelAction>, S))reduce middlewares:(NSArray<FlywheelKotlinUnit *(^(^(^)(FlywheelKotlinUnit *(^)(id<FlywheelAction>), S (^)(void)))(FlywheelKotlinUnit *(^)(id<FlywheelAction>)))(id<FlywheelAction>)> * _Nullable)middlewares __attribute__((swift_name("init(config:initialState:reduce:middlewares:)"))) __attribute__((objc_designated_initializer));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)awaitStateWithCompletionHandler:(void (^)(S _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("awaitState(completionHandler:)")));
- (void)dispatchAction:(id<FlywheelAction>)action __attribute__((swift_name("dispatch(action:)")));
- (S)state __attribute__((swift_name("state()")));
- (void)terminate __attribute__((swift_name("terminate()")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> coldActions __attribute__((swift_name("coldActions")));
@property (readonly) FlywheelStateReserveConfig *config __attribute__((swift_name("config")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> hotActions __attribute__((swift_name("hotActions")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> states __attribute__((swift_name("states")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("StateReserveConfig")))
@interface FlywheelStateReserveConfig : FlywheelBase
- (instancetype)initWithScope:(id<FlywheelKotlinx_coroutines_coreCoroutineScope>)scope debugMode:(BOOL)debugMode ignoreDuplicateState:(BOOL)ignoreDuplicateState assertStateValues:(BOOL)assertStateValues checkMutableState:(BOOL)checkMutableState __attribute__((swift_name("init(scope:debugMode:ignoreDuplicateState:assertStateValues:checkMutableState:)"))) __attribute__((objc_designated_initializer));
@property (readonly) BOOL assertStateValues __attribute__((swift_name("assertStateValues")));
@property (readonly) BOOL checkMutableState __attribute__((swift_name("checkMutableState")));
@property (readonly) BOOL debugMode __attribute__((swift_name("debugMode")));
@property (readonly) BOOL ignoreDuplicateState __attribute__((swift_name("ignoreDuplicateState")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@end;

__attribute__((swift_name("Cancellable")))
@protocol FlywheelCancellable
@required
- (void)cancel __attribute__((swift_name("cancel()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MutableStateChecker")))
@interface FlywheelMutableStateChecker<S> : FlywheelBase
- (instancetype)initWithInitialState:(S)initialState __attribute__((swift_name("init(initialState:)"))) __attribute__((objc_designated_initializer));
- (void)onStateChangedNewState:(S)newState __attribute__((swift_name("onStateChanged(newState:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MutableStateCheckerStateWrapper")))
@interface FlywheelMutableStateCheckerStateWrapper<S> : FlywheelBase
- (instancetype)initWithState:(S)state __attribute__((swift_name("init(state:)"))) __attribute__((objc_designated_initializer));
- (S)component1 __attribute__((swift_name("component1()")));
- (FlywheelMutableStateCheckerStateWrapper<S> *)doCopyState:(S)state __attribute__((swift_name("doCopy(state:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
- (void)validate __attribute__((swift_name("validate()")));
@property (readonly) S state __attribute__((swift_name("state")));
@end;

__attribute__((swift_name("StateType")))
@protocol FlywheelStateType <FlywheelState>
@required
@end;

__attribute__((swift_name("BaseMiddleware")))
@interface FlywheelBaseMiddleware<S> : FlywheelBase
- (instancetype)initWithScope:(id<FlywheelKotlinx_coroutines_coreCoroutineScope>)scope dispatcherProvider:(id<FlywheelDispatcherProvider>)dispatcherProvider __attribute__((swift_name("init(scope:dispatcherProvider:)"))) __attribute__((objc_designated_initializer));
- (FlywheelKotlinUnit *(^(^(^)(FlywheelKotlinUnit *(^)(id<FlywheelAction>), S (^)(void)))(FlywheelKotlinUnit *(^)(id<FlywheelAction>)))(id<FlywheelAction>))get __attribute__((swift_name("get()")));
- (void)handleAction:(id<FlywheelAction>)action state:(S (^)(void))state next:(void (^)(id<FlywheelAction>))next dispatch:(void (^)(id<FlywheelAction>))dispatch __attribute__((swift_name("handle(action:state:next:dispatch:)")));
@property (readonly) NSString *TAG __attribute__((swift_name("TAG")));
@property (readonly) id<FlywheelDispatcherProvider> dispatcherProvider __attribute__((swift_name("dispatcherProvider")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@end;

__attribute__((swift_name("BaseReducer")))
@protocol FlywheelBaseReducer
@required
- (id<FlywheelState>)reduceAction:(id<FlywheelAction>)action state:(id<FlywheelState>)state __attribute__((swift_name("reduce(action:state:)")));
@end;

__attribute__((swift_name("SideEffect")))
@protocol FlywheelSideEffect
@required
- (void)handleAction:(id<FlywheelAction>)action __attribute__((swift_name("handle(action:)")));
@end;

__attribute__((swift_name("BaseSideEffectCold")))
@interface FlywheelBaseSideEffectCold : FlywheelBase <FlywheelSideEffect>
- (instancetype)initWithStateReserve:(FlywheelStateReserve<id> *)stateReserve dispatchers:(id<FlywheelDispatcherProvider>)dispatchers __attribute__((swift_name("init(stateReserve:dispatchers:)"))) __attribute__((objc_designated_initializer));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)awaitStateWithCompletionHandler:(void (^)(id<FlywheelState> _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("awaitState(completionHandler:)")));
- (void)dispatchAction:(id<FlywheelAction>)action __attribute__((swift_name("dispatch(action:)")));
- (id<FlywheelState>)state __attribute__((swift_name("state()")));
@property (readonly) NSString *TAG __attribute__((swift_name("TAG")));
@property (readonly) id<FlywheelDispatcherProvider> dispatchers __attribute__((swift_name("dispatchers")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@end;

__attribute__((swift_name("BaseSideEffectHot")))
@interface FlywheelBaseSideEffectHot : FlywheelBase <FlywheelSideEffect>
- (instancetype)initWithStateReserve:(FlywheelStateReserve<id> *)stateReserve dispatchers:(id<FlywheelDispatcherProvider>)dispatchers __attribute__((swift_name("init(stateReserve:dispatchers:)"))) __attribute__((objc_designated_initializer));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)awaitStateWithCompletionHandler:(void (^)(id<FlywheelState> _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("awaitState(completionHandler:)")));
- (void)dispatchAction:(id<FlywheelAction>)action __attribute__((swift_name("dispatch(action:)")));
- (id<FlywheelState>)state __attribute__((swift_name("state()")));
@property (readonly) NSString *TAG __attribute__((swift_name("TAG")));
@property (readonly) id<FlywheelDispatcherProvider> dispatchers __attribute__((swift_name("dispatchers")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@end;

__attribute__((swift_name("BaseSideEffectHotCold")))
@interface FlywheelBaseSideEffectHotCold : FlywheelBase
- (instancetype)initWithStateReserve:(FlywheelStateReserve<id> *)stateReserve dispatchers:(id<FlywheelDispatcherProvider>)dispatchers __attribute__((swift_name("init(stateReserve:dispatchers:)"))) __attribute__((objc_designated_initializer));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)awaitStateWithCompletionHandler:(void (^)(id<FlywheelState> _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("awaitState(completionHandler:)")));
- (void)dispatchAction:(id<FlywheelAction>)action __attribute__((swift_name("dispatch(action:)")));
- (void)handleColdAction:(id<FlywheelAction>)action __attribute__((swift_name("handleCold(action:)")));
- (void)handleHotAction:(id<FlywheelAction>)action __attribute__((swift_name("handleHot(action:)")));
- (id<FlywheelState>)state __attribute__((swift_name("state()")));
@property (readonly) NSString *TAG __attribute__((swift_name("TAG")));
@property (readonly) id<FlywheelDispatcherProvider> dispatchers __attribute__((swift_name("dispatchers")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@end;

__attribute__((swift_name("DispatcherProvider")))
@protocol FlywheelDispatcherProvider
@required
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Default __attribute__((swift_name("Default")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *IO __attribute__((swift_name("IO")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Main __attribute__((swift_name("Main")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Unconfined __attribute__((swift_name("Unconfined")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DispatcherProviderImpl")))
@interface FlywheelDispatcherProviderImpl : FlywheelBase <FlywheelDispatcherProvider>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)dispatcherProviderImpl __attribute__((swift_name("init()")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Default __attribute__((swift_name("Default")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *IO __attribute__((swift_name("IO")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Main __attribute__((swift_name("Main")));
@property (readonly) FlywheelKotlinx_coroutines_coreCoroutineDispatcher *Unconfined __attribute__((swift_name("Unconfined")));
@end;

__attribute__((swift_name("StateReserveHolder")))
@interface FlywheelStateReserveHolder<S> : FlywheelBase
- (instancetype)initWithInitialState:(S)initialState reduce:(S (^)(id<FlywheelAction>, S))reduce config:(FlywheelStateReserveConfig * _Nullable)config __attribute__((swift_name("init(initialState:reduce:config:)"))) __attribute__((objc_designated_initializer));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)awaitStateWithCompletionHandler:(void (^)(S _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("awaitState(completionHandler:)")));
- (void)dispatchAction:(id<FlywheelAction>)action __attribute__((swift_name("dispatch(action:)")));
- (void)onCleared __attribute__((swift_name("onCleared()")));
- (S)state __attribute__((swift_name("state()")));
@property (readonly) NSString *TAG __attribute__((swift_name("TAG")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> coldActions __attribute__((swift_name("coldActions")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> eventActions __attribute__((swift_name("eventActions")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> hotActions __attribute__((swift_name("hotActions")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> navigateActions __attribute__((swift_name("navigateActions")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreCoroutineScope> scope __attribute__((swift_name("scope")));
@property (readonly) FlywheelStateReserve<S> *stateReserve __attribute__((swift_name("stateReserve")));
@property (readonly) id<FlywheelKotlinx_coroutines_coreFlow> states __attribute__((swift_name("states")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("FlywheelKt")))
@interface FlywheelFlywheelKt : FlywheelBase
+ (id<FlywheelState> (^)(id<FlywheelAction>, id<FlywheelState>))combineReducersReducers:(FlywheelKotlinArray<id<FlywheelState> (^)(id<FlywheelAction>, id<FlywheelState>)> *)reducers __attribute__((swift_name("combineReducers(reducers:)")));
+ (id _Nullable (^)(id<FlywheelAction>, id _Nullable))reducerForActionReducer:(id _Nullable (^)(id<FlywheelAction>, id _Nullable))reducer __attribute__((swift_name("reducerForAction(reducer:)")));
+ (id _Nullable (^)(id<FlywheelAction>, id _Nullable))plus:(id _Nullable (^)(id<FlywheelAction>, id _Nullable))receiver other:(id _Nullable (^)(id<FlywheelAction>, id _Nullable))other __attribute__((swift_name("plus(_:other:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("FlywheelUtilitiesKt")))
@interface FlywheelFlywheelUtilitiesKt : FlywheelBase
+ (id<FlywheelKotlinx_coroutines_coreCoroutineScope>)getDefaultScope __attribute__((swift_name("getDefaultScope()")));
+ (FlywheelStateReserveConfig *)getDefaultStateReserveConfigScope:(id<FlywheelKotlinx_coroutines_coreCoroutineScope>)scope debugMode:(BOOL)debugMode __attribute__((swift_name("getDefaultStateReserveConfig(scope:debugMode:)")));
+ (id<FlywheelKotlinx_coroutines_coreCoroutineScope>)getMainScope __attribute__((swift_name("getMainScope()")));
+ (NSString *)name:(id<FlywheelAction>)receiver __attribute__((swift_name("name(_:)")));
@property (class, readonly) FlywheelKotlinUnit *(^(^(^skipMiddleware)(FlywheelKotlinUnit *(^)(id<FlywheelAction>), id<FlywheelState> (^)(void)))(FlywheelKotlinUnit *(^)(id<FlywheelAction>)))(id<FlywheelAction>) __attribute__((swift_name("skipMiddleware")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ValidationKt")))
@interface FlywheelValidationKt : FlywheelBase
+ (void)assertStateValuesAction:(id<FlywheelAction>)action currentState:(id<FlywheelState>)currentState reduce:(id<FlywheelState> (^)(id<FlywheelAction>, id<FlywheelState>))reduce mutableStateChecker:(FlywheelMutableStateChecker<id<FlywheelState>> * _Nullable)mutableStateChecker __attribute__((swift_name("assertStateValues(action:currentState:reduce:mutableStateChecker:)")));
@end;

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
@end;

__attribute__((swift_name("KotlinException")))
@interface FlywheelKotlinException : FlywheelKotlinThrowable
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinUnit")))
@interface FlywheelKotlinUnit : FlywheelBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)unit __attribute__((swift_name("init()")));
- (NSString *)description __attribute__((swift_name("description()")));
@end;

__attribute__((swift_name("KotlinRuntimeException")))
@interface FlywheelKotlinRuntimeException : FlywheelKotlinException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinIllegalStateException")))
@interface FlywheelKotlinIllegalStateException : FlywheelKotlinRuntimeException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinCancellationException")))
@interface FlywheelKotlinCancellationException : FlywheelKotlinIllegalStateException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(FlywheelKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreFlow")))
@protocol FlywheelKotlinx_coroutines_coreFlow
@required

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)collectCollector:(id<FlywheelKotlinx_coroutines_coreFlowCollector>)collector completionHandler:(void (^)(FlywheelKotlinUnit * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("collect(collector:completionHandler:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineScope")))
@protocol FlywheelKotlinx_coroutines_coreCoroutineScope
@required
@property (readonly) id<FlywheelKotlinCoroutineContext> coroutineContext __attribute__((swift_name("coroutineContext")));
@end;

__attribute__((swift_name("KotlinCoroutineContext")))
@protocol FlywheelKotlinCoroutineContext
@required
- (id _Nullable)foldInitial:(id _Nullable)initial operation:(id _Nullable (^)(id _Nullable, id<FlywheelKotlinCoroutineContextElement>))operation __attribute__((swift_name("fold(initial:operation:)")));
- (id<FlywheelKotlinCoroutineContextElement> _Nullable)getKey:(id<FlywheelKotlinCoroutineContextKey>)key __attribute__((swift_name("get(key:)")));
- (id<FlywheelKotlinCoroutineContext>)minusKeyKey:(id<FlywheelKotlinCoroutineContextKey>)key __attribute__((swift_name("minusKey(key:)")));
- (id<FlywheelKotlinCoroutineContext>)plusContext:(id<FlywheelKotlinCoroutineContext>)context __attribute__((swift_name("plus(context:)")));
@end;

__attribute__((swift_name("KotlinCoroutineContextElement")))
@protocol FlywheelKotlinCoroutineContextElement <FlywheelKotlinCoroutineContext>
@required
@property (readonly) id<FlywheelKotlinCoroutineContextKey> key __attribute__((swift_name("key")));
@end;

__attribute__((swift_name("KotlinAbstractCoroutineContextElement")))
@interface FlywheelKotlinAbstractCoroutineContextElement : FlywheelBase <FlywheelKotlinCoroutineContextElement>
- (instancetype)initWithKey:(id<FlywheelKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer));
@property (readonly) id<FlywheelKotlinCoroutineContextKey> key __attribute__((swift_name("key")));
@end;

__attribute__((swift_name("KotlinContinuationInterceptor")))
@protocol FlywheelKotlinContinuationInterceptor <FlywheelKotlinCoroutineContextElement>
@required
- (id<FlywheelKotlinContinuation>)interceptContinuationContinuation:(id<FlywheelKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (void)releaseInterceptedContinuationContinuation:(id<FlywheelKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineDispatcher")))
@interface FlywheelKotlinx_coroutines_coreCoroutineDispatcher : FlywheelKotlinAbstractCoroutineContextElement <FlywheelKotlinContinuationInterceptor>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithKey:(id<FlywheelKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (void)dispatchContext:(id<FlywheelKotlinCoroutineContext>)context block:(id<FlywheelKotlinx_coroutines_coreRunnable>)block __attribute__((swift_name("dispatch(context:block:)")));
- (void)dispatchYieldContext:(id<FlywheelKotlinCoroutineContext>)context block:(id<FlywheelKotlinx_coroutines_coreRunnable>)block __attribute__((swift_name("dispatchYield(context:block:)")));
- (id<FlywheelKotlinContinuation>)interceptContinuationContinuation:(id<FlywheelKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (BOOL)isDispatchNeededContext:(id<FlywheelKotlinCoroutineContext>)context __attribute__((swift_name("isDispatchNeeded(context:)")));
- (FlywheelKotlinx_coroutines_coreCoroutineDispatcher *)plusOther:(FlywheelKotlinx_coroutines_coreCoroutineDispatcher *)other __attribute__((swift_name("plus(other:)"))) __attribute__((unavailable("Operator '+' on two CoroutineDispatcher objects is meaningless. CoroutineDispatcher is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The dispatcher to the right of `+` just replaces the dispatcher to the left.")));
- (void)releaseInterceptedContinuationContinuation:(id<FlywheelKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@end;

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
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreFlowCollector")))
@protocol FlywheelKotlinx_coroutines_coreFlowCollector
@required

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)emitValue:(id _Nullable)value completionHandler:(void (^)(FlywheelKotlinUnit * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("emit(value:completionHandler:)")));
@end;

__attribute__((swift_name("KotlinCoroutineContextKey")))
@protocol FlywheelKotlinCoroutineContextKey
@required
@end;

__attribute__((swift_name("KotlinContinuation")))
@protocol FlywheelKotlinContinuation
@required
- (void)resumeWithResult:(id _Nullable)result __attribute__((swift_name("resumeWith(result:)")));
@property (readonly) id<FlywheelKotlinCoroutineContext> context __attribute__((swift_name("context")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreRunnable")))
@protocol FlywheelKotlinx_coroutines_coreRunnable
@required
- (void)run __attribute__((swift_name("run()")));
@end;

__attribute__((swift_name("KotlinIterator")))
@protocol FlywheelKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end;

#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
