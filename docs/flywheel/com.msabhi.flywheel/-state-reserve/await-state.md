//[flywheel](../../../index.md)/[com.msabhi.flywheel](../index.md)/[StateReserve](index.md)/[awaitState](await-state.md)



# awaitState  
[common]  
Content  
suspend fun [awaitState](await-state.md)(): [S](index.md)  
More info  


This function is guaranteed to provide the final state after all actions are processed by the stateMachine reducer. So if your code relies on certain state, use this function.

  



