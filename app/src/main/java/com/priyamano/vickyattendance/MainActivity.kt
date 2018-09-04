package com.priyamano.vickyattendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(data.rollno==null) {
            startActivity(Intent(applicationContext, login::class.java))
            finish()
        }else{
            startActivity(Intent(applicationContext, home::class.java))
            finish()
        }


    }
}
