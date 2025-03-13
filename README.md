
# Intents Lab App

This Android app demonstrates various intents, such as navigating to a second activity, sending data between activities, opening a web page, dialing a phone number, and capturing a photo using the camera.

## `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="20dp">

    <Button
        android:id="@+id/btnNavigate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Go to Second Activity"
        android:padding="10dp"/>

    <EditText
        android:id="@+id/editTextName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter your name"
        android:padding="10dp"/>

    <Button
        android:id="@+id/btnSendData"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Send Name to Second Activity"
        android:padding="10dp"/>

    <Button
        android:id="@+id/btnOpenWeb"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Open Web Page"
        android:padding="10dp"/>

    <Button
        android:id="@+id/btnDialPhone"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Dial Phone"
        android:padding="10dp"/>

    <Button
        android:id="@+id/btnCapturePhoto"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Capture Photo"
        android:padding="10dp"/>

</LinearLayout>
```

---

## `activity_second.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="20dp">

    <TextView
        android:id="@+id/textViewMessage"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Welcome to Second Activity"
        android:textSize="20sp"
        android:padding="10dp"/>

    <TextView
        android:id="@+id/textViewGreeting"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello!"
        android:textSize="22sp"
        android:textStyle="bold"
        android:gravity="center"
        android:padding="10dp" />

</LinearLayout>
```

---

## `MainActivity.kt`

```kotlin
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

        // 🚀 SECTION 2: Sending Data to SecondActivity
        val btnSendData: Button = findViewById(R.id.btnSendData)
        val editTextName: EditText = findViewById(R.id.editTextName)

        btnSendData.setOnClickListener {
            val name = editTextName.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("USER_NAME", name) // Passing data
            startActivity(intent)
        }

        // 🚀 SECTION 3: Open a Web Page in Browser
        val btnOpenWeb: Button = findViewById(R.id.btnOpenWeb)
        btnOpenWeb.setOnClickListener {
            val webpage = Uri.parse("http://www.google.com")
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        // 🚀 SECTION 4: Dial a Phone Number
        val btnDialPhone: Button = findViewById(R.id.btnDialPhone)
        btnDialPhone.setOnClickListener {
            val phoneUri = Uri.parse("tel:1234567890")
            val intent = Intent(Intent.ACTION_DIAL, phoneUri)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        // 🚀 SECTION 5: Capture Photo Using Camera
        val btnCapturePhoto: Button = findViewById(R.id.btnCapturePhoto)
        btnCapturePhoto.setOnClickListener {
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
```

---

## `SecondActivity.kt`

```kotlin
package com.example.intentslab

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // ✅ SECTION 1: Initialize UI Elements
        val textViewGreeting: TextView = findViewById(R.id.textViewGreeting)
        val name = intent.getStringExtra("USER_NAME")

        if (name != null) {
            textViewGreeting.text = "Hello, $name!"
        } else {
            textViewGreeting.text = "Hello, Guest!" // Default greeting if no name is passed
        }

        // ✅ SECTION 3: Handle Custom Intent Filter
        val action = intent.action

        if (action == "com.example.MY_CUSTOM_ACTION") {
            // If launched with custom intent, update the TextView with a different message
            textViewGreeting.text = "Activity opened via Custom Intent!"
        }
    }
}
```

---

## `AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- ✅ SECTION 1: Declare Implicit Intent Queries -->
    <queries>
        <intent>
            <action android:name="android.intent.action.VIEW"/>
            <data android:scheme="http"/>
        </intent>
        <intent>
            <action android:name="android.intent.action.VIEW"/>
            <data android:scheme="https"/>
        </intent>

        <intent>
            <action android:name="android.intent.action.DIAL"/>
        </intent>

        <intent>
            <action android:name="android.media.action.IMAGE_CAPTURE"/>
        </intent>
    </queries>

    <!-- ✅ SECTION 2: Declare Camera Permissions & Features -->
    <uses-feature android:name="android.hardware.camera" android:required="true"/>
    <uses-permission android:name="android.permission.CAMERA"/>

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.IntentsLab"
        tools:targetApi="35">

        <!-- ✅ SECTION 3: Declare SecondActivity with a Custom Intent Filter -->
        <activity
            android:name=".SecondActivity"
            android:exported="false">
            <intent-filter>
                <action android:name="com.example.MY_CUSTOM_ACTION"/>
                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
        </activity>

        <!-- ✅ SECTION 4: Declare MainActivity (App Entry Point) -->
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>

    </application>

</manifest>
```

---

This is a simple application demonstrating basic Android intents, including implicit intents to open a webpage, dial a phone number, and capture an image, as well as sending and receiving data between activities.

### Features:
- **Activity Navigation**: Navigate between activities.
- **Data Transfer**: Send data (user's name) between activities.
- **Implicit Intents**: Open web pages, dial a phone number, and capture images.
- **Permissions**: Handle camera permissions to capture images.

---

Feel free to clone the repository and start experimenting with the intents!
