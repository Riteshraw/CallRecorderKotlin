package com.rr.kotlinbasics

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.util.Log


class PhoneStatReceiver : BroadcastReceiver {
    private val TAG = "PhoneStatReceiver"
    private var incomingFlag = false
    private var incoming_number: String? = null
    private var outgoing_number: String? = null

    constructor() {

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            incomingFlag = false
            outgoing_number = intent?.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            Log.i(TAG, "call OUT:$outgoing_number")
        } else {
            val tm = context?.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager

            when (tm.callState) {
                TelephonyManager.CALL_STATE_RINGING -> {
                    incomingFlag = true
                    incoming_number = intent?.getStringExtra("incoming_number")
                    Log.i(TAG, "RINGING :" + incoming_number);
                }
                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    if (incomingFlag)
                        Log.i(TAG, "incoming ACCEPT :" + incoming_number);
                }
                TelephonyManager.CALL_STATE_IDLE -> {
                    if (incomingFlag)
                        Log.i(TAG, "incoming IDLE");
                }
            }
        }
    }

}