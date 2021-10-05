package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {

    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnsignup: Button

    private lateinit var mauth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        supportActionBar?.hide()

        etemail=findViewById(R.id.et_email)
        etpassword=findViewById(R.id.et_password)
        btnlogin=findViewById(R.id.btn_login)
        btnsignup=findViewById(R.id.btn_signup)

        mauth= FirebaseAuth.getInstance()

        btnsignup.setOnClickListener {
            var intent= Intent(this,SignUpPage::class.java)
            startActivity(intent)
        }

        btnlogin.setOnClickListener {
            var email=etemail.text.toString()
            var password=etpassword.text.toString()

            login(email,password)

        }

    }

    private fun login(email: String, password: String) {

       mauth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent=Intent(this@LoginPage,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "User does not exist.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }
}