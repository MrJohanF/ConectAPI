package co.edu.ucompensar.conectapi.vendedor

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.ActivityPlanesBinding
import co.edu.ucompensar.conectapi.datos.Api
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.datos.Plan
import co.edu.ucompensar.conectapi.mensaje
import co.edu.ucompensar.conectapi.miles
import co.edu.ucompensar.conectapi.pesos

// V4 · Planes de precio de una API (agregar y eliminar planes) y envio a revision
class PlanesActivity : AppCompatActivity() {

    private lateinit var vista: ActivityPlanesBinding
    private lateinit var api: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityPlanesBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.barra.setNavigationOnClickListener { finish() }
        api = DatosDemo.buscarApi(intent.getIntExtra("idApi", 0)) ?: return finish()
        vista.txtApi.text = api.nombre

        mostrarPlanes()

        vista.btnAgregar.setOnClickListener {
            val nombre = vista.campoNombre.text.toString().trim()
            val llamadas = vista.campoLlamadas.text.toString().toIntOrNull()
            val precio = vista.campoPrecio.text.toString().toIntOrNull()
            if (nombre.isEmpty() || llamadas == null || precio == null) {
                mensaje("Completa los tres campos del plan")
                return@setOnClickListener
            }
            api.planes.add(Plan(nombre, llamadas, precio))
            vista.campoNombre.text?.clear()
            vista.campoLlamadas.text?.clear()
            vista.campoPrecio.text?.clear()
            mostrarPlanes()
        }

        vista.btnEnviar.setOnClickListener {
            if (api.planes.isEmpty()) {
                mensaje("Agrega al menos un plan")
                return@setOnClickListener
            }
            api.estado = "En revisión"
            mensaje("API enviada a revisión del administrador")
            finish()
        }
    }

    private fun mostrarPlanes() {
        vista.listaPlanes.removeAllViews()
        api.planes.forEach { plan ->
            val fila = layoutInflater.inflate(R.layout.item_plan_editable, vista.listaPlanes, false)
            fila.findViewById<TextView>(R.id.txtNombre).text = plan.nombre
            fila.findViewById<TextView>(R.id.txtDetalle).text = "${plan.llamadas.miles()} llamadas/mes · ${plan.precio.pesos()}"
            fila.findViewById<View>(R.id.btnBorrar).setOnClickListener {
                api.planes.remove(plan)
                mostrarPlanes()
            }
            vista.listaPlanes.addView(fila)
        }
    }
}
