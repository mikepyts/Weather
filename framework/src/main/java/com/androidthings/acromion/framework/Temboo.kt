package com.androidthings.acromion.framework

import com.androidthings.acromion.usecases.Alarm
import com.temboo.Library.Twilio.Calls.MakeCall
import com.temboo.core.TembooSession

// Framework implementation of Alarm interface
object Temboo : Alarm {
    override fun sendAlarm() {
        val tembooSession = TembooSession("", "", "")

        // Info https://temboo.com/library/Library/Twilio/Calls/MakeCall/
        val choreoCall = MakeCall(tembooSession)

        // Info:
        // https://www.twilio.com/console
        // https://www.twilio.com/console/runtime/twiml-bins
        val callInputSet = choreoCall.newInputSet()
        with(callInputSet) {
            set_AccountSID("")
            set_URL("")
            set_AuthToken("")
            set_From("")
            set_To("")
        }
        choreoCall.execute(callInputSet)
    }
}