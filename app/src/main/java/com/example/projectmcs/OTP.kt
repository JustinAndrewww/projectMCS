package com.example.projectmcs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.projectmcs.databinding.ActivityOtpBinding
import kotlin.random.Random

class OTP : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val phone = intent.getStringExtra("phone")
        val msg = intent.getStringExtra("msg")
        val otp = generateNumber()

        binding.getOTP.setOnClickListener {
            sendMessage(phone.toString(), otp.toString())
        }

        binding.loginBtn.setOnClickListener {
            val inputOTP = binding.etOTP.text.toString()

            if (inputOTP == otp.toString()){
                var intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else if(inputOTP == msg){
                var intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Verification code not match", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun sendMessage(number:String, msg:String){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.SEND_SMS),100)
        }else{
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(number, null, msg, null, null)
            Toast.makeText(this, "SMS has been sent", Toast.LENGTH_SHORT).show()
        }
    }

    fun generateNumber(): String {
        val uniqueNumbers = mutableSetOf<Int>()
        while (uniqueNumbers.size < 4) {
            uniqueNumbers.add(Random.nextInt(0, 10))
        }
        return uniqueNumbers.joinToString("") { it.toString() }
    }
}