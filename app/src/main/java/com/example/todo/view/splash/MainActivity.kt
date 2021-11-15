package com.example.todo.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.todo.R
import com.example.todo.view.display.ShowActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         Handler(Looper.getMainLooper()).postDelayed({
           startActivity(Intent(this@MainActivity, ShowActivity::class.java))
           finish()
          }, 3000)
    }
}