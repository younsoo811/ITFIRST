package com.example.itfirst

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {

    var tv_sender: TextView? = null
    var tv_date: TextView? = null
    var tv_content: TextView? = null

    private val REQUEST_SMS = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //런타임 퍼미션 체크
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECEIVE_SMS), REQUEST_SMS)
        }

        tv_sender = findViewById(R.id.senderText)
        tv_date = findViewById(R.id.receivedDateText)
        tv_content = findViewById(R.id.contentsText)

        val intent = intent
        processCommand(intent)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        processCommand(intent)
    }

    private fun processCommand(intent: Intent?) {
        if (intent != null) {
            val sender = intent.getStringExtra("sender")
            val date = intent.getStringExtra("date")
            val content = intent.getStringExtra("content")
            tv_sender!!.text = sender
            tv_date!!.text = date
            tv_content!!.text = content
        }
    }

}