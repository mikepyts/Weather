package com.androidthings.acromion.data

import com.androidthings.acromion.domain.TPD
import java.time.LocalDateTime

// Inversion of control (data from constructor parameters) and dependency inversion (data from outer layers which implement interfaces)
class BME280Repo(private val temperature: BMETemperature, private val pressure: BMEPressure) {

    // Instead of persistent TDP source from memory or database
    private val dataHolder = TPD(temperature.currentBMETemp(), pressure.currentBMEPress(), LocalDateTime.now())

    fun writeDataToHolder() {
        with(dataHolder) {
            temperature = this@BME280Repo.temperature.currentBMETemp()
            pressure = this@BME280Repo.pressure.currentBMEPress()
            dateTime = LocalDateTime.now()
        }
    }

    fun getDataFromHolder(): TPD {
        return dataHolder
    }
}

interface BMETemperature {
    fun currentBMETemp(): Int
}

interface BMEPressure {
    fun currentBMEPress(): Int
}

