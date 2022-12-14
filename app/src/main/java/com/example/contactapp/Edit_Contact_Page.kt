package com.example.contactapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.contactapp.databinding.ActivityEditContactPageBinding
import com.google.gson.Gson

class Edit_Contact_Page : AppCompatActivity() {
    private lateinit var dataBinding: ActivityEditContactPageBinding
    private lateinit var factory: ContactVMFactory
    private lateinit var viewModel: ContactViewModel
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_contact_page)
        factory = ContactVMFactory(ContactDBRepository(this))
        viewModel = ViewModelProvider(this, factory)[ContactViewModel::class.java]

        val intentData = intent.getStringExtra(Keys.CONTACT)
        contact = Gson().fromJson(intentData, Contact::class.java)

        dataBinding.etEdFname.setText(contact.fname)
        dataBinding.etEdLname.setText(contact.lname)
        dataBinding.etEdNumber.setText(contact.mobNo)

        dataBinding.btnUpdate.setOnClickListener {

            viewModel.updateContact(
                dataBinding.etEdFname.text.toString(), dataBinding.etEdLname.text.toString(),
                dataBinding.etEdNumber.text.toString(), contact.id
            )

            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_contact_menu_opton, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ed_edit -> {
                dataBinding.etEdFname.isFocusableInTouchMode = true
                dataBinding.etEdLname.isFocusableInTouchMode = true
                dataBinding.etEdNumber.isFocusableInTouchMode = true
                dataBinding.btnUpdate.visibility = View.VISIBLE
            }
            R.id.ed_delete -> {
                viewModel.deleteSingleContact(contact.id)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}