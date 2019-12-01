package com.status.beststatus.ui.favorites


import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.status.beststatus.*
import com.status.beststatus.R

import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.fragment_favourites.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class favourites : Fragment(), CardStackListener {
    lateinit var t: CardStackAdapter.MyHolder
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
        t=temp
    }

    override fun onCardSwiped(direction: Direction?) {
        var temp=t
        if (direction.toString().equals("Left")) {
            db.delete(temp.statustext.text.toString())
        }
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


    lateinit var adapter: CardStackAdapter
    lateinit var cardStackView: CardStackView
    lateinit var db: Database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_favourites, container, false)
        db= Database(context)

        var list =db.getfav()
        if (list.size==0){
            var msg=view.findViewById<TextView>(R.id.msg)
            msg.visibility=View.VISIBLE
        }
        else {
            cardStackView = view.findViewById<CardStackView>(R.id.card_stack_view)
            var cardmanager = CardStackLayoutManager(context, this)
            cardmanager.setStackFrom(StackFrom.Bottom)
            cardStackView.layoutManager = cardmanager
            adapter = CardStackAdapter(context!!, list)
            cardStackView.adapter = adapter
        }

        return view
    }

    class CardStackAdapter(var context: Context, var list: LinkedList<Status>):
        RecyclerView.Adapter<CardStackAdapter.MyHolder>(){
        lateinit var holderlist: LinkedList<MyHolder>
        init {
            holderlist= LinkedList()
        }
        class MyHolder(itemview:View): RecyclerView.ViewHolder(itemview) {
            lateinit var copy: FrameLayout
            lateinit var copy2: Button
            lateinit var download: FrameLayout
            lateinit var download2: Button
            lateinit var statustext: TextView
            lateinit var rightsave: TextView
            lateinit var leftdismiss: TextView
            lateinit var constint: ConstraintLayout
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
            holder.rightsave.text="Next"
            holder.statustext.text=list[position].status
            holder.rightsave.alpha= list[position].rightsave
            holder.leftdismiss.alpha=list[position].leftdismiss
            holder.constint.setBackgroundColor(Color.parseColor(getrandomcolor()))
            holder.copy.setOnClickListener {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("status", list[position].status)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context,"Status Copied", Toast.LENGTH_SHORT).show()
            }
            holder.copy2.setOnClickListener {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("status", list[position].status)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context,"Status Copied", Toast.LENGTH_SHORT).show()
            }
            holder.download.setOnClickListener {
                var intent= Intent(context, DownloadScreen::class.java)
                intent.putExtra("status",list[position].status)
                intent.putExtra("lang",list[position].lang)
                context.startActivity(intent)
            }
            holder.download2.setOnClickListener {
                var intent= Intent(context, DownloadScreen::class.java)
                intent.putExtra("status",list[position].status)
                intent.putExtra("lang",list[position].lang)
                context.startActivity(intent)
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
