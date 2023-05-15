package com.example.androidfirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class Login : AppCompatActivity() {


    lateinit var etUsername: EditText // we used lateinit to initialize our variable
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        etUsername = findViewById(R.id.etRUserName)// binding to the layout
        etPassword = findViewById(R.id.etRPassword)
        btnLogin = findViewById(R.id.btnLogin)

        this.findViewById<TextView>(R.id.deLoginLink).setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        btnLogin.setOnClickListener {
            val Name = etUsername.getText().toString();
            LoginUser(etUsername, etPassword);//, Toast.LENGTH_SHORT).show();

        }
    }

    private fun LoginUser(etUsername:EditText, etPassword:EditText) {
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
            .checkUser(User(userName, password))

        call.enqueue(object : Callback<ResponseBody?> {

            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                var s = ""
                try {
                    s = response.body()!!.string()
                } catch (e: IOException) {
                    e.printStackTrace()
                }


                if (s == userName) {

                    val intent = Intent(this@Login,Dashboard::class.java)
                    intent.putExtra("Username",s)

                    Toast.makeText(
                        this@Login,
                        "Successfully login",
                        Toast.LENGTH_LONG
                    ).show()


                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "User does not exists!", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()
            }


        })
    }

}