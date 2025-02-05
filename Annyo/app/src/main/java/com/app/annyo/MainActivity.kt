package com.app.annyo

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.call_data.web_socket.request_and_response.SignallingEventRequests
import com.example.call_domain.model.SignallingRequests
import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.repository.RtcSignalingRepository
import com.example.call_domain.use_cases.rtc_use_case.RtcUseCases
import com.example.call_domain.util.CallHandlerService
import com.example.call_domain.util.RtcMediaCaptureClient
import com.example.call_presentation.util.FcmCallBroadcastReceiver
import com.example.notification.call.CallNotifier
import com.example.notification.call.model.Individual
import com.example.notification.call.utills.broadcast.CallNotificationEventsMonitor
import com.example.ui.R
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var usercase : ProfileUseCases

    @Inject
    lateinit var rtc : RtcConnectionRepository


    @Inject
    lateinit var m : RtcMediaCaptureClient

    @Inject
    lateinit var mc: RtcMediaCaptureClient

    @Inject
    lateinit var s: RtcSignalingRepository
    @Inject
    lateinit var rc:RtcUseCases

    @Inject
    lateinit var ser: CallHandlerService

    private  val CALL = "https://www.annyo.co"

    @Inject
    lateinit var callBroad: CallNotificationEventsMonitor

    @Inject
    lateinit var callNot: CallNotifier

    @Inject
    lateinit var firebaseMess: FirebaseMessaging


//   val p =  PendingIntent.getActivity(
//        this,
//        1,
//        Intent().apply {
//            component = ComponentName(
//                packageName,
//                "com.example.call_presentation.views.CallActivity" ,
//            )
//        },
//        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
//    )

//    val inn = Intent().apply {
//        component = ComponentName(
//            packageName,
//            "${CALL}" ,
//        )
//    }

    //var in2 : Intent? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            //////////////////

        CoroutineScope(Dispatchers.IO).launch {
            s.observeSignallingSocketConnectionEvent().collect{
                println(it.toString()+"olfnvcoiiohnrrfVOIVNS")
            }

        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(5000)
            println("swendongggggggggggg")
            s.sendSignallingEvent(SignallingRequests.RegisterCallSession(
                "sessionid",
                "caller123",
                "callewe123"
            ))
            delay(5000)
            s.sendSignallingEvent(SignallingRequests.ConnectToCallServer("edascswedc"))
        }

        //////////////////
//        CoroutineScope(Dispatchers.Default).launch {   LC
//            rc.createPeerConnection().collect{
//                println(it.toString())
//            }
//        }
//
//
//
//        try {
//
//            in2 = Intent().apply {
////            action = Intent.ACTION_MAIN
////            data  = ("https://www.annyo.co").toUri()
//                component  = ComponentName(
//                    packageName,"com.example.call_presentation.views.CallActivity"
//                )
//            }
//
//            in2 = Intent(
//                this,
//                Class.forName("com.example.call_presentation.views.CallActivity")
//            )
//
//            startActivity(in2)
//        } catch (e: ClassNotFoundException) {
//            e.printStackTrace()
//        }    LC



//        val intent = Intent(this, CallActivity::class.java)
//        // Start the activity
//        startActivity(intent)
//



//        val locPro  = LocationProvider(this)
//
//        val locRE = locPro.getDefaultLocationRequest()
//        GlobalScope.launch {
//            locPro.checkLocationSettings(locRE)
//            locPro.getCurrentLocation()
//        }





//
//        m.enableOrDisableAudio()
//
//       CoroutineScope(Dispatchers.Main).launch {
//            rtc.createPeerConnection()
//                .collect{
//                    println(it)
//                }
//
//
//       }
//
//        CoroutineScope(Dispatchers.Main).launch {
//            delay(3000)
//            val a = rtc.createOffer(OfferOrAnswerType.VideoCallOffer)
//            println(a)
//        }





        // isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)

        ////////// Call Notification Broadcast


//        CoroutineScope(Dispatchers.Default).launch {
//            println("From Scope")
//            val a = callBroad.getMissedCallNotificationEvents()
//            println("Test111"+ a.toString())
//
//         //   delay(1000)
//            //sendBroadcast(Intent(CallBroadcastActions.ACTION_CALLBACK.action))
//          //  delay(1000)
//            delay(1000)
//            sendBroadcast(Intent("ACTION_CALLBACK"))
//        }
//
//        CoroutineScope(Dispatchers.Default).launch {
//
//            delay(1000)
//            sendBroadcast(Intent("ACTION_CALLBACK"))
//        }



       // sendBroadcast(Intent(CallBroadcastActions.ACTION_CALLBACK.action))


        ////////////////// Call Notification
//        callNot.postMissedCallNotification(
//            Individual(
//                "Angella", Icon.createWithResource(this,com.example.ui.R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash),true
//            )
//        )
//        runBlocking {
//            delay(10000)
//        }
//
//        callNot.postMissedCallNotification(
//            Individual(
//                "Angella",
//                IconCompat.createWithResource(this,R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash),
//                true,
//            ),10
//        )

        //////////////// Call Service

       //ContextCompat.startForegroundService(this ,Intent(this,ser::class.java))

        ///////// FCM

        firebaseMess.token.addOnSuccessListener { result->
            println("token = $result")
        }
        registerReceiver(FcmCallBroadcastReceiver(), IntentFilter("ACTION_CALLBACK"),
            RECEIVER_EXPORTED
        )

        setContent {
//            AnnyoTheme {
//                val scope  = rememberCoroutineScope()
//                val context = LocalContext.current
//                SurfaceView(context).context /// todo if you can pass surface view in place of surface view renderer
//                VideoCallScreen(rc,mc) {
//                    rc.setUpSurfaceViewRendererUseCase(it)
//                }
            val context = LocalContext.current
            Column {
                Button(onClick = {  /// B1
                    // startActivity(intent)
                    // sendBroadcast(Intent("ACTION_CALLBACK"))
                    callNot.postMissedCallNotification(
                        Individual(
                            "Angella", IconCompat.createWithResource(context, R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash),true
                        )
                    ) }) {}

                Button(onClick = {  /// B2
                    ContextCompat.startForegroundService(
                        context ,Intent(context,ser::class.java).apply {
                            putExtra("a","AAAA")
                        }
                    )
                }) {}

                Button(onClick = {  /// B3
                    val  in2 = Intent(
                        context,
                        Class.forName("com.example.call_presentation.util.CallHandlerServiceImpl")
                    ).apply {
                        putExtra("CALL_TYPE","OUTGOING_CALL")
                        putExtra("CALL_MODE","VIDEO_CALL")
                        //component  = ComponentName(packageName,"com/example/call_presentation/views/CallActivity")
                    }
                    ContextCompat.startForegroundService(
                        context,in2
                    )
                 //   ContextCompat.startActivity(context,in2,null)
//                    ContextCompat.startForegroundService(
//                        context ,Intent(context,ser::class.java).apply {
//                            putExtra("CALL_TYPE","OUTGOING_CALL")
//                            putExtra("CALL_MODE","VIDEO_CALL")
//                        }
//                    )
//                    ContextCompat.startForegroundService(
//                        context,
//                        Intent(context  ,Class.forName("com/example/call_presentation/util/CallHandlerServiceImpl")).apply {
//                            putExtra("CALL_TYPE","OUTGOING_CALL")
//                            putExtra("CALL_MODE","VIDEO_CALL")
//                        }
//                    )

                    println("pppppp = $packageName")
                }) {}


                Button(onClick = { sendBroadcast(Intent("ACTION_CALLBACK")) }) {

                }
            }

             //  AnnyoNavHost(navController = rememberNavController(), startDestination = onboardingGraph)
                //  ProfileScreen()
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
                 //  RegisterScreen()
             // OtpLogScreen()
               // ProfileScreen()
             //   Name()
              //  OnBoardingAnimation()
                //LocTExtCompose()
//               PermissionDialogBox(
//                   onAllowAccessClick = {},
//                   onGoToSettingsClick = {},
//                   onNotNowClick = {}
//               )
//                val context = LocalContext.current
//                val scope = rememberCoroutineScope()
//
//                Button(onClick = {  }) {
//                }
                 //LocationInputButton()
//                LocationInputScreen(
//                    CurrentLocationProvider(this)
//                ) {}
                // OnBoardingScreen()
           //   OnBoardingScreen()
              //  OnboardingScreen()
              //  OnboardingScreen()
//                PhotoInputScreen(
//
//                ){ photoCount, uri ->
//
//                }
            //    EditProfileScreen()
                var options by remember {
                    mutableStateOf(listOf("fewfew","FWEEmkew"))
                }
               // EditProfileScreen()
               // OtpLogInScreen()
//                ChildrenScreen(
//                    onNextCLick = {},
//                    onBackClick = {}
//                )
               // TestScreen()
            //ProfileEditScreenRoute(navigateUp = { /*TODO*/ }) //todo(commented)
//                Call{
//
//                }
              /// PhotosScreen()
                //PhotosScreen()
             //   EditProfileScreen()

            //                Column {
////                    DropDownList(
////                        modifier = Modifier
////                            .width(500.dp),
////                        listHeight = 500.dp
////                    )
////                    DropDownList(
////                        modifier = Modifier
////                            .height(200.dp)
////                            .width(500.dp),
////                        listHeight = 500.dp
////                    )
//
//                    ChipsGroup(
//                        choices = listOf("fewfew","FWEEmkew","ml;efl;mv","ML;K;lmc"),
//                        selectedOptions = options,
//                        onSelect = {
//                            options = listOf(it)
//                        }
//                    )
//                }
               // EditProfileScreen()
                //RegisterScreen()
               // BottomBar()
              //  TopAppBar()
            //TestScreen()
                //casac()
               // OtpLogInScreen()

              //  OtpLoginScreen()
                //CountryCodes()
             //   CountryCodeListItem()
              //  CanvasTry()
               // ProfileScreen()
               // EditScreen()
               // Swipe()
                    //OtpLoginScreen()
                    //()
                  //  LikeScreen()
                //DislikeScreen()
              //  }
            }
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(){
//    Column(
//        modifier = Modifier
//            .background(color = MaterialTheme.colorScheme.secondaryContainer)
//            .fillMaxSize()
//    ){
//       TopAppBar(modifier = Modifier)
//        //EditQuizScreen()
//       // PreferenceEditScreen()
//         //  EditProfileScreen()
//       // ProfileScreen()
//      // QuizScreen()
//
//        // JBN()
//  // MatchScreen()
//      // ProfileDiscoverScreen()
//   //
//        //MainScreenPopup()
//      //  CanvasTry()
//      //BottomAppBar()
//    }
    var isMatched by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier,
        topBar = {
//            if (!isMatched)
              // TopAppBar()  // todo(commented)
        },
        bottomBar = {
            //  if (!isMatched)
             // BottomAppBar()
        }
    ){ padding->

        //ProfileScreen()

//        ExploreScreen(
//            modifier = Modifier.padding(padding)
//        ){}
//        ExploreScreen(
//            modifier = Modifier.padding(padding)
//        ){isMatched = !isMatched}
        //  EditQuizScreen()
        //OnBoardingScreen()
//        ExploreScreen(
//        ){}
       // PreferenceEditScreen()
     //   RegisterScreen()
        //OtpLogInScreen()
      //  OnBoardingScreen()
        //ProfileScreen()
        ///EditProfileScreen()
    }
}


/// componentName  -  Activity
// componentName  - Service
/// classFor  - Activity
// classFor - Service









//// kotlinx Date Time

//val current   = Clock.System.now()
//// dob  = LocalDateTime.parse("2023-3-01")
//val today = current.toLocalDateTime(TimeZone.currentSystemDefault())
//val instantInThePast: Instant = Instant.parse("2002-10-02T00:00:00Z")
//val dddv = Instant.parse("2023-10-02T00:00:00Z")
//val period  = instantInThePast.periodUntil(dddv, TimeZone.UTC)
//
//val born = LocalDate(2002,10,2)
//val now = current.toLocalDateTime(TimeZone.currentSystemDefault()).date
//
//val age = born.yearsUntil(now)
//
//val asOfNo  = current
//val berlin = current.toLocalDateTime(TimeZone.of("Europe/Berlin"))
//
//val duration =  (dddv-instantInThePast)
//// val dmksc = LocalDateTime(LocalDate(today.year,today.month,today.dayOfMonth),LocalTime(today.hour,today.minute,today.second)).toInstant(TimeZone.of("Europe/Berlin"))
//
//// Toast.makeText(this,period.toString(),Toast.LENGTH_LONG).show()
//Log.d("dob","${period.days} ${period.months} ${period.years}")
//Log.d("dob","agev = "+age.toString())
//Log.d("dob", "noofdays = $duration")
//Toast.makeText(this,age.toString(),Toast.LENGTH_LONG).show()
//
//try {
//    //  val date = LocalDate()
//}catch (e:Exception){
//    Log.d("dob",e.message.toString())
//}

