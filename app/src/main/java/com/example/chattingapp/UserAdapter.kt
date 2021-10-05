package com.example.chattingapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class UserAdapter(val context:Context,val userlist:ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)
        return UserViewholder(view)
    }

    override fun onBindViewHolder(holder: UserViewholder, position: Int) {
        val currentUser=userlist[position]
        holder.textname.text=currentUser.name

        holder.itemView.setOnClickListener {
            val intent= Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)

            context.startActivity(intent)
        }

    }

    override fun getItemCount()=userlist.size

    class UserViewholder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var textname=itemView.findViewById<TextView>(R.id.txt_name)
    }
}