//[flywheel](../../../index.md)/[com.msabhi.flywheel.attachments](../index.md)/[[ios]FlywheelViewModel](index.md)



# FlywheelViewModel  
 [ios] open class [FlywheelViewModel](index.md)<[S](index.md) : [State](../../com.msabhi.flywheel/-state/index.md)>(**initialState**: [S](index.md), **reduce**: Reduce<[S](index.md)>?, **config**: [StateReserveConfig](../../com.msabhi.flywheel/-state-reserve-config/index.md))

Please note: [FlywheelViewModel](index.md) is provided for convenience. Flywheel's StateReserve can be used without [FlywheelViewModel](index.md) using your own ViewModel or with any other architecture patterns.

   


## Constructors  
  
| | |
|---|---|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/FlywheelViewModel/#TypeParam(bounds=[com.msabhi.flywheel.State])#kotlin.Function2[com.msabhi.flywheel.Action,TypeParam(bounds=[com.msabhi.flywheel.State]),TypeParam(bounds=[com.msabhi.flywheel.State])]?#com.msabhi.flywheel.StateReserveConfig/PointingToDeclaration/"></a>[FlywheelViewModel](-flywheel-view-model.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/FlywheelViewModel/#TypeParam(bounds=[com.msabhi.flywheel.State])#kotlin.Function2[com.msabhi.flywheel.Action,TypeParam(bounds=[com.msabhi.flywheel.State]),TypeParam(bounds=[com.msabhi.flywheel.State])]?#com.msabhi.flywheel.StateReserveConfig/PointingToDeclaration/"></a> [ios] fun <[S](index.md) : [State](../../com.msabhi.flywheel/-state/index.md)> [FlywheelViewModel](-flywheel-view-model.md)(initialState: [S](index.md), reduce: Reduce<[S](index.md)>? = null, config: [StateReserveConfig](../../com.msabhi.flywheel/-state-reserve-config/index.md) = getDefaultStateReserveConfig())   <br>|


## Functions  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/awaitState/#/PointingToDeclaration/"></a>[awaitState](await-state.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/awaitState/#/PointingToDeclaration/"></a>[ios]  <br>Content  <br>suspend fun [awaitState](await-state.md)(): [S](index.md)  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/dispatch/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[dispatch](dispatch.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/dispatch/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[ios]  <br>Content  <br>fun [dispatch](dispatch.md)(action: [Action](../../com.msabhi.flywheel/-action/index.md))  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/onCleared/#/PointingToDeclaration/"></a>[onCleared](on-cleared.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/onCleared/#/PointingToDeclaration/"></a>[ios]  <br>Content  <br>fun [onCleared](on-cleared.md)()  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/state/#/PointingToDeclaration/"></a>[state](state.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/state/#/PointingToDeclaration/"></a>[ios]  <br>Content  <br>fun [state](state.md)(): [S](index.md)  <br><br><br>|


## Properties  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/eventActions/#/PointingToDeclaration/"></a>[eventActions](event-actions.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/eventActions/#/PointingToDeclaration/"></a> [ios] val [eventActions](event-actions.md): Flow<[EventAction](../../com.msabhi.flywheel/-event-action/index.md)>   <br>|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/navigateActions/#/PointingToDeclaration/"></a>[navigateActions](navigate-actions.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/navigateActions/#/PointingToDeclaration/"></a> [ios] val [navigateActions](navigate-actions.md): Flow<[NavigateAction](../../com.msabhi.flywheel/-navigate-action/index.md)>   <br>|
| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/states/#/PointingToDeclaration/"></a>[states](states.md)| <a name="com.msabhi.flywheel.attachments/FlywheelViewModel/states/#/PointingToDeclaration/"></a> [ios] val [states](states.md): Flow<[S](index.md)>   <br>|

