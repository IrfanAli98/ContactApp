package com.example.contactapp.`interface`

import com.example.contactapp.Contact
import java.text.FieldPosition

interface OnItemClickListener {
    fun onItemClick(contact:Contact, position: Int)
}