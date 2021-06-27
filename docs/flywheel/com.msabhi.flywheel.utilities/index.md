//[flywheel](../../index.md)/[com.msabhi.flywheel.utilities](index.md)



# Package com.msabhi.flywheel.utilities  


## Types  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel.utilities/MutableStateChecker///PointingToDeclaration/"></a>[MutableStateChecker](-mutable-state-checker/index.md)| <a name="com.msabhi.flywheel.utilities/MutableStateChecker///PointingToDeclaration/"></a>[common]  <br>Content  <br>class [MutableStateChecker](-mutable-state-checker/index.md)<[S](-mutable-state-checker/index.md) : [State](../com.msabhi.flywheel/-state/index.md)>(**initialState**: [S](-mutable-state-checker/index.md))  <br><br><br>|


## Functions  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel.utilities//assertStateValues/#com.msabhi.flywheel.Action#TypeParam(bounds=[com.msabhi.flywheel.State])#kotlin.Function2[com.msabhi.flywheel.Action,TypeParam(bounds=[com.msabhi.flywheel.State]),TypeParam(bounds=[com.msabhi.flywheel.State])]#com.msabhi.flywheel.utilities.MutableStateChecker[TypeParam(bounds=[com.msabhi.flywheel.State])]?/PointingToDeclaration/"></a>[assertStateValues](assert-state-values.md)| <a name="com.msabhi.flywheel.utilities//assertStateValues/#com.msabhi.flywheel.Action#TypeParam(bounds=[com.msabhi.flywheel.State])#kotlin.Function2[com.msabhi.flywheel.Action,TypeParam(bounds=[com.msabhi.flywheel.State]),TypeParam(bounds=[com.msabhi.flywheel.State])]#com.msabhi.flywheel.utilities.MutableStateChecker[TypeParam(bounds=[com.msabhi.flywheel.State])]?/PointingToDeclaration/"></a>[common, android, ios]  <br>Content  <br>[common]  <br>fun <[S](assert-state-values.md) : [State](../com.msabhi.flywheel/-state/index.md)> [assertStateValues](assert-state-values.md)(action: [Action](../com.msabhi.flywheel/-action/index.md), currentState: [S](assert-state-values.md), reduce: [Reduce](../com.msabhi.flywheel/index.md#-87403171%2FClasslikes%2F-2051426397)<[S](assert-state-values.md)>, mutableStateChecker: [MutableStateChecker](-mutable-state-checker/index.md)<[S](assert-state-values.md)>?)  <br>[android, ios]  <br>fun <[S](assert-state-values.md) : [State](../com.msabhi.flywheel/-state/index.md)> [assertStateValues](assert-state-values.md)(action: [Action](../com.msabhi.flywheel/-action/index.md), currentState: [S](assert-state-values.md), reduce: Reduce<[S](assert-state-values.md)>, mutableStateChecker: [MutableStateChecker](-mutable-state-checker/index.md)<[S](assert-state-values.md)>?)  <br><br><br>|
| <a name="com.msabhi.flywheel.utilities//getDefaultScope/#/PointingToDeclaration/"></a>getDefaultScope| <a name="com.msabhi.flywheel.utilities//getDefaultScope/#/PointingToDeclaration/"></a>[android, ios]  <br>Content  <br>[android]  <br>fun [getDefaultScope]([android]get-default-scope.md)(): CoroutineScope  <br>[ios]  <br>fun [getDefaultScope]([ios]get-default-scope.md)(): CoroutineScope  <br><br><br>|
| <a name="com.msabhi.flywheel.utilities//getDefaultStateReserveConfig/#/PointingToDeclaration/"></a>getDefaultStateReserveConfig| <a name="com.msabhi.flywheel.utilities//getDefaultStateReserveConfig/#/PointingToDeclaration/"></a>[ios, android]  <br>Content  <br>[ios]  <br>fun [getDefaultStateReserveConfig](index.md#1611839336%2FFunctions%2F-558535777)(): [StateReserveConfig](../com.msabhi.flywheel/-state-reserve-config/index.md)  <br>[android]  <br>fun [getDefaultStateReserveConfig](get-default-state-reserve-config.md)(scope: CoroutineScope = getDefaultScope()): [StateReserveConfig](../com.msabhi.flywheel/-state-reserve-config/index.md)  <br><br><br>|
| <a name="com.msabhi.flywheel.utilities//name/com.msabhi.flywheel.Action#/PointingToDeclaration/"></a>[name](name.md)| <a name="com.msabhi.flywheel.utilities//name/com.msabhi.flywheel.Action#/PointingToDeclaration/"></a>[common]  <br>Content  <br>fun [Action](../com.msabhi.flywheel/-action/index.md).[name](name.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>|


## Properties  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel.utilities//skipMiddleware/#/PointingToDeclaration/"></a>[skipMiddleware](skip-middleware.md)| <a name="com.msabhi.flywheel.utilities//skipMiddleware/#/PointingToDeclaration/"></a> [common] val [skipMiddleware](skip-middleware.md): [Middleware](../com.msabhi.flywheel/index.md#-695516751%2FClasslikes%2F-2051426397)<[State](../com.msabhi.flywheel/-state/index.md)>   <br>|

