package com.status.beststatus.ui.submitstatus


import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.firebase.database.FirebaseDatabase
import com.status.beststatus.R


/**
 * A simple [Fragment] subclass.
 */
class submitstatus : Fragment() {

    lateinit var dialog: ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user shayari")

        var view= inflater.inflate(R.layout.fragment_submitstatus, container, false)

        var submit=view.findViewById<Button>(R.id.submit)
        var name=view.findViewById<EditText>(R.id.name)
        var email=view.findViewById<EditText>(R.id.email)
        var shayari =view.findViewById<EditText>(R.id.shayari)

        submit.setOnClickListener {
            dialog= ProgressDialog(context)
            dialog.setMessage("Please, Wait while loding.")
            dialog.setCancelable(false)
            dialog.show()
            if (name.text.isEmpty()){
                if (dialog.isShowing)
                    dialog.dismiss()
                name.error="Please Enter Name"
                return@setOnClickListener
            }
            if (email.text.isEmpty()){
                if (dialog.isShowing)
                    dialog.dismiss()
                email.error="Please Enter Email"
                return@setOnClickListener
            }
            if (shayari.text.isEmpty()){
                if (dialog.isShowing)
                    dialog.dismiss()
                shayari.error="Please Enter Shayari"
                return@setOnClickListener
            }

            var map=HashMap<String,String>()
            map.put("name",name.text.toString())
            map.put("email",email.text.toString())
            map.put("shayari",shayari.text.toString())

            myRef.push().setValue(map).addOnSuccessListener {
                if (dialog.isShowing)
                    dialog.dismiss()
                Toast.makeText(context,"Thank You for Shayari Submit",Toast.LENGTH_SHORT).show()
                name.text.clear()
                email.text.clear()
                shayari.text.clear()
            }
        }

        return view
    }


}
