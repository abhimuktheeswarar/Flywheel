//[flywheel](../../../index.md)/[com.msabhi.flywheel.utilities](../index.md)/[MutableStateChecker](index.md)/[onStateChanged](on-state-changed.md)



# onStateChanged  
[common]  
Content  
fun [onStateChanged](on-state-changed.md)(newState: [S](index.md))  
More info  


Should be called whenever state changes. This validates that the hashcode of each state instance does not change between when it is first set and when the next state is set. If it does change it means different state instances share some mutable data structure.

  



