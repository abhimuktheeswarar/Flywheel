//[flywheel](../../../index.md)/[com.msabhi.flywheel.attachments](../index.md)/[[android]FlywheelViewModel](index.md)



# FlywheelViewModel  
 [android] open class [FlywheelViewModel](index.md)<[S](index.md) : [State](../../com.msabhi.flywheel/-state/index.md)>(**initialState**: [S](index.md), **reduce**: Reduce<[S](index.md)>?, **middlewares**: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<Middleware<[S](index.md)>>?, **stateReserve**: [StateReserve](../../com.msabhi.flywheel/-state-reserve/index.md)<[S](index.md)>?, **scope**: CoroutineScope, **config**: [StateReserveConfig](../../com.msabhi.flywheel/-state-reserve-config/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

Please note: [FlywheelViewModel](index.md) is provided for convenience. Flywheel's StateReserve can be used without [FlywheelViewModel](index.md) using your own ViewModel or with any other architecture patterns.

   


## Constructors  
  
| | |
|---|---|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/FlywheelViewModel/#TypeParam(bounds=[com.msabhi.flywheel.State])#kotlin.Function2[com.msabhi.flywheel.Action,TypeParam(bounds=[com.msabhi.flywheel.State]),TypeParam(bounds=[com.msabhi.flywheel.State])]?#kotlin.collections.List[kotlin.Function2[kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit],kotlin.Function0[TypeParam(bounds=[com.msabhi.flywheel.State])],kotlin.Function1[kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit],kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit]]]]?#com.msabhi.flywheel.StateReserve[TypeParam(bounds=[com.msabhi.flywheel.State])]?#kotlinx.coroutines.CoroutineScope#com.msabhi.flywheel.StateReserveConfig/PointingToDeclaration/"></a>[FlywheelViewModel](-flywheel-view-model.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/FlywheelViewModel/#TypeParam(bounds=[com.msabhi.flywheel.State])#kotlin.Function2[com.msabhi.flywheel.Action,TypeParam(bounds=[com.msabhi.flywheel.State]),TypeParam(bounds=[com.msabhi.flywheel.State])]?#kotlin.collections.List[kotlin.Function2[kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit],kotlin.Function0[TypeParam(bounds=[com.msabhi.flywheel.State])],kotlin.Function1[kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit],kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit]]]]?#com.msabhi.flywheel.StateReserve[TypeParam(bounds=[com.msabhi.flywheel.State])]?#kotlinx.coroutines.CoroutineScope#com.msabhi.flywheel.StateReserveConfig/PointingToDeclaration/"></a> [android] fun <[S](index.md) : [State](../../com.msabhi.flywheel/-state/index.md)> [FlywheelViewModel](-flywheel-view-model.md)(initialState: [S](index.md), reduce: Reduce<[S](index.md)>? = null, middlewares: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<Middleware<[S](index.md)>>? = null, stateReserve: [StateReserve](../../com.msabhi.flywheel/-state-reserve/index.md)<[S](index.md)>? = null, scope: CoroutineScope = getDefaultScope(), config: [StateReserveConfig](../../com.msabhi.flywheel/-state-reserve-config/index.md) = getDefaultStateReserveConfig(scope))   <br>|


## Functions  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/awaitState/#/PointingToDeclaration/"></a>[awaitState](await-state.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/awaitState/#/PointingToDeclaration/"></a>[android]  <br>Content  <br>suspend fun [awaitState](await-state.md)(): [S](index.md)  <br><br><br>|
| <a name="androidx.lifecycle/ViewModel/clear/#/PointingToDeclaration/"></a>[clear](index.md#-1936886459%2FFunctions%2F-1856263721)| <a name="androidx.lifecycle/ViewModel/clear/#/PointingToDeclaration/"></a>[android]  <br>Content  <br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)()  <br>  <br>fun [clear](index.md#-1936886459%2FFunctions%2F-1856263721)()  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/dispatch/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[dispatch](dispatch.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/dispatch/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[android]  <br>Content  <br>fun [dispatch](dispatch.md)(action: [Action](../../com.msabhi.flywheel/-action/index.md))  <br><br><br>|
| <a name="androidx.lifecycle/ViewModel/getTag/#kotlin.String/PointingToDeclaration/"></a>[getTag](index.md#-215894976%2FFunctions%2F-1856263721)| <a name="androidx.lifecycle/ViewModel/getTag/#kotlin.String/PointingToDeclaration/"></a>[android]  <br>Content  <br>open fun <[T](index.md#-215894976%2FFunctions%2F-1856263721) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> [getTag](index.md#-215894976%2FFunctions%2F-1856263721)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [T](index.md#-215894976%2FFunctions%2F-1856263721)  <br><br><br>|
| <a name="androidx.lifecycle/ViewModel/setTagIfAbsent/#kotlin.String#TypeParam(bounds=[kotlin.Any])/PointingToDeclaration/"></a>[setTagIfAbsent](index.md#-1567230750%2FFunctions%2F-1856263721)| <a name="androidx.lifecycle/ViewModel/setTagIfAbsent/#kotlin.String#TypeParam(bounds=[kotlin.Any])/PointingToDeclaration/"></a>[android]  <br>Content  <br>open fun <[T](index.md#-1567230750%2FFunctions%2F-1856263721) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> [setTagIfAbsent](index.md#-1567230750%2FFunctions%2F-1856263721)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), p1: [T](index.md#-1567230750%2FFunctions%2F-1856263721)): [T](index.md#-1567230750%2FFunctions%2F-1856263721)  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/state/#/PointingToDeclaration/"></a>[state](state.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/state/#/PointingToDeclaration/"></a>[android]  <br>Content  <br>fun [state](state.md)(): [S](index.md)  <br><br><br>|


## Properties  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/eventActions/#/PointingToDeclaration/"></a>[eventActions](event-actions.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/eventActions/#/PointingToDeclaration/"></a> [android] val [eventActions](event-actions.md): Flow<[EventAction](../../com.msabhi.flywheel/-event-action/index.md)>   <br>|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/navigateActions/#/PointingToDeclaration/"></a>[navigateActions](navigate-actions.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/navigateActions/#/PointingToDeclaration/"></a> [android] val [navigateActions](navigate-actions.md): Flow<[NavigateAction](../../com.msabhi.flywheel/-navigate-action/index.md)>   <br>|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/states/#/PointingToDeclaration/"></a>[states](states.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/states/#/PointingToDeclaration/"></a> [android] val [states](states.md): Flow<[S](index.md)>   <br>|

