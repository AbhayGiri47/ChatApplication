package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userlist:ArrayList<User>
    private lateinit var adapter: UserAdapter

    private lateinit var mauth:FirebaseAuth
    private lateinit var mdbref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mauth= FirebaseAuth.getInstance()
        mdbref= FirebaseDatabase.getInstance().getReference()

        userlist= ArrayList()
        adapter=UserAdapter(this,userlist)

        userRecyclerView = findViewById(R.id.userRecyclerVew)
        userRecyclerView.layoutManager=LinearLayoutManager(this)

        userRecyclerView.adapter=adapter

        mdbref.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (postsnapshot in snapshot.children){
                    val currentUser=postsnapshot.getValue(User::class.java)

                    if (mauth.currentUser?.uid != currentUser?.uid){
                        userlist.add(currentUser!!)

                    }
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout){
            mauth.signOut()
            var intent=Intent(this,LoginPage::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}