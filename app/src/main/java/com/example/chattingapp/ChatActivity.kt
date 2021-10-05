package com.example.chattingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messagebox:EditText
    private lateinit var sendbutton:ImageView
    private lateinit var messageAdapter:MessageAdapter
    private lateinit var messagelist:ArrayList<Message>
    private lateinit var mdbref:DatabaseReference

    var receiverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageRecyclerView=findViewById(R.id.chatrecyclerview)
        messagebox=findViewById(R.id.messagebox)
        sendbutton=findViewById(R.id.sendbtn)
        messagelist=ArrayList()
        messageAdapter= MessageAdapter(this,messagelist)
        mdbref=FirebaseDatabase.getInstance().getReference()

        messageRecyclerView.layoutManager=LinearLayoutManager(this)
        messageRecyclerView.adapter=messageAdapter

        val name=intent.getStringExtra("name")
        val Receiveruid=intent.getStringExtra("uid")
        val SenderUid=FirebaseAuth.getInstance().currentUser?.uid

        receiverRoom=SenderUid+Receiveruid
        senderRoom=Receiveruid+SenderUid

        supportActionBar?.title=name

        // Adding message to Database
        sendbutton.setOnClickListener {
            val message=messagebox.text.toString()
            val messageobject=Message(message,SenderUid)
            mdbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageobject).addOnSuccessListener {
                    mdbref.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageobject)
                }
            messagebox.setText("")
        }

        // Logic for adding data into Recycler View

        mdbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messagelist.clear()

                    for (postsnapshot in snapshot.children){
                        val message=postsnapshot.getValue(Message::class.java)
                        messagelist.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


    }
}