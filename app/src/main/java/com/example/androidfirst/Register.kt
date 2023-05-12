package com.example.androidfirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText;
import android.widget.TextView
import android.widget.Toast

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class Register : AppCompatActivity() {

    private lateinit var etUsername: EditText // we used lateinit to initialize our variable
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etRUserName)// binding to the layout
        etPassword = findViewById(R.id.etRPassword)
        btnRegister = findViewById(R.id.btnRegister)

        //redirect you to login page if already have an account this is a routing link
        this.findViewById<TextView>(R.id.deLoginLink).setOnClickListener{
            startActivity(Intent(this, Login::class.java))
        }

        btnRegister.setOnClickListener {
            val Name = etUsername.getText().toString();
            // Toast.makeText(this, Name+"has been registered",Toast.LENGTH_SHORT).show();
            registrationUser(this, Name + "has been registered");


        }

    }

    private fun registrationUser(register: Register, s: String) {
        val userName: String = etUsername.getText().toString().trim()
        val password: String = etPassword.getText().toString().trim()


        if (userName.isEmpty()) {
            etUsername.setError("Username is required")
            etUsername.requestFocus()
            return

        } else if (password.isEmpty()) {
            etPassword.setError("Password is required")
            etPassword.requestFocus()
            return
        };

        val call: Call<ResponseBody> = RetrofitClient
            .getInstance()
            .api
            .createUser(User(userName, password))

        call.enqueue(object : Callback<ResponseBody?> {

            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                var s = ""
                try {
                    s = response.body()!!.string()
                }

                catch (e: IOException) {
                    e.printStackTrace()
                }

                if (s == "SUCCESS") {
                    Toast.makeText(
                        this@Register,
                        "Successfully registered. Please login",
                        Toast.LENGTH_LONG
                    ).show()


                    startActivity(Intent(this@Register, Login::class.java))
                } else {
                    Toast.makeText(this@Register, "User already exists!", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@Register, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }


}




