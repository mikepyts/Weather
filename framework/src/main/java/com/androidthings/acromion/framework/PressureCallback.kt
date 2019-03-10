package com.androidthings.acromion.framework

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.androidthings.acromion.data.BMEPressure

// Those constants are related only for this logic
const val LEVEL1 = 1009
const val LEVEL2 = 1020

// Framework implementation of BMEPressure interface
class PressureCallback : SensorEventListener, BMEPressure {

    private var pressure: Int = 0

    // Update pressure value from sensor
    override fun onSensorChanged(event: SensorEvent?) {

        if (event != null) {
            pressure = event.values[0].toInt()
        }

        with(PinControl) {
            when {
                pressure <= LEVEL1 -> setRGBPins(red = false, green = false, blue = true)
                pressure in LEVEL1..LEVEL2 -> setRGBPins(red = false, green = true, blue = false)
                pressure >= LEVEL2 -> setRGBPins(red = true, green = false, blue = false)
            }
        }
    }

    override fun currentBMEPress(): Int {
        return pressure
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}