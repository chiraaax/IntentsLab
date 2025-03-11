package com.example.intentslab

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        val textViewGreeting: TextView = findViewById(R.id.textViewGreeting)
        val name = intent.getStringExtra("USER_NAME")

        if (name != null) {
            textViewGreeting.text = "Hello, $name!"
        }else{
            textViewGreeting.text="Hello,Guest!"
        }

        val action = intent.action

        if(action == "com.example.MY_CUSTIOM_ACTION"){
            textViewGreeting.text = "Activity opened via Custom Intent!"
        }
    }
}
