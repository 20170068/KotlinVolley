package com.example.metodopost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var build: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtName = findViewById<EditText>(R.id.TxtName)
        val txtType = findViewById<EditText>(R.id.txtType)
        val txtPrice = findViewById<EditText>(R.id.txtPrice)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val txtAge = findViewById<EditText>(R.id.txtAge)
        build = AlertDialog.Builder(this)


        btnSend.setOnClickListener{
            if(txtName.text.isEmpty() || txtType.text.isEmpty() || txtPrice.text.isEmpty() || txtAge.text.isEmpty()){

                build.setTitle("ERROR")
                    .setMessage("Por favor, completa todos los datos.")
                    .setCancelable(true)
                    .setNegativeButton("Ok"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }.show()
                //Toast.makeText(this, "Falta de llenar campos", Toast.LENGTH_SHORT).show()
            }
            else {
                sendData()
                sendAge()
                Toast.makeText(this, "Se envió correctamente", Toast.LENGTH_LONG).show()
                txtName.text = null
                txtType.text = null
                txtPrice.text = null
                txtAge.text = null
            }


        }

    }

    private fun sendAge() {
        val txtAge = findViewById<EditText>(R.id.txtAge)
        val url = "http://192.168.50.43:4000/age"

        val datos = HashMap<String, Any>()
        datos ["edad"] = txtAge.text.toString()

        val guardar_datos = JSONObject(datos as Map<*, *>?)

        val jsonObject = JsonObjectRequest(Request.Method.POST, url, guardar_datos, {
            response ->
            try {


            } catch(e: Exception){
                e.printStackTrace()
            }
        }, { error ->
            error.printStackTrace()
        })

        SingletonRequest.getInstance(this).addToRequestQueue(jsonObject)

    }

    private fun sendData() {
        val txtName = findViewById<EditText>(R.id.TxtName)
        val txtType = findViewById<EditText>(R.id.txtType)
        val txtPrice = findViewById<EditText>(R.id.txtPrice)
        val url = "http://192.168.50.43:4000/tasks"

        val data = HashMap<String, Any>()
        data ["name"] = txtName.text.toString()
        data["type"] = txtType.text.toString()
        data["price"] = txtPrice.text.toString()

        val sen_data = JSONObject(data as Map<*, *>?)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, sen_data, {
            response ->

            try {
                val serv = response.getInt("error")
                if (serv == 0){
                    Toast.makeText(this, "Éxito. ${response.getString("mensaje")}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Error. ${response.getString("mensaje")}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception){
                e.printStackTrace()
            }

        }, {error ->
            error.printStackTrace()
        })

        SingletonRequest.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
}