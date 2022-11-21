package com.ratfarn.fonarik3

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ORIENTATION
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService


class KompassActivity : AppCompatActivity(), SensorEventListener {

    //Насколько повёрнут компас
    private var currentDegree = 0f
    //сенсер устройства
    private var mSendorManager:SensorManager? = null
    private var imagedegree: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kompass)
        initDate()
    }
    private fun initDate(){
    mSendorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    }
    override fun onResume(){
        super.onResume()
        @Suppress("DEPRECATION")
        mSendorManager?.registerListener(this,
            mSendorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME)
    }
    override fun onSensorChanged(event: SensorEvent?) {
        val degree = Math.round(event?.values?.get(0)!!)
        val rotateAnimation = RotateAnimation(
            currentDegree,
            (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.duration = 210
        rotateAnimation.fillAfter =true

        imagedegree = findViewById(R.id.imagedegree)

        imagedegree?.startAnimation(rotateAnimation)
        currentDegree = (-degree).toFloat()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    fun transitionToSecond(view: View){
        finish()
    }
}

