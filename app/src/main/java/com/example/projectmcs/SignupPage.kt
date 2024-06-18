package com.example.projectmcs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.projectmcs.databinding.ActivitySignupPageBinding

class SignupPage : AppCompatActivity() {

    private lateinit var binding: ActivitySignupPageBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        dbHelper = DBHelper(this)

        fun validate(): Boolean{
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val cpassword = binding.etConfirmPassword.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            val radioMale = binding.radioMale
            val radioFemale = binding.radioFemale

            if(username.isEmpty()){
                Toast.makeText(this, "username must be filled", Toast.LENGTH_SHORT).show()
                return false
            } else if(password.isEmpty()){
                Toast.makeText(this, "password must be filled", Toast.LENGTH_SHORT).show()
                return false
            }else if(cpassword.isEmpty()){
                Toast.makeText(this, "confirm password must be filled", Toast.LENGTH_SHORT).show()
                return false
            }else if(email.isEmpty()){
                Toast.makeText(this, "email must be filled", Toast.LENGTH_SHORT).show()
                return false
            }else if(phone.isEmpty()){
                Toast.makeText(this, "phone number must be filled", Toast.LENGTH_SHORT).show()
                return false
            }

            if (dbHelper.findUserByUsername(username)) {
                Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show()
                return false
            }

            if(password.length < 8){
                Toast.makeText(this, "Password must be at least 8 character ", Toast.LENGTH_SHORT).show()
                return false
            }

            if(!password.equals(cpassword)){
                Toast.makeText(this, "password do not match", Toast.LENGTH_SHORT).show()
                return false
            }

            var validateEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            if(!validateEmail || !email.endsWith("@puff.com")){
                Toast.makeText(this, "Email must ends with '@puff.com' ", Toast.LENGTH_SHORT).show()
                return false
            }

            var validatePhone = Patterns.PHONE.matcher(phone).matches()
            var len = phone.length
            if(!validatePhone || len<11 || len>13){
                Toast.makeText(this, "Phone number must between 11 to 13 character", Toast.LENGTH_SHORT).show()
                return false
            }else if(phone.isEmpty()){
                Toast.makeText(this, "phone number must be filled", Toast.LENGTH_SHORT).show()
                return false
            }

            if(radioMale.isChecked || radioFemale.isChecked){
                return true
            }else{
                Toast.makeText(this, "please check one", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }

        binding.btnRegister.setOnClickListener{
            if(validate()){
                val username = binding.etUsername.text.toString()
                val password = binding.etPassword.text.toString()
                val email= binding.etEmail.text.toString()
                val phone = binding.etPhone.text.toString()

                signupDatabase(username, password, email, phone)
//                val phoneInt = phone.toIntOrNull()
//                if (phoneInt != null) {
//
//                } else {
//                    Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
//                }

            }
        }

        binding.textLogin.setOnClickListener {
            var intent= Intent(this,LoginPage::class.java)
            startActivity(intent)
        }
    }

    private fun signupDatabase(username: String, password: String, email: String, phone: String){
        val insertedRowId = dbHelper.insertUser(username, password, email, phone)
        if(insertedRowId != -1L){
            Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show()
        }
    }


}