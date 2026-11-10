package co.edu.ucompensar.conectapi.comprador

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.ActivityDetalleApiBinding
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.datos.ItemCarrito
import co.edu.ucompensar.conectapi.datos.Plan
import co.edu.ucompensar.conectapi.mensaje
import co.edu.ucompensar.conectapi.miles
import co.edu.ucompensar.conectapi.pesos
import co.edu.ucompensar.conectapi.pintarFondo

// C2 · Detalle del producto: informacion de la API y sus planes
class DetalleApiActivity : AppCompatActivity() {

    private lateinit var vista: ActivityDetalleApiBinding
    private var planElegido: Plan? = null
    private val filasDePlanes = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityDetalleApiBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.barra.setNavigationOnClickListener { finish() }

        // La API que se toco llega por el Intent
        val api = DatosDemo.buscarApi(intent.getIntExtra("idApi", 1)) ?: return finish()

        vista.txtLogo.text = api.nombre.take(1)
        vista.txtLogo.pintarFondo(api.color)
        vista.txtNombre.text = api.nombre
        vista.txtVendedor.text = "por ${api.vendedor} · ${api.calificacion}"
        vista.txtDescripcion.text = api.descripcion
        vista.txtCategoria.text = api.categoria

        // Se crea una fila por cada plan con el layout item_plan.xml
        api.planes.forEachIndexed { posicion, plan ->
            val fila = layoutInflater.inflate(R.layout.item_plan, vista.listaPlanes, false)
            fila.findViewById<TextView>(R.id.txtNombre).text = plan.nombre
            fila.findViewById<TextView>(R.id.txtLlamadas).text = "${plan.llamadas.miles()} llamadas/mes"
            fila.findViewById<TextView>(R.id.txtPrecio).text = plan.precio.pesos()
            fila.setOnClickListener { elegir(fila, plan) }
            vista.listaPlanes.addView(fila)
            filasDePlanes.add(fila)
            // Por defecto queda marcado el segundo plan (o el primero si solo hay uno)
            if (posicion == minOf(1, api.planes.size - 1)) elegir(fila, plan)
        }

        vista.btnProbar.setOnClickListener { mensaje("Respuesta de prueba: 200 OK") }

        vista.btnAgregar.setOnClickListener {
            val plan = planElegido ?: return@setOnClickListener
            DatosDemo.carrito.add(ItemCarrito(api, plan, 1))
            mensaje("${api.nombre} · ${plan.nombre} agregado al carrito")
            finish()
        }
    }

    // Marca una fila como seleccionada y desmarca las demas
    private fun elegir(filaElegida: View, plan: Plan) {
        planElegido = plan
        filasDePlanes.forEach { fila ->
            fila.isSelected = fila == filaElegida
            fila.findViewById<RadioButton>(R.id.radio).isChecked = fila == filaElegida
        }
    }
}
