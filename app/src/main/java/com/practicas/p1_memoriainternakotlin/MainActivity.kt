package com.practicas.p1_memoriainternakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val ARCHIVO_TEXTO = "miArchivo.txt"
    private val ARCHIVO_JSON = "datos.json"
    private val ARCHIVO_LOG = "log.txt"

    private lateinit var etTexto: EditText
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTexto = findViewById(R.id.etTexto)
        tvResultado = findViewById(R.id.tvResultado)

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            guardarTexto()
            escribirLog("Presionó botón Guardar Texto")
        }

        findViewById<Button>(R.id.btnLeer).setOnClickListener {
            leerTexto()
            escribirLog("Presionó botón Leer Texto")
        }

        findViewById<Button>(R.id.btnEliminar).setOnClickListener {
            eliminarTexto()
            escribirLog("Presionó botón Eliminar Texto")
        }

        findViewById<Button>(R.id.btnGuardarJSON).setOnClickListener {
            guardarJSON()
            escribirLog("Presionó botón Guardar JSON")
        }

        findViewById<Button>(R.id.btnLeerJSON).setOnClickListener {
            leerJSON()
            escribirLog("Presionó botón Leer JSON")
        }
    }

    // ================================
    // ACTIVIDAD 1: GUARDAR TEXTO
    // ================================
    private fun guardarTexto() {
        val texto = etTexto.text.toString()

        try {
            val fos = openFileOutput(ARCHIVO_TEXTO, MODE_PRIVATE)
            fos.write(texto.toByteArray())
            fos.close()

            Toast.makeText(this, "Texto guardado", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
        }
    }

    // ================================
    // ACTIVIDAD 1: LEER TEXTO
    // ================================
    private fun leerTexto() {
        if (!archivoExiste(ARCHIVO_TEXTO)) {
            Toast.makeText(this, "No existe el archivo aún", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val fis = openFileInput(ARCHIVO_TEXTO)
            val reader = BufferedReader(InputStreamReader(fis))
            val texto = reader.readText()

            tvResultado.text = texto

            reader.close()
            fis.close()

        } catch (e: Exception) {
            Toast.makeText(this, "Error al leer", Toast.LENGTH_SHORT).show()
        }
    }

    // ================================
    // ACTIVIDAD 1: ELIMINAR TEXTO
    // ================================
    private fun eliminarTexto() {
        if (deleteFile(ARCHIVO_TEXTO))
            Toast.makeText(this, "Archivo eliminado", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "No existe el archivo", Toast.LENGTH_SHORT).show()
    }

    // ================================
    // ACTIVIDAD 3: GUARDAR JSON
    // ================================
    private fun guardarJSON() {
        val json = """{"nombre":"juan","edad":20}"""

        try {
            val fos = openFileOutput(ARCHIVO_JSON, MODE_PRIVATE)
            fos.write(json.toByteArray())
            fos.close()

            Toast.makeText(this, "JSON guardado", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar JSON", Toast.LENGTH_SHORT).show()
        }
    }

    // ================================
    // ACTIVIDAD 3: LEER JSON
    // ================================
    private fun leerJSON() {
        if (!archivoExiste(ARCHIVO_JSON)) {
            Toast.makeText(this, "No hay JSON guardado aún", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val fis = openFileInput(ARCHIVO_JSON)
            val reader = BufferedReader(InputStreamReader(fis))
            val json = reader.readText()

            tvResultado.text = json

            reader.close()
            fis.close()

        } catch (e: Exception) {
            Toast.makeText(this, "Error al leer JSON", Toast.LENGTH_SHORT).show()
        }
    }

    // ================================
    // ACTIVIDAD 4: LOG DE ACCIONES
    // ================================
    private fun escribirLog(mensaje: String) {
        try {
            val fos = openFileOutput(ARCHIVO_LOG, MODE_APPEND)
            val fecha = Date().toString()
            fos.write("$fecha → $mensaje\n".toByteArray())
            fos.close()
        } catch (_: Exception) { }
    }

    // Verifica existencia de archivo
    private fun archivoExiste(nombre: String): Boolean {
        return fileList().contains(nombre)
    }
}