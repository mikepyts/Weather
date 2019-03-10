package com.androidthings.acromion.framework

import android.util.Log
import com.androidthings.acromion.framework.RaspberryPie.GPIOmanager
import com.google.android.things.pio.Gpio

// Initialize all pins, set ActiveTypes and signal directions
object PinControl {

    // Link LED pins to the Raspberry GPIO physical pins
    val bluePin: Gpio = GPIOmanager.openGpio("BCM17")
    val redPin: Gpio = GPIOmanager.openGpio("BCM27")
    val greenPin: Gpio = GPIOmanager.openGpio("BCM22")
    val redLedPin: Gpio = GPIOmanager.openGpio("BCM4")

    init {
        Log.d("PinControl", "Pins linked to Raspberry Pie GPIO pins")
        try {
            // Set pin active type (high or low signal level)
            bluePin.setActiveType(Gpio.ACTIVE_LOW)
            redPin.setActiveType(Gpio.ACTIVE_LOW)
            greenPin.setActiveType(Gpio.ACTIVE_LOW)
            redLedPin.setActiveType(Gpio.ACTIVE_HIGH)
            Log.d("PinControl", "Active type set for pins set")

            // Set direction (receive to/generate signal from pin)
            bluePin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH)
            redPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH)
            greenPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH)
            redLedPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
            Log.d("PinControl", "Active type set for pins set")

        } catch (e: Exception) {
            Log.d("PinControl", e.message)
        }
    }

    fun setRGBPins(red: Boolean, green: Boolean, blue: Boolean) {
        redPin.value = red
        greenPin.value = green
        bluePin.value = blue
    }

}