package co.edu.ucompensar.conectapi.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.AdaptadorSimple
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.FragmentProductosAdminBinding
import co.edu.ucompensar.conectapi.datos.Api
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.mensaje
import co.edu.ucompensar.conectapi.mostrarEstado
import co.edu.ucompensar.conectapi.pintarFondo

// A4 · Gestion de productos del administrador: aprobar, rechazar, pausar y eliminar APIs
class ProductosAdminFragment : Fragment() {

    private var _vista: FragmentProductosAdminBinding? = null
    private val vista get() = _vista!!

    private lateinit var adaptador: AdaptadorSimple<Api>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentProductosAdminBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adaptador = AdaptadorSimple(R.layout.item_producto_admin, emptyList()) { fila, api -> llenarFila(fila, api) }
        vista.listaProductos.adapter = adaptador
        vista.grupoEstado.setOnCheckedStateChangeListener { _, _ -> filtrar() }
        filtrar()
    }

    private fun llenarFila(fila: View, api: Api) {
        val logo = fila.findViewById<TextView>(R.id.txtLogo)
        logo.text = api.nombre.take(1)
        logo.pintarFondo(api.color)
        fila.findViewById<TextView>(R.id.txtNombre).text = api.nombre
        fila.findViewById<TextView>(R.id.txtVendedor).text = "${api.vendedor} · ${api.categoria}"
        fila.findViewById<TextView>(R.id.txtEstado).mostrarEstado(api.estado)

        // Lista de chequeo que revisa el administrador
        val https = if (api.urlBase.startsWith("https://")) "✔ Usa HTTPS" else "✖ Sin HTTPS"
        val docs = if (api.documentacion.isNotEmpty()) "✔ Tiene documentación" else "✖ Sin documentación"
        fila.findViewById<TextView>(R.id.txtRevision).text = "$https   $docs   ✔ ${api.planes.size} planes"

        val principal = fila.findViewById<Button>(R.id.btnPrincipal)
        val secundario = fila.findViewById<Button>(R.id.btnSecundario)

        // Los botones cambian segun el estado del producto
        when (api.estado) {
            "En revisión" -> {
                principal.text = "Aprobar"
                secundario.text = "Rechazar"
                principal.setOnClickListener { cambiarEstado(api, "Aprobada") }
                secundario.setOnClickListener { cambiarEstado(api, "Rechazada") }
            }
            "Aprobada" -> {
                principal.text = "Pausar"
                secundario.text = "Eliminar"
                principal.setOnClickListener { cambiarEstado(api, "Pausada") }
                secundario.setOnClickListener { eliminar(api) }
            }
            else -> {
                principal.text = "Reactivar"
                secundario.text = "Eliminar"
                principal.setOnClickListener { cambiarEstado(api, "Aprobada") }
                secundario.setOnClickListener { eliminar(api) }
            }
        }
    }

    private fun cambiarEstado(api: Api, nuevo: String) {
        api.estado = nuevo
        requireContext().mensaje("${api.nombre}: $nuevo. Se avisa al vendedor.")
        filtrar()
    }

    private fun eliminar(api: Api) {
        DatosDemo.apis.remove(api)
        requireContext().mensaje("${api.nombre} eliminada de la tienda")
        filtrar()
    }

    private fun filtrar() {
        val lista = when (vista.grupoEstado.checkedChipId) {
            R.id.chipAprobadas -> DatosDemo.apis.filter { it.estado == "Aprobada" }
            R.id.chipOtras -> DatosDemo.apis.filter { it.estado == "Pausada" || it.estado == "Rechazada" }
            else -> DatosDemo.apis.filter { it.estado == "En revisión" }
        }
        adaptador.cambiarDatos(lista)
        vista.txtVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        vista.listaProductos.visibility = if (lista.isEmpty()) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
