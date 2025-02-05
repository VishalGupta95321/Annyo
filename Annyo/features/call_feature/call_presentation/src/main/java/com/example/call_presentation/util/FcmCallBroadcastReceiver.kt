package com.example.call_presentation.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class FcmCallBroadcastReceiver(): BroadcastReceiver() {
//
//    @Inject
//    lateinit var serviceImpl: CallHandlerServiceImpl


    override fun onReceive(p0: Context?, p1: Intent?) {
        val  in2 = Intent(
            p0,
            Class.forName("com/example/call_presentation/util/CallHandlerServiceImpl")
        ).apply {
            putExtra("CALL_TYPE","OUTGOING_CALL")
            putExtra("CALL_MODE","VIDEO_CALL")
        }
     //   println("got itttttttttttttt")
//        ContextCompat.startForegroundService(
//            p0!! ,in2
//        )
        //ContextCompat.startActivity(p0!!,in2,null)
    }
}