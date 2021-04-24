package com.example.project

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class FetchData(var url: String) : Thread() {
    private var data = "Empty"

    override fun run() {
        try {
            val url = URL(url)
            val httpURLConnection =
                url.openConnection() as HttpURLConnection
            val inputStream = httpURLConnection.inputStream
            val bufferedReader =
                BufferedReader(InputStreamReader(inputStream))
            val result = StringBuilder()
            var result_line: String?
            while (bufferedReader.readLine().also { result_line = it } != null) {
                result.append(result_line)
            }
            bufferedReader.close()
            inputStream.close()
            httpURLConnection.disconnect()
            data = result.toString()
        } catch (e: IOException) {
            data = e.toString()
        }
    }

    fun startFetch(): Boolean {
        start()
        return true
    }

    fun onComplete(): Boolean {
        while (true) {
            if (!this.isAlive) {
                return true
            }
        }
    }

    fun getResult(): String {
        return this.getData()
    }

    fun getData(): String {
        return data
    }

    fun setData(data: String) {
        this.data = data
    }

}
