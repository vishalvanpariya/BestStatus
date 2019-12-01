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
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class DownloadScreen : AppCompatActivity() {

    lateinit var status:String
    lateinit var db:Database
    lateinit var menu: Menu
    lateinit var lang:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_screen)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="Status"

        db= Database(this)




        status=intent.getStringExtra("status")
        statustext.text=status
        lang=intent.getStringExtra("lang")



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
//            var layout= (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.dynamicframlayout,null)
//            var text=layout.findViewById<TextView>(R.id.statustext)
//            text.text=status
//            saveBitMap(this,layout)
//            Toast.makeText(this,"Image Saved",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,EditScreen::class.java).putExtra("status",status).putExtra("lang",lang))
        }


        edit.setOnClickListener {
            startActivity(Intent(this,EditScreen::class.java).putExtra("status",status).putExtra("lang",lang))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main, menu)
        this.menu=menu!!
        if (db.exist(status)){
            menu.getItem(0).setIcon(resources.getDrawable(R.drawable.ic_favorite_black_24dp))
        }
        return true
    }

    fun share(item: MenuItem) {
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

    fun addfav(item: MenuItem){
        if (db.exist(status)){
            db.delete(status)
            menu.getItem(0).setIcon(resources.getDrawable(R.drawable.ic_favorite_border_black_24dp))
        }
        else{
            db.insert(status,lang)
            menu.getItem(0).setIcon(resources.getDrawable(R.drawable.ic_favorite_black_24dp))
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
