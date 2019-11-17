package com.status.beststatus.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.status.beststatus.Category
import com.status.beststatus.R

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        var hindi=root.findViewById<Button>(R.id.hindi)
        var english=root.findViewById<Button>(R.id.english)
        var fav=root.findViewById<Button>(R.id.fav)

        hindi.setOnClickListener {
            startActivity(Intent(activity,Category::class.java))
        }

        english.setOnClickListener {

        }

        fav.setOnClickListener {

        }

        return root
    }
}