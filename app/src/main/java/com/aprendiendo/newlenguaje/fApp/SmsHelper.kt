package com.aprendiendo.newlenguaje.fApp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast

object SmsHelper {

    // ...

    // ...

    const val SMS_SENT = "SMS_SENT"
    const val SMS_DELIVERED = "SMS_DELIVERED"

    fun enviarSMS(context: Context, numero: String, mensaje: String) {
        var smsEntregado = false

        // Intent para verificar el estado del envío del SMS
        val sentIntent = Intent(SMS_SENT)
        val deliveredIntent = Intent(SMS_DELIVERED)

        val sentPI = PendingIntent.getBroadcast(context, 0, sentIntent, PendingIntent.FLAG_IMMUTABLE)
        val deliveredPI = PendingIntent.getBroadcast(context, 0, deliveredIntent, PendingIntent.FLAG_IMMUTABLE)

       /** BroadcastReceiver para verificar el estado del envío del SMS
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(context, "SMS enviado", Toast.LENGTH_SHORT).show()
                        // Aquí puedes realizar acciones adicionales si el SMS se envió con éxito
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                        Toast.makeText(context, "Error genérico al enviar SMS", Toast.LENGTH_SHORT).show()
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE -> {
                        Toast.makeText(context, "Sin servicio para enviar SMS", Toast.LENGTH_SHORT).show()
                    }
                    SmsManager.RESULT_ERROR_NULL_PDU -> {
                        Toast.makeText(context, "PDU nulo al enviar SMS", Toast.LENGTH_SHORT).show()
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF -> {
                        Toast.makeText(context, "Radio apagada al enviar SMS", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }, IntentFilter(SMS_SENT))
*/

        val deliveryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                smsEntregado = true
                Toast.makeText(context, "SMS entregado", Toast.LENGTH_SHORT).show()

            }
        }

        context.registerReceiver(deliveryReceiver, IntentFilter(SMS_DELIVERED))

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(numero, null, mensaje, sentPI, deliveredPI)

        // Verificar el estado de entrega después de un tiempo (ajusta según sea necesario)
        Handler(Looper.getMainLooper()).postDelayed({
            if (!smsEntregado) {
                Toast.makeText(context, "Mensaje ... pendiente a entrega", Toast.LENGTH_SHORT).show()
            }
        }, 5000)
    }


}
