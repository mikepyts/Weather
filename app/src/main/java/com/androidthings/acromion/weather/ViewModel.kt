package com.androidthings.acromion.weather

import android.app.Activity
import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.androidthings.acromion.data.BME280Repo
import com.androidthings.acromion.framework.*
import com.androidthings.acromion.usecases.SendData
import com.androidthings.acromion.usecases.WriteData

// Not-initialized variables
lateinit var BME280: BME280
lateinit var sensorManager: SensorManager
lateinit var pressureCallback: PressureCallback
lateinit var temperatureCallback: TemperatureCallback

class ViewModel : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)

        // Set-up board
        RaspberryPie
        // Bind pins
        PinControl

        // Configure BME280
        pressureCallback = PressureCallback()
        temperatureCallback = TemperatureCallback()
        sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        BME280 = BME280(sensorManager, temperatureCallback, pressureCallback)
        BME280.registerSensors()

        // Initialize data repository
        val bme280Repo = BME280Repo(temperatureCallback, pressureCallback)

        // Prepare Pub/Sub connection
        val publisher = Publisher(this)
        val sendDataCase = SendData(bme280Repo, publisher)
        val writeDataCase = WriteData(bme280Repo)

        // Instead of service (will be implemented later)
        val handler = Handler()
        val runnableCode = object : Runnable {
            override fun run() {
                writeDataCase.invoke()
                sendDataCase.invoke()
                handler.postDelayed(this, 5000)

            }
        }
        handler.post(runnableCode)
    }

    // Release resources
    override fun onDestroy() {
        super.onDestroy()
        Log.d("Main", "onDestroy: unregistering listeners")
        sensorManager.unregisterListener(temperatureCallback)
        sensorManager.unregisterListener(pressureCallback)
        try {
            BME280.sensorDriver.close()
            Log.d("Main", "onDestroy: SensorDriver was closed")
        } catch (e: Exception) {
            Log.d("Main", e.message)
        }
    }
}
