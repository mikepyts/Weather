package com.androidthings.acromion.framework

import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import com.google.android.things.contrib.driver.bmx280.Bmx280SensorDriver

// Bosch BME280 control
class BME280(
    val manager: SensorManager,
    val temperatureCallback: TemperatureCallback,
    val pressureCallback: PressureCallback
) {

    val sensorDriver = Bmx280SensorDriver("I2C1", 0x76)
    private val sensorCallback = BME280Callback()

    // Register BME280 sensors
    fun registerSensors() {
        Log.d("BME280", "Registering dynamic callback")
        manager.registerDynamicSensorCallback(sensorCallback)
        with(sensorDriver) {
            try {
                registerTemperatureSensor()
                Log.d("BME280", "Temperature sensor registered")
                registerPressureSensor()
                Log.d("BME280", "Pressure sensor registered")
            } catch (e: Exception) {
                Log.d("BME280", e.message)
            }
        }
    }

    // Dynamic sensorCallback
    inner class BME280Callback : SensorManager.DynamicSensorCallback() {

        override fun onDynamicSensorConnected(sensor: Sensor?) {
            super.onDynamicSensorConnected(sensor)
            if (sensor != null) {
                Log.d("BME280", "Dynamic sensor connected")
                when (sensor.type) {
                    Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                        Log.d("BME280", "Temperature sensor")
                        manager.registerListener(temperatureCallback, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                    }
                    Sensor.TYPE_PRESSURE -> {
                        Log.d("BME280", "Pressure sensor")
                        manager.registerListener(pressureCallback, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                    }
                }
            }
        }
    }
}
