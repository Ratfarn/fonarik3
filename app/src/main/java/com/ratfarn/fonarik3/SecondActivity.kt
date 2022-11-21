package com.ratfarn.fonarik3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.View
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import com.ratfarn.fonarik3.databinding.ActivitySecondBinding
import kotlin.random.Random

class SecondActivity : AppCompatActivity() {
    private lateinit var flashing: ImageButton
    private lateinit var cm:CameraManager

    /**Для смены заднего фона**/
    private lateinit var bind: ActivitySecondBinding
    private val rnd = Random
    private var color: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**Для смены заднего фона**/
        bind = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(bind.root)
        flashing = findViewById(R.id.flashing)
        flashing.isEnabled = false
        cm = getSystemService(CAMERA_SERVICE) as CameraManager
        color = intent.getIntExtra("color", 0)
        bind.layout2.setBackgroundColor(color)
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        }
        else flashing.isEnabled = true

        intent.putExtra("color", color) // передающиеся данные для другого активити
        setResult(RESULT_OK, intent) // установка результата для другого активити

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            flashing.isEnabled = true
    }


    fun transition1(view: View){
        intent.putExtra("color", color) // передающиеся данные для другого активити
        setResult(RESULT_OK, intent) // установка результата для другого активити
        finish()
    }
    fun transitionToKompass(view: View){
        val intent = Intent(this, KompassActivity::class.java)
        startActivity(intent)
    }
    fun blinkmode(view: View) {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            var light = false
            var s = "10101010101010101010101010101010"
            for (i in s.indices){
                light = s[i] == '1'
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    cm.setTorchMode(cm.cameraIdList[0],light)
                }
                Thread.sleep(50)
            }
        }
    }

    /**Смена заднего фона**/
    fun changecolor(view: View) {
        color = Color.argb(255, rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256))

        bind.layout2.setBackgroundColor(color)
        intent.putExtra("color", color) // передающиеся данные для другого активити
        setResult(RESULT_OK, intent) // установка результата для другого активити
    }
}

