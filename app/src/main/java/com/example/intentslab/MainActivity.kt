package com.example.intentslab

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnNavigate: Button = findViewById(R.id.btnNavigate)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        val btnSendData: Button = findViewById(R.id.btnSendData)
        val editTextName: EditText = findViewById(R.id.editTextName)

        btnSendData.setOnClickListener {
            val name = editTextName.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("USER_NAME", name) // Passing data
            startActivity(intent)
        }

        val btnOpenWeb:Button = findViewById(R.id.btnOpenWeb)
        btnOpenWeb.setOnClickListener{
            val webpage = Uri.parse("http://www.google.com")
            val intent = Intent(Intent.ACTION_VIEW,webpage)
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }

        val btnDialPhone:Button = findViewById(R.id.btnDialPhone)
        btnDialPhone.setOnClickListener{
            val phoneUri = Uri.parse("tel:1234567890")
            val intent = Intent(Intent.ACTION_DIAL,phoneUri)
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }

        val btnCapturePhoto:Button = findViewById(R.id.btnCapturePhoto)
        btnCapturePhoto.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            } else {
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            }

        }

    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera permission is required!", Toast.LENGTH_SHORT).show()
            }
        }


    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}








