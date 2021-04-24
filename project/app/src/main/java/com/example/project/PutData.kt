package com.example.project

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder




class PutData(
    val url: String,
    val method: String,
    val field: Array<String?>,
    val data:Array<String?>
) : Thread() {
    var result_data = "Empty"
    //var data: Array<String>
    //var field: Array<String>
    override fun run() {
        try {
            val UTF8 = "UTF-8"
            val iso = "iso-8859-1"
            val url = URL(url)
            val httpURLConnection =
                url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = method
            httpURLConnection.doOutput = true
            httpURLConnection.doInput = true
            val outputStream = httpURLConnection.outputStream
            val bufferedWriter =
                BufferedWriter(OutputStreamWriter(outputStream, UTF8))
            val post_data = StringBuilder()
            for (i in field.indices) {
                post_data.append(URLEncoder.encode(field[i], "UTF-8")).append("=")
                    .append(URLEncoder.encode(this.data.get(i), UTF8)).append("&")
            }
            bufferedWriter.write(post_data.toString())
            bufferedWriter.flush()
            bufferedWriter.close()
            outputStream.close()
            val inputStream = httpURLConnection.inputStream
            val bufferedReader =
                BufferedReader(InputStreamReader(inputStream, iso))
            val result = StringBuilder()
            var result_line: String?
            while (bufferedReader.readLine().also { result_line = it } != null) {
                result.append(result_line)
            }
            bufferedReader.close()
            inputStream.close()
            httpURLConnection.disconnect()
            result_data = result.toString()
        } catch (e: IOException) {
            result_data = e.toString()
        }
    }

    fun startPut(): Boolean {
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
        return result_data
    }


}


