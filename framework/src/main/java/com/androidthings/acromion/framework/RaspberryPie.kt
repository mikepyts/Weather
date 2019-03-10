package com.androidthings.acromion.framework

import com.google.android.things.pio.PeripheralManager

// Create PeripheralManager
object RaspberryPie {
    val GPIOmanager: PeripheralManager = PeripheralManager.getInstance()
}

