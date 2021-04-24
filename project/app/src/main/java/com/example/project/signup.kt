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

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        val textInputEditTextFullname = findViewById<TextInputEditText>(R.id.fullname)
        val textInputEditTextUsername = findViewById<TextInputEditText>(R.id.username)
        val textInputEditTextEmail = findViewById<TextInputEditText>(R.id.email)
        val textInputEditTextPassword = findViewById<TextInputEditText>(R.id.password)
        val buttonSignup = findViewById<Button>(R.id.btnSignup)
        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)

        textViewLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, login::class.java)
            startActivity(intent)
            finish()
        })

        buttonSignup.setOnClickListener(View.OnClickListener {
            val fullname: String
            val username: String
            val password: String
            val email: String
            fullname = textInputEditTextFullname.getText().toString()
            username = textInputEditTextUsername.getText().toString()
            password = textInputEditTextPassword.getText().toString()
            email = textInputEditTextEmail.getText().toString()

            //Start ProgressBar first (Set visibility VISIBLE)
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                //Starting Write and Read data with URL
                //Creating array for parameters
                val field = arrayOfNulls<String>(4)
                field[0] = "fullname"
                field[1] = "username"
                field[2] = "password"
                field[3] = "email"
                //Creating array for data
                val data = arrayOfNulls<String>(4)
                data[0] = fullname
                data[1] = username
                data[2] = password
                data[3] = email
                val putData =
                    //PutData("http://192.168.4.47/LoginRegister/signup.php", "POST", field, data)
                    PutData("http://104.5.23.196/LoginRegister/signup.php", "POST", field, data)

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        val result = putData.getResult()
                        //End ProgressBar (Set visibility to GONE)
                        //Log.i("PutData", result);
                        if (result == "Sign Up Success") {
                            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, login::class.java)
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