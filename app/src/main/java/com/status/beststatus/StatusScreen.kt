package com.status.beststatus

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yuyakaido.android.cardstackview.*
import java.util.*
import androidx.constraintlayout.widget.ConstraintLayout
import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.view.MenuItem
import android.widget.Button


class StatusScreen : AppCompatActivity(),CardStackListener {
    var currentpos=0
    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        var temp=cardStackView.findViewHolderForAdapterPosition(currentpos) as CardStackAdapter.MyHolder
        if (direction.toString().equals("Right")){
            temp.rightsave.alpha=ratio
        }
        else{
            temp.leftdismiss.alpha=ratio
        }
    }

    override fun onCardSwiped(direction: Direction?) {
        Log.d("xxxx","$direction")
    }

    override fun onCardCanceled() {
        var temp=cardStackView.findViewHolderForAdapterPosition(currentpos) as CardStackAdapter.MyHolder
        temp.rightsave.alpha=0F
        temp.leftdismiss.alpha=0F
    }

    override fun onCardAppeared(view: View?, position: Int) {
        currentpos=position
    }

    override fun onCardRewound() {

    }

    lateinit var dialog: ProgressDialog
    lateinit var adapter:CardStackAdapter
    lateinit var cardStackView:CardStackView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_screen)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var key=intent.getStringExtra("key")
        supportActionBar!!.title=key.split("/").last()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(intent.getStringExtra("key"))

        dialog= ProgressDialog(this)
        dialog.setMessage("Please, Wait while loding.")
        dialog.setCancelable(false)
        dialog.show()
        var list=LinkedList<Status>()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                if (dialog.isShowing)
                    dialog.dismiss()
                Toast.makeText(this@StatusScreen,"Fail to Load Data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (ds in dataSnapshot.children) {
                    list.add(Status(ds.getValue(String::class.java)!!))
                }
                if (dialog.isShowing)
                    dialog.dismiss()
                cardStackView = findViewById<CardStackView>(R.id.card_stack_view)
                var cardmanager=CardStackLayoutManager(this@StatusScreen,this@StatusScreen)
                cardmanager.setStackFrom(StackFrom.Bottom)
                cardStackView.layoutManager = cardmanager
                adapter=CardStackAdapter(this@StatusScreen,list)
                cardStackView.adapter=adapter
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    class Status(var status:String, var rightsave:Float= 0F, var leftdismiss:Float=0F)

    class CardStackAdapter(var context: Context,var list:LinkedList<Status>):RecyclerView.Adapter<CardStackAdapter.MyHolder>(){
        lateinit var holderlist:LinkedList<MyHolder>
        init {
            holderlist= LinkedList()
        }
        class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
            lateinit var copy:FrameLayout
            lateinit var copy2:Button
            lateinit var download:FrameLayout
            lateinit var download2:Button
            lateinit var statustext:TextView
            lateinit var rightsave: TextView
            lateinit var leftdismiss: TextView
            lateinit var constint:ConstraintLayout
            init {
                copy=itemview.findViewById(R.id.copybutton)
                copy2=itemview.findViewById(R.id.copybutton2)
                download=itemview.findViewById(R.id.downloadbutton)
                download2=itemview.findViewById(R.id.downloadbutton2)
                statustext=itemview.findViewById(R.id.statustext)
                rightsave=itemview.findViewById(R.id.saveright)
                leftdismiss=itemview.findViewById(R.id.dismissleft)
                constint=itemview.findViewById(R.id.constraint)
            }
        }


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CardStackAdapter.MyHolder {
            var holder=MyHolder(LayoutInflater.from(context).inflate(R.layout.statuscard,parent,false))
            holderlist.add(holder)
            return holder
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: CardStackAdapter.MyHolder, position: Int) {
            holder.statustext.text=list[position].status
            holder.rightsave.alpha= list[position].rightsave
            holder.leftdismiss.alpha=list[position].leftdismiss
            holder.constint.setBackgroundColor(Color.parseColor(getrandomcolor()))
            holder.copy.setOnClickListener {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("status", list[position].status)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context,"Status Copied",Toast.LENGTH_SHORT).show()
            }
            holder.copy2.setOnClickListener {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("status", list[position].status)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context,"Status Copied",Toast.LENGTH_SHORT).show()
            }
            holder.download.setOnClickListener {
                context.startActivity(Intent(context,DownloadScreen::class.java).putExtra("status",list[position].status))
            }
            holder.download2.setOnClickListener {
                context.startActivity(Intent(context,DownloadScreen::class.java).putExtra("status",list[position].status))
            }
        }

        fun getrandomcolor():String{
            var color="#578ECB"
            val rand = Random()
            var rannum=rand.nextInt(12)
            when(rannum){
                0->color="#578ECB"
                1->color="#301934"
                2->color="#013220"
                3->color="#e75480"
                4->color="#FF8C00"
                5->color="#8b0000"
                6->color="#808080"
                7->color="#00008b"
                8->color="#000000"
                9->color="#00FFFF"
                10->color="#800000"
                11->color="#808000"
            }
            return color
        }

    }
}
