package com.example.projectmcs

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.projectmcs.databinding.ActivityLoginPageBinding
import kotlin.random.Random

class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnLogin.setOnClickListener{
            val loginUsername = binding.etUsername.text.toString()
            val loginPassword = binding.etPassword.text.toString()
            loginDatabase(loginUsername, loginPassword)
        }

        binding.textSignUp.setOnClickListener {
            var intent= Intent(this,SignupPage::class.java)
            startActivity(intent)
        }
    }

    private fun loginDatabase(username: String, password: String){
        val userExists = dbHelper.findUserByUsername(username)
        Log.d("data",userExists.toString())
        if(userExists){
            val cpassword = dbHelper.getPasswordByUsername(username)
            Log.d("password",cpassword.toString())
            if (cpassword == password){
                Log.d("masuk","data benar")
                var phone = dbHelper.getPhoneByUsername(username)
                dbHelper.getUserDetails(username, password)
                val msg = generateNumber()

                sendMessage(phone.toString(), msg)


                var intent= Intent(this,OTP::class.java)
                intent.putExtra("msg", msg)
                intent.putExtra("phone", phone)
                startActivity(intent)

            }

        }else{
            Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
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