package com.example.call_presentation.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.annyo.network.di.SupervisorMainImmediateScope
import com.example.call_domain.use_cases.rtc_use_case.RtcUseCases
import com.example.call_domain.util.CallHandlerService
import com.example.call_domain.util.CallMode
import com.example.call_domain.util.OfferType
import com.example.call_domain.util.RtcMediaCaptureClient
import com.example.notification.fcm.FcmBroadcastActions
import com.example.theme.AnnyoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CallActivity() : ComponentActivity() {

    @Inject
    lateinit var mc: RtcMediaCaptureClient

    @Inject
    lateinit var rc: RtcUseCases

    @Inject
    @SupervisorMainImmediateScope
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var ser: CallHandlerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
 /////////
        //registerReceiver(FcmCallBroadcastReceiver(), IntentFilter("ACTION_INCOMING_CALL"))

        CoroutineScope(Dispatchers.Default).launch {
            delay(2000)
            val intent = Intent()
            intent.setAction(FcmBroadcastActions.ACTION_INCOMING_CALL)
//            val intent = Intent(FcmBroadcastActions.ACTION_INCOMING_CALL).apply {
//                // putExtra(message.data.keys.first(),message.data.values.first())
//            }
            sendBroadcast(intent)
            sendBroadcast(Intent("ACTION_USB_DEVICE_ATTACHED"))
        }
////////

//         val mConnection = object : ServiceConnection {
//
//            override fun onServiceConnected(className: ComponentName, service: IBinder) {
//                // This is called when the connection with the service has been
//                // established, giving us the object we can use to
//                // interact with the service.  We are communicating with the
//                // service using a Messenger, so here we get a client-side
//                // representation of that from the raw IBinder object.
//              //  mService = Messenger(service)
//              //  bound = true
//                println("Bound Successful")
//                val a  = service as CallHandlerServiceImpl.CallHandlerServiceBinder
//
//                scope.launch {
//                    a.getService().observePeerConnectionEvents().collect{
//                        println(it.toString())
//                    }
//                }
//            }
//
//            override fun onServiceDisconnected(className: ComponentName) {
//                // This is called when the connection with the service has been
//                // unexpectedly disconnected&mdash;that is, its process crashed.
//              //  mService = null
//              //  bound = false
//            }
//        }
//
//        startService(Intent(this,ser::class.java))
//        Intent(this, ser::class.java).also { intent ->
//            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
//        }

        CoroutineScope(Dispatchers.Default).launch {
            rc.createPeerConnection().collect{
                println(it.toString())
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            rc.sendOutgoingCallOffer(OfferType.VideoCall)
        }
        setContent {
            AnnyoTheme {
//               IncomingCallScreen( state = IncomingCallState(
//                   callerName = "Angella",
//                   callType = CallType.AudioCall,
//                   callerPic = Uri.parse("https://plus.unsplash.com/premium_photo-1673032234607-17578f901231?w=400&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw3M3x8fGVufDB8fHx8fA%3D%3D")
//               ) )
                CallingScreen(
                    state = CallScreenState(
                        callType = CallMode.AudioCall, calleeName = "Angella",
                        calleePic = Uri.parse(
                            "https://plus.unsplash.com/premium_photo-1673032234607-17578f901" +
                                    "231?w=400&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw3M3x8fGVufD" +
                                    "B8fHx8fA%3D%3D"
                        ),
                        callStatus = CallStatus.Ringing
                    ), localSurfaceView = {
                        rc.setUpSurfaceViewRendererForVideoCapturing(it)},
                    remoteSurfaceView = {
                        rc.setUpSurfaceViewRendererForVideoCapturing(it)
                    }
                )
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AnnyoTheme {
//        Greeting("Android")
//    }
//}