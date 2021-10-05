package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpPage : AppCompatActivity() {

    private lateinit var etname: EditText
    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText
    private lateinit var btnsignup: Button

    private lateinit var mauth: FirebaseAuth
    private lateinit var mdbref:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        supportActionBar?.hide()


        etname=findViewById(R.id.et_name)
        etemail=findViewById(R.id.et_email)
        etpassword=findViewById(R.id.et_password)
        btnsignup=findViewById(R.id.btn_signup)

        mauth= FirebaseAuth.getInstance()


        btnsignup.setOnClickListener {
            var name=etname.text.toString()
            var email=etemail.text.toString()
            var password=etpassword.text.toString()

            signup(name,email,password)
        }
    }

    private fun signup(name:String,email: String, password: String) {

        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addusertodatabase(name,email,mauth.currentUser?.uid!!)
                    val intent=Intent(this@SignUpPage,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Error Ocurred...",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addusertodatabase(name: String, email: String, uid: String) {
        mdbref=FirebaseDatabase.getInstance().getReference()
        mdbref.child("user").child(uid).setValue(User(name, email, uid))
    }
}