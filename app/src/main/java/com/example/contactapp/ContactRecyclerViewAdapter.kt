package com.example.contactapp

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.contactapp.`interface`.OnItemClickListener
import com.example.contactapp.databinding.LayoutCustomViewBinding

class ContactRecyclerViewAdapter(val context: Context, private var contactList: List<Contact>, private val onItemClickListener: OnItemClickListener):Adapter<ContactRecyclerViewAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(var layoutCustomViewBinding: LayoutCustomViewBinding): RecyclerView.ViewHolder(layoutCustomViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var layoutCustomViewBinding:LayoutCustomViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.layout_custom_view, parent, false)
        return CustomViewHolder(layoutCustomViewBinding)    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var contacts = contactList.get(position)
        holder.layoutCustomViewBinding.tvFname.text = contacts.fname
        holder.layoutCustomViewBinding.tvLname.text = contacts.lname
        holder.layoutCustomViewBinding.tvNumber.text = contacts.mobNo

        holder.layoutCustomViewBinding.btnDial.setOnClickListener{
            val mobileNo:String = contacts.mobNo
            val intent = Intent(ACTION_DIAL, Uri.parse("tel:$mobileNo"))
            context.startActivity(intent)
        }
        holder.layoutCustomViewBinding.btnCall.setOnClickListener{
            val mobileNo:String = contacts.mobNo
            val intent = Intent(ACTION_CALL, Uri.parse("tel:$mobileNo"))
            context.startActivity(intent)

        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(contacts, position)
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}

