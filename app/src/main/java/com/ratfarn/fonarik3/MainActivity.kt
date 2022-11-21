package com.ratfarn.fonarik3

import android.content.Intent
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.content.Context
import com.ratfarn.fonarik3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    /**Код фонарика**/
    private lateinit var cameraM: CameraManager
    private lateinit var powerBtn: ImageButton

    /**Для смены заднего фона**/
    private lateinit var bind: ActivityMainBinding
    private var color: Int? = 0

    var isFlash = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        /**Присваеваем значение**/
        powerBtn = findViewById(R.id.poweron)
        cameraM = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        powerBtn.setOnClickListener(View.OnClickListener {
            flashLightOnRoOff()
        })
    }
    private fun flashLightOnRoOff(){
        /**сам фонарик**/
        if(!isFlash){
            val cameraListId = cameraM.cameraIdList[0]
            cameraM.setTorchMode(cameraListId,true)
            isFlash=true
            powerBtn.setImageResource(R.drawable.ic__power_on)
        }
        else{
            val cameraListId = cameraM.cameraIdList[0]
            cameraM.setTorchMode(cameraListId,false)
            isFlash=false
            powerBtn.setImageResource(R.drawable.ic__power_off)
        }
    }
    /**Переход на следующую активити**/
    fun transition(view: View){
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("color", color)
        startActivityForResult(intent,0)
    }
    /**Наследование цвета фона**/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        color = data?.getIntExtra("color", 0)
        bind.layout1.setBackgroundColor(color ?: 0)
    }
}