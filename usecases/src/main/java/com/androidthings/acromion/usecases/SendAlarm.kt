package com.androidthings.acromion.usecases

// Send Alarm (Current framework Temboo -> Twilio API -> Make)
class SendAlarm(private val alarm: Alarm) {

    operator fun invoke() {
        alarm.sendAlarm()
    }
}

interface Alarm {
    fun sendAlarm()
}