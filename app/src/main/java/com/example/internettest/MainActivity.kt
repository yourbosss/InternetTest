package com.example.internettest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var Button: Button = findViewById(R.id.btnHTTP)
        val url = URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
        Button.setOnClickListener {
            Thread {
                val connection = url.openConnection() as HttpURLConnection
                try{
                    val data = connection.inputStream.bufferedReader().readText()
                    connection.disconnect()
                    Log.d("Flickr cats", data)
                } catch(e: Exception) {
                    e.printStackTrace()
                    Button.setBackgroundColor(Color.RED)
                }
            }.start()
        }
        var OkButton: Button = findViewById(R.id.btnOkHTTP)
        OkButton.setOnClickListener {
            var okHttpClient: OkHttpClient = OkHttpClient()
            val request: Request = Request.Builder().url(url).build()
            okHttpClient.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("err", e.toString());
                }
                override fun onResponse(call: Call, response: Response) {
                    val json = response.body?.string()
                    Log.i("Flickr OkCats", json.toString())
                }
            })
        }
    }
}