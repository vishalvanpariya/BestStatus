package com.status.beststatus

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_category.*
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
                catrecycler.adapter=CatAdapter(context = this@Category,list = list)
                catrecycler.layoutManager=LinearLayoutManager(this@Category)
            }
        })
    }

    class CatAdapter(var context: Context,var list: LinkedList<String>):RecyclerView.Adapter<CatAdapter.Holder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatAdapter.Holder {
            return Holder(LayoutInflater.from(context).inflate(R.layout.categoryitem,parent,false))
        }

        class Holder(itemview:View): RecyclerView.ViewHolder(itemview){
            lateinit var text:TextView
            init {
                text=itemview.findViewById(R.id.categorytext)
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: CatAdapter.Holder, position: Int) {
            holder.text.text=list[position]
        }

    }
}
