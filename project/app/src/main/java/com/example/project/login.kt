package com.example.project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val textInputEditTextUsername = findViewById<TextInputEditText>(R.id.usernameL)
        val textInputEditTextPassword = findViewById<TextInputEditText>(R.id.passwordL)
        val buttonlogin = findViewById<Button>(R.id.btnLogin)
        val textViewSignup = findViewById<TextView>(R.id.textViewSignup)

        textViewSignup.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, signup::class.java)
            startActivity(intent)
            finish()
        })


        buttonlogin.setOnClickListener(View.OnClickListener {
            val username: String
            val password: String
            username = textInputEditTextUsername.getText().toString()
            password = textInputEditTextPassword.getText().toString()


            //Start ProgressBar first (Set visibility VISIBLE)
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                //Starting Write and Read data with URL
                //Creating array for parameters
                val field = arrayOfNulls<String>(2)
                field[0] = "username"
                field[1] = "password"
                //Creating array for data
                val data = arrayOfNulls<String>(2)
                data[0] = username
                data[1] = password
                val putData =
                    //PutData("http://192.168.4.47/LoginRegister/login.php", "POST", field, data)
                    PutData("http://104.5.23.196/LoginRegister/login.php", "POST", field, data)

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        val result = putData.getResult()
                        //End ProgressBar (Set visibility to GONE)
                        //Log.i("PutData", result);
                        if (result == "Login Success") {
                            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.putExtra("pass_u_n", username)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //End Write and Read data with URL
            }
        })
    }


}