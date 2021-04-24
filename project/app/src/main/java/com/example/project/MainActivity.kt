package com.example.project

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val EditTextmsg = findViewById<EditText>(R.id.editTextTextMsg)
        val hisMsg = findViewById<TextView>(R.id.textViewMsg)
        val btnInput = findViewById<Button>(R.id.btnInput)
        hisMsg.setMovementMethod(ScrollingMovementMethod())
        val uname = intent.getStringExtra("pass_u_n")

        val thread2: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        sleep(3000)
                        runOnUiThread { // update TextView here!
                            //val getData = FetchData("http://192.168.4.47/LoginRegister/getMsg.php")
                            val getData = FetchData("http://104.5.23.196/LoginRegister/getMsg.php")
                            if (getData.startFetch()) {
                                if (getData.onComplete()) {
                                    val result: String = getData.getResult()
                                    //hisMsg.text = Html.fromHtml(result, Html.FROM_HTML_MODE_COMPACT)
                                    hisMsg.text= Html.fromHtml(result)
                                }
                            }
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }

        thread2.start()


        btnInput.setOnClickListener {
            val username: String
            val message: String
            //username="tt";
            username = uname!!

            //username=textInputEditTextUsername.getText().toString();
            message = EditTextmsg.text.toString()

            //Start ProgressBar first (Set visibility VISIBLE)

            //Start ProgressBar first (Set visibility VISIBLE)
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                //Starting Write and Read data with URL
                //Creating array for parameters
                val field = arrayOfNulls<String>(2)
                field[0] = "username"
                field[1] = "msg"
                //Creating array for data
                val data = arrayOfNulls<String>(2)
                data[0] = username
                data[1] = message
                //val putData = PutData("http://192.168.4.47/LoginRegister/addinfo.php", "POST", field, data)
                val putData = PutData("http://104.5.23.196/LoginRegister/addinfo.php", "POST", field, data)
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        val result = putData.getResult()
                        //End ProgressBar (Set visibility to GONE)
                        //Log.i("PutData", result);
                        if (result == "Input Success") {
                            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                            //Intent intent=new Intent(getApplicationContext(),Login.class);
                            //startActivity(intent);
                            //finish();
                            EditTextmsg.setText("");
                        } else {
                            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //End Write and Read data with URL


        } }


    }
}