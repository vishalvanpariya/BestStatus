package com.status.beststatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import android.util.Log
import java.util.*


class Category : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("hindi")
        var list=LinkedList<String>()

        myRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@Category,"Fail to Load Data",Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    list.add(ds.key!!)
                }

            }
        })
    }
}
