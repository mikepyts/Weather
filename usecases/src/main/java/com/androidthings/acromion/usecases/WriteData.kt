package com.androidthings.acromion.usecases

import com.androidthings.acromion.data.BME280Repo

// Usecase: write data from BME280 callbacks to data repository
class WriteData(private val bme280Repo: BME280Repo) {

    operator fun invoke() = bme280Repo.writeDataToHolder()

}