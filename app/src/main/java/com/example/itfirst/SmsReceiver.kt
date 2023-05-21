package com.example.itfirst

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


class SmsReceiver : BroadcastReceiver() {

    private val TAG = "SmsReceiver"

    override fun onReceive(context: Context?, intent: Intent) {
        Log.d(TAG, "onReceive() called")
        val bundle = intent.extras
        val messages = parseSmsMessage(bundle)
        if (messages.size > 0) {
            val sender = messages[0]!!.originatingAddress
            val content = messages[0]!!.messageBody.toString()
            val date = Date(messages[0]!!.timestampMillis)
            Log.d(TAG, "sender: $sender")
            Log.d(TAG, "content: $content")
            Log.d(TAG, "date: $date")

            sendToActivity(context!!, sender!!, content, date);
        }
    }

    private fun parseSmsMessage(bundle: Bundle?): Array<SmsMessage?> {
        // PDU: Protocol Data Units
        val objs = bundle!!["pdus"] as Array<Any>?
        val messages = arrayOfNulls<SmsMessage>(
            objs!!.size
        )
        for (i in objs.indices) {
            messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray)
        }
        return messages
    }

    private val format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private fun sendToActivity(context: Context, sender: String, content: String, date: Date) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
        )
        intent.putExtra("sender", sender)
        intent.putExtra("content", content)
        intent.putExtra("date", format.format(date))
        context.startActivity(intent)
    }
}