//[flywheel](../../../index.md)/[com.msabhi.flywheel](../index.md)/[StateReserve](index.md)/[states](states.md)



# states  
[common]  
Content  
val [states](states.md): Flow<[S](index.md)>  
More info  


Returns a Flow for this StateReserve's state. It will begin by immediately emitting the latest set value and then continue with all subsequent updates. This flow never completes.

  



