package com.hackitjunior.journaler.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.hackitjunior.journaler.R
import com.hackitjunior.journaler.permission.PermissionCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


abstract class BaseActivity : PermissionCompatActivity() {


    companion object {
        private var fontExoBold: Typeface? = null
        private var fontExoRegular: Typeface? = null
        private val REQUEST_GPS = 0

        fun applyFonts(view: View, ctx: Context) {
            var vTag = ""
            if (view.tag is String) {
                vTag = view.tag as String
            }
            when (view) {
                is ViewGroup -> {
                    for (x in 0..view.childCount - 1) {
                        applyFonts(view.getChildAt(x), ctx)
                    }
                }
                is Button -> {
                    when (vTag) {
                        ctx.getString(R.string.tag_font_bold) -> {
                            view.typeface = fontExoBold
                        }
                        else -> {
                            view.typeface = fontExoRegular
                        }
                    }
                }
                is TextView -> {
                    when (vTag) {
                        ctx.getString(R.string.tag_font_bold) -> {
                            view.typeface = fontExoBold
                        }
                        else -> {
                            view.typeface = fontExoRegular
                        }
                    }
                }
                is EditText -> {
                    when (vTag) {
                        ctx.getString(R.string.tag_font_bold) -> {
                            view.typeface = fontExoBold
                        }
                        else -> {
                            view.typeface = fontExoRegular
                        }
                    }
                }
            }
        }
    }

    protected abstract val tag : String
    protected abstract fun getLayout(): Int
    protected abstract fun getActivityTitle(): Int

    fun Activity.getAnimation(animation: Int): Animation =
        AnimationUtils.loadAnimation(this, animation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setContentView(getLayout())
        setSupportActionBar(toolbar)
        requestPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        // activity_title.setText(getActivityTitle())
        Log.v(tag, "[ ON CREATE ]")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.v(tag, "[ ON POST CREATE ]")
        applyFonts()
    }

    protected fun applyFonts() {
        initFonts()
        Log.v(tag, "Applying fonts [ START ]")
        val rootView = findViewById(android.R.id.content) as ViewGroup
        applyFonts(rootView, this)
        Log.v(tag, "Applying fonts [ END ]")
    }

    private fun initFonts() {
        if (fontExoBold == null) {
            Log.v(tag, "Initializing font [ Exo2-Bold ]")
            fontExoBold = Typeface.createFromAsset(assets, "fonts/Exo2-Bold.ttf")
        }
        if (fontExoRegular == null) {
            Log.v(tag, "Initializing font [ Exo2-Regular ]")
            fontExoRegular = Typeface.createFromAsset(assets, "fonts/Exo2-Regular.ttf")
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.v(tag, "[ ON RESTART ]")
    }

    override fun onStart() {
        super.onStart()
        Log.v(tag, "[ ON START ]")
    }

    override fun onResume() {
        super.onResume()
        Log.v(tag, "[ ON RESUME ]")
        val animation = getAnimation(R.anim.top_to_bottom)
        (findViewById(R.id.toolbar) as Toolbar).startAnimation(animation)
    }

    override fun onPause() {
        super.onPause()
        Log.v(tag, "[ ON PAUSE ]")
        val animation = getAnimation(R.anim.hide_to_top)
        (findViewById(R.id.toolbar) as Toolbar).startAnimation(animation)

    }

    override fun onPostResume() {
        super.onPostResume()
        Log.v(tag, "[ ON POST RESUME ]")
    }

    override fun onStop() {
        super.onStop()
        Log.v(tag, "[ ON STOP ]")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(tag, "[ ON DESTROY ]")
    }

}