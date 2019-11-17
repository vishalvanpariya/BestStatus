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
import android.app.ProgressDialog
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.MenuItem
import androidx.cardview.widget.CardView
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction


class Category : AppCompatActivity() {

    lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var key=intent.getStringExtra("key")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(key)
        var list=LinkedList<String>()

        dialog=ProgressDialog(this)
        dialog.setMessage("Please, Wait while loding.")
        dialog.setCancelable(false)
        dialog.show()

        myRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                if (dialog.isShowing)
                    dialog.dismiss()
                Toast.makeText(this@Category,"Fail to Load Data",Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (ds in dataSnapshot.children) {
                    list.add(ds.key!!)
                }
                catrecycler.adapter=CatAdapter(context = this@Category,list = list,key = key)
                catrecycler.layoutManager=LinearLayoutManager(this@Category)
                if (dialog.isShowing)
                    dialog.dismiss()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    class CatAdapter(var context: Context,var list: LinkedList<String>,var key:String):RecyclerView.Adapter<CatAdapter.Holder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatAdapter.Holder {
            return Holder(LayoutInflater.from(context).inflate(R.layout.categoryitem,parent,false))
        }

        class Holder(itemview:View): RecyclerView.ViewHolder(itemview){
            lateinit var text:TextView
            lateinit var cardView: CardView
            init {
                text=itemview.findViewById(R.id.categorytext)
                cardView=itemview.findViewById(R.id.itemcard)
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: CatAdapter.Holder, position: Int) {
            holder.text.text=list[position]
            holder.cardView.setOnClickListener {
                context.startActivity(Intent(context,StatusScreen::class.java).putExtra("key","$key/${list[position]}"))
            }
        }

    }
}
