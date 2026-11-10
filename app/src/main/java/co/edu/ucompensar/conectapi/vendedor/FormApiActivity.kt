package co.edu.ucompensar.conectapi.vendedor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.ActivityFormApiBinding
import co.edu.ucompensar.conectapi.datos.Api
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.mensaje

// V3 · Formulario para crear o editar una API. Si llega un id, se edita; si llega 0, se crea.
class FormApiActivity : AppCompatActivity() {

    private lateinit var vista: ActivityFormApiBinding
    private var api: Api? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityFormApiBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.barra.setNavigationOnClickListener { finish() }
        vista.btnLogo.setOnClickListener { mensaje("Aquí se abre la cámara o la galería") }

        api = DatosDemo.buscarApi(intent.getIntExtra("idApi", 0))
        api?.let { llenarCampos(it) }

        vista.btnBorrador.setOnClickListener {
            if (guardar()) {
                mensaje("Cambios guardados")
                finish()
            }
        }

        vista.btnSiguiente.setOnClickListener {
            if (guardar()) {
                val intent = Intent(this, PlanesActivity::class.java)
                intent.putExtra("idApi", api!!.id)
                startActivity(intent)
                finish()
            }
        }

        vista.btnEliminar.setOnClickListener {
            DatosDemo.apis.remove(api)
            mensaje("API eliminada")
            finish()
        }
    }

    private fun llenarCampos(api: Api) {
        vista.barra.title = "Editar API"
        vista.campoNombre.setText(api.nombre)
        vista.campoCategoria.setText(api.categoria, false)
        vista.campoUrl.setText(api.urlBase)
        vista.campoDocs.setText(api.documentacion)
        vista.campoDescripcion.setText(api.descripcion)
        vista.btnEliminar.visibility = View.VISIBLE
    }

    // Revisa los campos y guarda. Devuelve false si falta algo.
    private fun guardar(): Boolean {
        val nombre = vista.campoNombre.text.toString().trim()
        val url = vista.campoUrl.text.toString().trim()
        if (nombre.isEmpty()) {
            vista.campoNombre.error = "Escribe el nombre"
            return false
        }
        if (!url.startsWith("https://")) {
            vista.campoUrl.error = "La dirección debe empezar por https://"
            return false
        }
        val categoria = vista.campoCategoria.text.toString().ifEmpty { "Pagos" }
        val descripcion = vista.campoDescripcion.text.toString().trim()
        val docs = vista.campoDocs.text.toString().trim()

        val actual = api
        if (actual == null) {
            // Crear
            api = Api(
                DatosDemo.siguienteIdApi(), nombre, DatosDemo.MI_EMPRESA, categoria, descripcion,
                0.0, R.color.cian, "Borrador", mutableListOf(), 0, url, docs
            ).also { DatosDemo.apis.add(it) }
        } else {
            // Actualizar
            actual.nombre = nombre
            actual.categoria = categoria
            actual.descripcion = descripcion
            actual.urlBase = url
            actual.documentacion = docs
        }
        return true
    }
}
