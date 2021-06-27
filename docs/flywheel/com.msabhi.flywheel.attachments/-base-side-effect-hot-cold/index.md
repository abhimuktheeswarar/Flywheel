//[flywheel](../../../index.md)/[com.msabhi.flywheel.attachments](../index.md)/[BaseSideEffectHotCold](index.md)



# BaseSideEffectHotCold  
 [common] abstract class [BaseSideEffectHotCold](index.md)(**stateReserve**: [StateReserve](../../com.msabhi.flywheel/-state-reserve/index.md)<*>, **dispatchers**: [DispatcherProvider](../-dispatcher-provider/index.md))   


## Functions  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/awaitState/#/PointingToDeclaration/"></a>[awaitState](await-state.md)| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/awaitState/#/PointingToDeclaration/"></a>[common]  <br>Content  <br>suspend fun <[S](await-state.md) : [State](../../com.msabhi.flywheel/-state/index.md)> [awaitState](await-state.md)(): [S](await-state.md)  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/dispatch/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[dispatch](dispatch.md)| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/dispatch/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[common]  <br>Content  <br>fun [dispatch](dispatch.md)(action: [Action](../../com.msabhi.flywheel/-action/index.md))  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/handleCold/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[handleCold](handle-cold.md)| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/handleCold/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[common]  <br>Content  <br>abstract fun [handleCold](handle-cold.md)(action: [Action](../../com.msabhi.flywheel/-action/index.md))  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/handleHot/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[handleHot](handle-hot.md)| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/handleHot/#com.msabhi.flywheel.Action/PointingToDeclaration/"></a>[common]  <br>Content  <br>abstract fun [handleHot](handle-hot.md)(action: [Action](../../com.msabhi.flywheel/-action/index.md))  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/state/#/PointingToDeclaration/"></a>[state](state.md)| <a name="com.msabhi.flywheel.attachments/BaseSideEffectHotCold/state/#/PointingToDeclaration/"></a>[common]  <br>Content  <br>fun <[S](state.md) : [State](../../com.msabhi.flywheel/-state/index.md)> [state](state.md)(): [S](state.md)  <br><br><br>|

