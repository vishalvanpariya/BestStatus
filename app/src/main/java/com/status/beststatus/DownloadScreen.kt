package com.status.beststatus

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_download_screen.*
import android.content.Intent
import android.view.MenuItem


class DownloadScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_screen)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="Status"

        var status=intent.getStringExtra("status")
        statustext.text=status
        var lang=intent.getStringExtra("lang")

        copy.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("status", status)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this,"Status Copied", Toast.LENGTH_SHORT).show()
        }

        share.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, status)
            startActivity(
                Intent.createChooser(
                    sharingIntent,
                    "Share Status"
                )
            )
        }


        edit.setOnClickListener {
            startActivity(Intent(this,EditScreen::class.java).putExtra("status",status).putExtra("lang",lang))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
