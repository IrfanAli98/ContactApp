package com.example.contactapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

// TODO: 1.Repository created after taking an empty activity
class ContactDBRepository(private val context: Context) {
    // TODO:3. requirement for Database
    private val DB_NAME: String = "ContactDataBase"
    private val TABLE_NAME:String = "Contact_List"
    private val DB_VERSION:Int = 1

    private val SR_NO: String = "SRNO"
    private val FNAME: String = "FNAME"
    private val LNAME: String = "LNAME"
    private val MOB_NO: String = "MOBNO"

    // TODO: 4. query to Create Table
    private val CREATE_TABLE = buildString {
        append("CREATE TABLE ")
        append(TABLE_NAME)
        append("(")
        append(SR_NO)
        append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
        append(FNAME)
        append(" TEXT, ")
        append(LNAME)
        append(" TEXT, ")
        append(MOB_NO)
        append(" TEXT)")
    }

    // TODO: function to save the Contacts from Table
    fun saveContact(fname:String, lname:String, mobNo:String){
        val contentValues=ContentValues()
        contentValues.put(FNAME, fname)
        contentValues.put(LNAME, lname)
        contentValues.put(MOB_NO, mobNo)

        val id: Long= sqLiteDatabase.insert(TABLE_NAME, null, contentValues)
        if (id.equals("-1")){
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Successfully Inserted", Toast.LENGTH_SHORT).show()
        }
    }

    // TODO: function to get the Contacts from Table

    fun getContacts():List<Contact>{
        var contactList = ArrayList<Contact>()
        val columns= arrayOf(SR_NO, FNAME, LNAME, MOB_NO)
        val cursor: Cursor =sqLiteDatabase.query(TABLE_NAME,columns,null, null,null, null,null)
        if(cursor.moveToNext()){
            do {
                val id = cursor.getInt(0)
                val fname = cursor.getString(1)
                val lname = cursor.getString(2)
                val mobNo = cursor.getString(3)
                val contact = Contact(id, fname, lname, mobNo)
                contactList.add(contact)

            }while (cursor.moveToNext())
        }else{
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
        }
        return contactList
    }

    fun updateContact(fname: String, lname: String, mobNo: String, srNo: Int){
        val contentValues = ContentValues()
        contentValues.put(FNAME, fname)
        contentValues.put(LNAME, lname)
        contentValues.put(MOB_NO,mobNo)

        val Id: Int = sqLiteDatabase.update(TABLE_NAME, contentValues, "$SR_NO=$srNo", null)
        if(Id>0){
            Toast.makeText(context, "$Id record Updated Successfully", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }


    // TODO: for deleting the single record
    fun deleteSingleContact(srNo: Int){
        val Id : Int= sqLiteDatabase.delete(TABLE_NAME, "$SR_NO = $srNo", null)
        if(Id>0){
            Toast.makeText(context, "$Id Contact Deleted Successfully", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    // TODO: for deleting all record at once
    fun deleteAllContact(){
        val Id : Int= sqLiteDatabase.delete(TABLE_NAME, null, null)
        if(Id>0){
            Toast.makeText(context, "$Id Contact Deleted Successfully", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    // TODO: 5. inner class object created because ViewModel only access outer class not inner class
    // TODO: it will initialize the OpenHelperClass as having Read and Write database Facility
    var myDB=MyDB(context)
    var sqLiteDatabase: SQLiteDatabase = myDB.writableDatabase

    // TODO: 2. Open helper class created so that can Create or upgrade DataBase
    inner class MyDB(context: Context):SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION){
        override fun onCreate(sqlDB: SQLiteDatabase?) {
            sqlDB?.let {
                it.execSQL(CREATE_TABLE)
            }
        }

        override fun onUpgrade(sqlDB: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("Not yet implemented")
        }
    }
}