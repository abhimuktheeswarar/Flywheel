//[flywheel](../../../index.md)/[com.msabhi.flywheel](../index.md)/[SkipReducer](index.md)



# SkipReducer  
 [common] interface [SkipReducer](index.md) : [Action](../-action/index.md)

If a action doesn't change the state or no need to pass through a reducer, we can implement [SkipReducer](index.md) to any action. Example: A ShowToastAction doesn't have to pass through a reducer, so it can implement [SkipReducer](index.md).

   

