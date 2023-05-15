package com.example.androidfirst

//import android.R
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Dashboard : AppCompatActivity() {


    lateinit var welcomeText:String
    lateinit var deWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)



        welcomeText ="Welcome "+ getIntent().getStringExtra("Username").toString() + "!";
        deWelcome = this.findViewById(R.id.deWelcome);
        deWelcome.setText(welcomeText);

//        val welcomeText = "Welcome " + intent.getStringExtra("Username") + "!"
//        val deWelcome = findViewById<TextView>(R.id.deWelcome);
//        deWelcome.setText(welcomeText);
    }
}