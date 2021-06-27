//[flywheel](../../../index.md)/[com.msabhi.flywheel.attachments](../index.md)/[BaseMiddleware](index.md)



# BaseMiddleware  
 [common] abstract class [BaseMiddleware](index.md)<[S](index.md) : [State](../../com.msabhi.flywheel/-state/index.md)>(**scope**: CoroutineScope, **dispatcherProvider**: [DispatcherProvider](../-dispatcher-provider/index.md))   


## Functions  
  
|  Name |  Summary | 
|---|---|
| <a name="com.msabhi.flywheel.attachments/BaseMiddleware/get/#/PointingToDeclaration/"></a>[get](get.md)| <a name="com.msabhi.flywheel.attachments/BaseMiddleware/get/#/PointingToDeclaration/"></a>[common]  <br>Content  <br>fun [get](get.md)(): ([Dispatch](../../com.msabhi.flywheel/index.md#1816344649%2FClasslikes%2F-2051426397), [GetState](../../com.msabhi.flywheel/index.md#-1063038712%2FClasslikes%2F-2051426397)<[S](index.md)>) -> ([Dispatch](../../com.msabhi.flywheel/index.md#1816344649%2FClasslikes%2F-2051426397)) -> [Dispatch](../../com.msabhi.flywheel/index.md#1816344649%2FClasslikes%2F-2051426397)  <br><br><br>|
| <a name="com.msabhi.flywheel.attachments/BaseMiddleware/handle/#com.msabhi.flywheel.Action#kotlin.Function0[TypeParam(bounds=[com.msabhi.flywheel.State])]#kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit]#kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit]/PointingToDeclaration/"></a>[handle](handle.md)| <a name="com.msabhi.flywheel.attachments/BaseMiddleware/handle/#com.msabhi.flywheel.Action#kotlin.Function0[TypeParam(bounds=[com.msabhi.flywheel.State])]#kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit]#kotlin.Function1[com.msabhi.flywheel.Action,kotlin.Unit]/PointingToDeclaration/"></a>[common]  <br>Content  <br>abstract fun [handle](handle.md)(action: [Action](../../com.msabhi.flywheel/-action/index.md), state: [GetState](../../com.msabhi.flywheel/index.md#-1063038712%2FClasslikes%2F-2051426397)<[S](index.md)>, next: [Dispatch](../../com.msabhi.flywheel/index.md#1816344649%2FClasslikes%2F-2051426397), dispatch: [Dispatch](../../com.msabhi.flywheel/index.md#1816344649%2FClasslikes%2F-2051426397))  <br><br><br>|

