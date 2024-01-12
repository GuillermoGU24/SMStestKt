package com.aprendiendo.newlenguaje.fApp

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aprendiendo.newlenguaje.R
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream

val TAG = "fireBase"
private const val MY_PERMISSIONS_REQUEST_SEND_SMS = 123

class firtsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firts)
        FirebaseApp.initializeApp(this)
        val db = FirebaseFirestore.getInstance()
        val btn1 = findViewById<AppCompatButton>(R.id.btn1)
        val text = findViewById<EditText>(R.id.txtnum)

        val listaDocumentos: ArrayList<Objecto> = ArrayList()

        btn1.setOnClickListener {
            db.collection("sms")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val numero = document.data.get("numero").toString()
                        val msj = document.data.get("msj") as? ArrayList<String> ?: ArrayList()

                        val objeto = Objecto(document.id, numero, msj.toTypedArray())

                        Log.d(TAG, "${document.id} => ${objeto.toString()}")
                    }
                }.addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Si no está concedido, solicitar permiso
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.SEND_SMS),
                    MY_PERMISSIONS_REQUEST_SEND_SMS
                )
            } else {

                val msg = "hola"
                val smsMessage = msg
                val number : String = text.text.toString()
                SmsHelper.enviarSMS(this, number, smsMessage)
            }
        }
    }
}
