package com.androidthings.acromion.framework

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.androidthings.acromion.data.BMETemperature
import com.androidthings.acromion.framework.PinControl.redLedPin
import com.androidthings.acromion.usecases.SendAlarm

// Framework implementation of BMEPressure interface
class TemperatureCallback : SensorEventListener, BMETemperature {

    var temperature = -274

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            temperature = event.values[0].toInt()
        }

        when {
            temperature > 20 -> {
                redLedPin.value = true
                SendAlarm(Temboo)
            }
            temperature <= 0 -> redLedPin.value = false
        }
    }

    override fun currentBMETemp(): Int {
        return temperature
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
