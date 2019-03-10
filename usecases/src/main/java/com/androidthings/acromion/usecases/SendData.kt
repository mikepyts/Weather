package com.androidthings.acromion.usecases

import com.androidthings.acromion.data.BME280Repo
import com.androidthings.acromion.domain.TPD
import org.jetbrains.anko.doAsync

// Usecase: SendData to the API
class SendData(private val bme280Repo: BME280Repo, private val sender: Sender) {

    operator fun invoke() {
        doAsync {
            bme280Repo.getDataFromHolder()
            with(sender) {
                sendData(createEncodedData(bme280Repo.getDataFromHolder()))
            }
        }
    }
}

interface Sender {
    fun createEncodedData(TPD: TPD): String
    fun sendData(data: String) {

    }
}
