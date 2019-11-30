package com.status.beststatus

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_download_screen.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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

        download.setOnClickListener {
            var layout= (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.dynamicframlayout,null)
            var text=layout.findViewById<TextView>(R.id.statustext)
            text.text=status
            saveBitMap(this,layout)
            Toast.makeText(this,"Image Saved",Toast.LENGTH_SHORT).show()
        }


        edit.setOnClickListener {
            startActivity(Intent(this,EditScreen::class.java).putExtra("status",status).putExtra("lang",lang))
        }
    }

    private fun saveBitMap(context: Context, drawView: View): File? {
        val pictureFileDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "Your Name Facts"
        )
        if (!pictureFileDir.exists()) {
            val isDirectoryCreated = pictureFileDir.mkdirs()
            if (!isDirectoryCreated)
                return null
        }
        val filename = pictureFileDir.path + File.separator  + "${System.currentTimeMillis()}.jpg"
        val pictureFile = File(filename)
        val bitmap = getBitmapFromView(drawView)
        try {
            pictureFile.createNewFile()
            val oStream = FileOutputStream(pictureFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, oStream)
            oStream.flush()
            oStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        scanGallery(context, pictureFile.absolutePath)
        return pictureFile
    }

    private fun scanGallery(cntx: Context, path: String) {
        try {
            MediaScannerConnection.scanFile(
                cntx,
                arrayOf(path),
                null,
                object : MediaScannerConnection.OnScanCompletedListener {
                    override fun onScanCompleted(path: String, uri: Uri) {}
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getBitmapFromView(view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val returnedBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
