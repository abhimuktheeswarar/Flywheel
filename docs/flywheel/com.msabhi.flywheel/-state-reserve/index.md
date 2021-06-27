//[flywheel](../../../index.md)/[com.msabhi.flywheel](../index.md)/[StateReserve](index.md)



# StateReserve  
 [common] class [StateReserve](index.md)<[S](index.md) : [State](../-state/index.md)>(**config**: [StateReserveConfig](../-state-reserve-config/index.md), **initialState**: [S](index.md), **reduce**: [Reduce](../index.md#-87403171%2FClasslikes%2F-2051426397)<[S](index.md)>, **middlewares**: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Middleware](../index.md#-695516751%2FClasslikes%2F-2051426397)<[S](index.md)>>?)

The [StateReserve](index.md) is a state container which holds the state and orchestrates all the input & outputs for the stateMachine.

   


## Constructors  
  
| | |
|---|---|
| <a name="com.msabhi.flywheel/StateReserve/StateReserve/#com.msabhi.flywheel.StateReserveConfig#TypeParam(bounds=[com.msabhi.flywheel.State])#kotlin.Function2[com.msabhi.flywheel.Action,TypeParam(bounds=[com.msabhi.flywheel.State]),TypeParam(bounds=[com.msabhi.flywheel.State])]#kotlin.collections.List[kotlin.Function2[kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit],kotlin.Function0[TypeParam(bounds=[com.msabhi.flywheel.State])],kotlin.Function1[kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit],kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit]]]]?/PointingToDeclaration/"></a>[StateReserve](-state-reserve.md)| <a name="com.msabhi.flywheel/StateReserve/StateReserve/#com.msabhi.flywheel.StateReserveConfig#TypeParam(bounds=[com.msabhi.flywheel.State])#kotlin.Function2[com.msabhi.flywheel.Action,TypeParam(bounds=[com.msabhi.flywheel.State]),TypeParam(bounds=[com.msabhi.flywheel.State])]#kotlin.collections.List[kotlin.Function2[kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit],kotlin.Function0[TypeParam(bounds=[com.msabhi.flywheel.State])],kotlin.Function1[kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit],kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit]]]]?/PointingToDeclaration/"></a> [common] fun <[S](index.md) : [State](../-state/index.md)> [StateReserve](-state-reserve.md)(config: [StateReserveConfig](../-state-reserve-config/index.md), initialState: [S](index.md), reduce: [Reduce](../index.md#-87403171%2FClasslikes%2F-2051426397)<[S](index.md)>, middlewares: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Middleware](../index.md#-695516751%2FClasslikes%2F-2051426397)<[S](index.md)>>?)   <br>|


## Functions  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel/StateReserve/awaitState/#/PointingToDeclaration/"></a>[awaitState](await-state.md)| <a name="com.msabhi.flywheel/StateReserve/awaitState/#/PointingToDeclaration/"></a>[common]  <br>Content  <br>suspend fun [awaitState](await-state.md)(): [S](index.md)  <br>More info  <br>This function is guaranteed to provide the final state after all actions are processed by the stateMachine reducer.  <br><br><br>|
| <a name="com.msabhi.flywheel/StateReserve/dispatch/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[dispatch](dispatch.md)| <a name="com.msabhi.flywheel/StateReserve/dispatch/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[common]  <br>Content  <br>fun [dispatch](dispatch.md)(action: [Action](../-action/index.md))  <br>More info  <br>It is the entry point for actions to update the StateReserve's state.  <br><br><br>|
| <a name="com.msabhi.flywheel/StateReserve/state/#/PointingToDeclaration/"></a>[state](state.md)| <a name="com.msabhi.flywheel/StateReserve/state/#/PointingToDeclaration/"></a>[common]  <br>Content  <br>fun [state](state.md)(): [S](index.md)  <br>More info  <br>Synchronous access to state.  <br><br><br>|
| <a name="com.msabhi.flywheel/StateReserve/terminate/#/PointingToDeclaration/"></a>[terminate](terminate.md)| <a name="com.msabhi.flywheel/StateReserve/terminate/#/PointingToDeclaration/"></a>[common]  <br>Content  <br>fun [terminate](terminate.md)()  <br>More info  <br>Call this function to cancel the scope, thus cancelling/stopping all operations going on in StateReserve & in the associated SideEffects.  <br><br><br>|


## Properties  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel/StateReserve/coldActions/#/PointingToDeclaration/"></a>[coldActions](cold-actions.md)| <a name="com.msabhi.flywheel/StateReserve/coldActions/#/PointingToDeclaration/"></a> [common] val [coldActions](cold-actions.md): Flow<[Action](../-action/index.md)>Returns a Flow of actions that are passed through reducer.   <br>|
| <a name="com.msabhi.flywheel/StateReserve/config/#/PointingToDeclaration/"></a>[config](config.md)| <a name="com.msabhi.flywheel/StateReserve/config/#/PointingToDeclaration/"></a> [common] val [config](config.md): [StateReserveConfig](../-state-reserve-config/index.md)   <br>|
| <a name="com.msabhi.flywheel/StateReserve/hotActions/#/PointingToDeclaration/"></a>[hotActions](hot-actions.md)| <a name="com.msabhi.flywheel/StateReserve/hotActions/#/PointingToDeclaration/"></a> [common] val [hotActions](hot-actions.md): Flow<[Action](../-action/index.md)>Returns a Flow of actions that are passed through middleware and before reaching reducer.   <br>|
| <a name="com.msabhi.flywheel/StateReserve/states/#/PointingToDeclaration/"></a>[states](states.md)| <a name="com.msabhi.flywheel/StateReserve/states/#/PointingToDeclaration/"></a> [common] val [states](states.md): Flow<[S](index.md)>Returns a Flow for this StateReserve's state.   <br>|

