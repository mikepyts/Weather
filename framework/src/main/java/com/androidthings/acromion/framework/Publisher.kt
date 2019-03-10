package com.androidthings.acromion.framework

import android.content.Context
import android.util.Log
import com.androidthings.acromion.domain.TPD
import com.androidthings.acromion.usecases.Sender
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.json.JSONObject
import java.util.*


// Framework implementation of Sender interface
class Publisher(private var ctx: Context) : Sender {

    private val accessScope =
        listOf("https://www.googleapis.com/auth/pubsub", "https://www.googleapis.com/auth/cloud-platform")
    private val pubsubAPI = "https://pubsub.googleapis.com/v1/projects/weather-231121/topics/WeatherDataTopic:publish"
    private val base64Encoder: Base64.Encoder = Base64.getEncoder()

    private var token = "Empty"

    // Receive Google OAuth token
    private fun authenticate() {
        doAsync {
            val googleCredential = GoogleCredential
                .fromStream(ctx.assets.open("service-account.json"))
                .createScoped(accessScope)
            googleCredential.refreshToken()
            onComplete { this@Publisher.token = googleCredential.accessToken }
        }
        Log.d("Publisher", token)
    }

    // Encode TPD to base64
    override fun createEncodedData(TPD: TPD): String {
        with(TPD) {
            val data = """{"Temperature":"$temperature", "Pressure":"$pressure", "DateTime":"$dateTime"}"""
            Log.d("Publisher", "Encoded data was created")
            Log.d("Publisher", data)
            return base64Encoder.encodeToString(data.toByteArray())
        }
    }

    // Send POST API to Pub/Sub
    override fun sendData(data: String) {
        when (token) {
            "Empty" -> authenticate()
            else -> {
                val body = JSONObject("""{"messages": [{"data":"$data"}]}""")
                Log.d("Publisher", token)
                val headers = mapOf("Authorization" to ("Bearer $token"))
                val postRequest = khttp.post(pubsubAPI, headers, data = body)
                Log.d("Publisher", postRequest.statusCode.toString())
            }
        }
    }

}
