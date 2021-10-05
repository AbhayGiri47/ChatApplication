package com.example.chattingapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MessageAdapter(val context: Context,val messagelist: ArrayList<Message> ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE=1
    val ITEM_SENT=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            //inflate receive
            val view= LayoutInflater.from(parent.context).inflate(R.layout.receive,parent,false)
            return receiveviewholder(view)
        }else{
            //inflate sent
            val view= LayoutInflater.from(parent.context).inflate(R.layout.sent,parent,false)
            return sentviewholder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var currentmessage=messagelist[position]

        if (holder.javaClass==sentviewholder::class.java){
            //do stuff for receive view holder
            val viewholder=holder as sentviewholder
            holder.sentmessage.text=currentmessage.message

        }else{
            //do stuff for receive view holder
            val viewholder=holder as receiveviewholder
            holder.receivemessage.text=currentmessage.message
        }



    }

    override fun getItemViewType(position: Int): Int {
        var currentmessage=messagelist[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderid)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount()=messagelist.size

    class sentviewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        var sentmessage=itemView.findViewById<TextView>(R.id.sentmesage)
    }

    class receiveviewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        var receivemessage=itemView.findViewById<TextView>(R.id.receivedmesage)

    }
}