package com.google.mlkit.vision.demo.kotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.demo.R

class LoginActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1000)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        permisos()

        val usuario = findViewById<EditText>(R.id.user)
        val password = findViewById<EditText>(R.id.password)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val user = usuario.text.toString()
            val pass = password.text.toString()
            if (user == "data" && pass == "123") {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LivePreviewActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show()
                usuario.text.clear()
                password.text.clear()
                usuario.requestFocus()
            }
        }

    }

    private fun permisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permisoCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            val permisoEscribirAlmacenamiento = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val permisoLeerAlmacenamiento = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

            val permisosARequerir = mutableListOf<String>()
            if (permisoCamara != PackageManager.PERMISSION_GRANTED) {
                permisosARequerir.add(Manifest.permission.CAMERA)
            }
            if (permisoEscribirAlmacenamiento != PackageManager.PERMISSION_GRANTED) {
                permisosARequerir.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (permisoLeerAlmacenamiento != PackageManager.PERMISSION_GRANTED) {
                permisosARequerir.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if (permisosARequerir.isNotEmpty()) {
                ActivityCompat.requestPermissions(this, permisosARequerir.toTypedArray(), CAMERA_PERMISSION_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // permisos aceptados
                } else {
                    // denegados
                }
            }
        }
    }

}
