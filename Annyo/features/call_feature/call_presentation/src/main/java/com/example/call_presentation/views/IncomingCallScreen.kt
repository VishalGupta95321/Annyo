package com.example.call_presentation.views

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.app.call_presentation.R
import com.example.call_domain.util.CallMode
import com.example.call_presentation.CallScreenViewModel
import com.example.theme.AnnyoTheme
import org.webrtc.SurfaceViewRenderer

@Composable
fun CallScreenRoute(
    modifier: Modifier = Modifier,
    callScreenViewModel: CallScreenViewModel = hiltViewModel()
) {

}


@Composable
fun CallingScreen(
    modifier: Modifier = Modifier,
    state: CallScreenState,
    onToggleSpeaker: () -> Unit = {},
    onSwitchToVideoCall: () -> Unit = {},
    onSwitchCamera: () -> Unit = {},
    onToggleVideo: () -> Unit = {},
    onToggleMic: () -> Unit = {},
    onEndCall: () -> Unit = {},
    onCancel: () -> Unit = {},
    onMessage: (String) -> Unit = { _ -> },
    onCallAgain: () -> Unit = {},
    localSurfaceView: (SurfaceViewRenderer) -> Unit,
    remoteSurfaceView: (SurfaceViewRenderer) -> Unit
) {
    RtcSurfaceViewRenderer(surface = remoteSurfaceView)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CallInfo(
                personPic = state.calleePic,
                personName = state.calleeName
            )
            Text(
                modifier = modifier,
                text = when (state.callStatus) {
                    is CallStatus.Answered -> state.callStatus.duration
                    CallStatus.Busy -> stringResource(R.string.busy)
                    CallStatus.Calling -> stringResource(R.string.calling)
                    CallStatus.Connecting -> stringResource(R.string.connecting)
                    CallStatus.Failed -> stringResource(R.string.failed)
                    CallStatus.NoAnswer -> stringResource(R.string.noanswer)
                    CallStatus.ReConnecting -> stringResource(R.string.reconnecting)
                    CallStatus.Ringing -> stringResource(R.string.ringing)
                },
                style = MaterialTheme.typography.titleMedium
            )
        }
        RtcSurfaceViewRenderer(
            modifier = Modifier.fillMaxSize(0.3f)
                .background(color = Color.Red),
            surface = localSurfaceView
        )
        when (state.callType) {
            CallMode.AudioCall -> AudioCallingHandlerDock(
                modifier = Modifier.align(Alignment.BottomCenter),
                onToggleSpeaker = onToggleSpeaker,
                onSwitchToVideoCall = onSwitchToVideoCall,
                onToggleMic = onToggleMic,
                onEndCall = onEndCall
            )

            CallMode.VideoCall -> VideoCallingHandlerDock(
                modifier = Modifier.align(Alignment.BottomCenter),
                onSwitchCamera = onSwitchCamera,
                onToggleVideo = onToggleVideo,
                onToggleMic = onToggleMic,
                onEndCall = onEndCall
            )
        }
    }
    if (state.callStatus == CallStatus.NoAnswer)
        CallNotAnsweredHandlerDock(
            onCancel = onCancel,
            onMessage = onMessage,
            onCallAgain = onCallAgain
        )
}

data class CallScreenState(
    val callType: CallMode,
    val calleeName: String,
    val calleePic: Uri?,
    val callStatus: CallStatus
)

@Composable
fun IncomingCallScreen(
    modifier: Modifier = Modifier,
    state: IncomingCallState,
    onAnswer: () -> Unit = {},
    onDecline: () -> Unit = {},
    onMessage: (String) -> Unit = { _ ->},
    localSurfaceView: (SurfaceViewRenderer) -> Unit,
) {
    RtcSurfaceViewRenderer(surface = localSurfaceView)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CallInfo(
                personPic = state.callerPic,
                personName = state.callerName
            )
            Text(
                modifier = modifier,
                text = if (state.callType == CallMode.AudioCall) "AudioCall" else "VideoCall",
                style = MaterialTheme.typography.titleMedium
            )
        }
        IncomingCallHandlerDock(
            modifier = Modifier.align(Alignment.BottomCenter),
            callType = state.callType,
            onAnswer = onAnswer,
            onDecline = onDecline,
            onMessage = onMessage
        )
    }
}


data class IncomingCallState(
    val callType: CallMode,
    val callerPic: Uri?,
    val callerName: String
)

@Composable
fun AudioCallingHandlerDock(
    modifier: Modifier = Modifier,
    onToggleSpeaker: () -> Unit,
    onSwitchToVideoCall: () -> Unit,
    onToggleMic: () -> Unit,
    onEndCall: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CallButton(iconId = R.drawable.speaker_on, onClick = onToggleSpeaker)
        CallButton(iconId = R.drawable.videocam_on, onClick = onSwitchToVideoCall)
        CallButton(iconId = R.drawable.mic_on, onClick = onToggleMic)
        CallButton(tint = Color.Red, iconId = R.drawable.call_end, onClick = onEndCall)
    }
}

@Composable
fun VideoCallingHandlerDock(
    modifier: Modifier = Modifier,
    onSwitchCamera: () -> Unit,
    onToggleVideo: () -> Unit,
    onToggleMic: () -> Unit,
    onEndCall: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CallButton(iconId = R.drawable.cameraswitch, onClick = onSwitchCamera)
        CallButton(iconId = R.drawable.videocam_on, onClick = onToggleVideo)
        CallButton(iconId = R.drawable.mic_on, onClick = onToggleMic)
        CallButton(tint = Color.Red, iconId = R.drawable.call_end, onClick = onEndCall)
    }
}

@Composable
fun IncomingCallHandlerDock(
    modifier: Modifier = Modifier,
    callType: CallMode,
    onAnswer: () -> Unit,
    onDecline: () -> Unit,
    onMessage: (message: String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 30.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CallButton(tint = Color.Red, iconId = R.drawable.call_end, onClick = onDecline)
        when (callType) {
            CallMode.AudioCall -> CallButton(
                tint = Color.Green, iconId = R.drawable.call_answer,
                onClick = onAnswer
            )

            CallMode.VideoCall -> CallButton(
                tint = Color.Blue, iconId = R.drawable.videocall,
                onClick = onAnswer
            )
        }
        CallButton(iconId = R.drawable.message, onClick = { onMessage("") })
    }
}

@Composable
fun CallNotAnsweredHandlerDock(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onMessage: (message: String) -> Unit,
    onCallAgain: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 30.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CallButton(iconId = R.drawable.cancel, onClick = onCancel)
        CallButton(iconId = R.drawable.message, onClick = { onMessage("") })
        CallButton(tint = Color.Green, iconId = R.drawable.call_answer, onClick = onCallAgain)
    }
}


@Composable
fun SwitchAudioToVideoCallDock(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onAccept: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 30.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CallButton(tint = Color.Green, iconId = R.drawable.cancel, onClick = onCancel)
        CallButton(tint = Color.Green, iconId = R.drawable.videocall, onClick = onAccept)
    }
}

@Composable
fun CallResponseMessagesPrompt(modifier: Modifier = Modifier) {
// todo
}


//@Composable
//fun IncomingCallScreen(
//    modifier: Modifier = Modifier,
//    isVideoCall: Boolean = false
//) {
//    Box(
//        modifier = modifier.fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier.align(Alignment.TopCenter),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            CallInfo(
//                personPic = null,
//                personName = "Angella"
//            )
//            CallStatusText(
//                text = "Calling"
//            )
//        }
//        OnVideoCallHandlerDock(
//            onSwitchCamera = { /*TODO*/ },
//            onToggleVideo = { /*TODO*/ },
//            onToggleMic = { /*TODO*/ }) {
//        }
//    }
//}

@Composable
private fun CallerInfo(modifier: Modifier = Modifier) {

}

@Composable
fun CallInfo(
    modifier: Modifier = Modifier,
    personPic: Uri?,
    personName: String   // todo add place holder and stuff
) {
    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.toDp()
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size((screenHeight / 3f))
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(
                model = personPic,
            ),
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = personName,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun CallStatusText(
    modifier: Modifier = Modifier,
    text: String
) {

}


@Composable
fun CallButton(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    @DrawableRes iconId: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable { onClick() }
    ) {
        Icon(
            modifier = modifier
                .padding(10.dp)
                .size(45.dp),
            tint = tint,
            painter = painterResource(
                id = iconId
            ),
            contentDescription = null
        )
    }
}

sealed interface CallStatus {
    data object Calling : CallStatus
    data object Connecting : CallStatus
    data object ReConnecting : CallStatus
    data object Ringing : CallStatus
    data class Answered(
        val duration: String /// fixme: use long,timestamp or duration type etc
    ) : CallStatus

    data object NoAnswer : CallStatus
    data object Busy : CallStatus
    data object Failed : CallStatus
}

sealed interface OutgoingCallStatusState {
    data object VideoCall : OutgoingCallStatusState
    data object AudioCall : OutgoingCallStatusState
}

@Preview
@Composable
private fun A() {
    AnnyoTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxSize()
        )
//        IncomingCallScreen(
//            state = IncomingCallState(
//                callerName = "Angella",
//                callType = CallType.AudioCall,
//                callerPic = Uri.parse("https://plus.unsplash.com/premium_photo-1673032234607-17578f901231?w=400&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw3M3x8fGVufDB8fHx8fA%3D%3D")
//            )
//        )
        CallingScreen(
            state = CallScreenState(
                callType = CallMode.AudioCall, calleeName = "Angella",
                calleePic = Uri.parse(
                    "https://plus.unsplash.com/premium_photo-1673032234607-17578f901" +
                            "231?w=400&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw3M3x8fGVufD" +
                            "B8fHx8fA%3D%3D"
                ),
                callStatus = CallStatus.Ringing
            ), localSurfaceView = {}, remoteSurfaceView = {}
        )
    }
}