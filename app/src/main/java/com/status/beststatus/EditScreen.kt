package com.status.beststatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import kotlinx.android.synthetic.main.activity_download_screen.statustext
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import android.content.Context
import android.graphics.Typeface
import java.io.IOException
import android.media.MediaScannerConnection
import android.net.Uri
import kotlinx.android.synthetic.main.activity_edit_screen.*
import android.util.DisplayMetrics
import android.util.Log
import android.app.AlertDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import android.app.Dialog
import android.view.Gravity
import android.widget.SeekBar
import android.widget.Toast
import com.skydoves.colorpickerview.ColorPickerDialog




class EditScreen : AppCompatActivity() {
    var temp:Int=0
    var temp2:Int=0
    var temp3=0
    var size=12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_edit_screen)
        supportActionBar!!.hide()

        var status=intent.getStringExtra("status")
        statustext.text=status

        save.setOnClickListener {
            saveBitMap(this,framlayout)
            Toast.makeText(this,"Image Saved", Toast.LENGTH_SHORT).show()
        }

        gradiant.setOnClickListener {
            temp2++
            if (temp2==13)
                temp2=1
            when(temp2){
                1-> framlayout.setBackgroundColor(Color.parseColor("#FF0000"))
                2->framlayout.setBackgroundColor(Color.parseColor("#FFD700"))
                3->framlayout.setBackgroundColor(Color.parseColor("#FF8C00"))
                4->framlayout.setBackgroundColor(Color.parseColor("#00FF00"))
                5->framlayout.setBackgroundColor(Color.parseColor("#006400"))
                6->framlayout.setBackgroundColor(Color.parseColor("#00FA9A"))
                7->framlayout.setBackgroundColor(Color.parseColor("#008080"))
                8->framlayout.setBackgroundColor(Color.parseColor("#0000CD"))
                9->framlayout.setBackgroundColor(Color.parseColor("#191970"))
                10->framlayout.setBackgroundColor(Color.parseColor("#FF00FF"))
                11->framlayout.setBackgroundColor(Color.parseColor("#800080"))
                12->framlayout.setBackgroundColor(Color.parseColor("#000000"))
            }
        }

        color.setOnClickListener {
            temp++
            if (temp==13)
                temp=1
            when(temp){
                1->framlayout.background=getDrawable(R.drawable.bg1)
                2->framlayout.background=getDrawable(R.drawable.bg2)
                3->framlayout.background=getDrawable(R.drawable.bg3)
                4->framlayout.background=getDrawable(R.drawable.bg4)
                5->framlayout.background=getDrawable(R.drawable.bg5)
                6->framlayout.background=getDrawable(R.drawable.bg6)
                7->framlayout.background=getDrawable(R.drawable.bg7)
                8->framlayout.background=getDrawable(R.drawable.bg8)
                9->framlayout.background=getDrawable(R.drawable.bg9)
                10->framlayout.background=getDrawable(R.drawable.bg10)
                11->framlayout.background=getDrawable(R.drawable.bg11)
                12->framlayout.background=getDrawable(R.drawable.bg12)
            }

        }

        back.setOnClickListener {
            onBackPressed()
        }

        colorpicker.setOnClickListener {
            ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle("ColorPicker")
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton("Ok",
                    ColorEnvelopeListener { envelope, fromUser ->
                        statustext.setTextColor(envelope.color)
                    })
                .setNegativeButton("Cancel"
                ) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
                .attachBrightnessSlideBar(true)  // default is true. If false, do not show the BrightnessSlideBar.
                .show()
        }

        font.setOnClickListener {
            if (intent.getStringExtra("lang").equals("hindi")){
                temp3++
                if (temp3 == 14)
                    temp3 = 1
                when (temp3) {
                    1 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "aksharunicode.ttf")
                    2 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "asarregular.ttf")
                    3 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "biryaniregular.ttf")
                    4 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "khandregular.ttf")
                    5 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "kuraleregular.ttf")
                    6 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "modakregular.ttf")
                    7 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "mohini.ttf")
                    8 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "poppinsregular.ttf")
                    9 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "sahadeva.ttf")
                    10 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "sanskrit2003.ttf")
                    11 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "sarpanchregular.ttf")
                    12 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "tillanaregular.ttf")
                    13 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "yatraoneregular.ttf")
                }
            }
            else {
                temp3++
                if (temp3 == 18)
                    temp3 = 1
                when (temp3) {
                    1 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "alexrush.ttf")
                    2 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "allura.otf")
                    3 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "amatic.ttf")
                    4 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "arizonia.ttf")
                    5 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "blackjack.otf")
                    6 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "ffftusj.ttf")
                    7 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "grandhotel.otf")
                    8 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "greatvibes.otf")
                    9 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "opensans.ttf")
                    10 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "ostrichsans.otf")
                    11 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "pacifico.ttf")
                    12 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "quicksand.otf")
                    13 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "roboto.ttf")
                    14 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "seasrn.ttf")
                    15 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "sofia.otf")
                    16 -> statustext.typeface = Typeface.createFromAsset(getAssets(), "walkway.ttf")
                    17 -> statustext.typeface =
                        Typeface.createFromAsset(getAssets(), "windsong.ttf")
                }
            }
        }

        textsize.setOnClickListener {
            var dialog=Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.seekbardialog)
            var window=dialog.window
            window!!.setGravity(Gravity.TOP)
            dialog.show()

            var seekbar = dialog.findViewById<SeekBar>(R.id.seekbar)
            seekbar.progress=size
            seekbar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    statustext.textSize= (p1+12).toFloat()
                    size=p1
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
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
}
