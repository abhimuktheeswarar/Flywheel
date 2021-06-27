//[flywheel](../../../index.md)/[com.msabhi.flywheel](../index.md)/[StateReserve](index.md)/[state](state.md)



# state  
[common]  
Content  
fun [state](state.md)(): [S](index.md)  
More info  


Synchronous access to state. Please note, there is no guarantee that the state will be the final expected state. i.e there could be some actions in the queue to update the state. Calling this function will provide the state at that moment, not after all actions have passed through a reducer.

  



