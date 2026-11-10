package co.edu.ucompensar.conectapi.comprador

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.AdaptadorSimple
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.base.RolActivity
import co.edu.ucompensar.conectapi.databinding.FragmentInicioBinding
import co.edu.ucompensar.conectapi.datos.Api
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.datos.Sesion
import co.edu.ucompensar.conectapi.pesos
import co.edu.ucompensar.conectapi.pintarFondo

// C1 · Inicio del comprador: saludo, banner, categorias y APIs destacadas
class InicioFragment : Fragment() {

    private var _vista: FragmentInicioBinding? = null
    private val vista get() = _vista!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentInicioBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val principal = requireActivity() as RolActivity

        vista.txtSaludo.text = "Hola, ${Sesion.nombre.substringBefore(" ")}"
        vista.btnBuscar.setOnClickListener { principal.irA(R.id.nav_catalogo) }
        vista.btnVerTodo.setOnClickListener { principal.irA(R.id.nav_catalogo) }
        vista.banner.setOnClickListener { abrirDetalle(4) }

        vista.listaCategorias.adapter = AdaptadorSimple(R.layout.item_categoria_inicio, DatosDemo.categorias) { fila, categoria ->
            fila.findViewById<TextView>(R.id.txtNombre).text = categoria.nombre
            fila.setOnClickListener { principal.irA(R.id.nav_catalogo) }
        }

        vista.listaDestacadas.adapter = AdaptadorSimple(R.layout.item_api, DatosDemo.apisAprobadas()) { fila, api ->
            llenarFilaApi(fila, api)
            fila.setOnClickListener { abrirDetalle(api.id) }
        }
    }

    private fun llenarFilaApi(fila: View, api: Api) {
        val logo = fila.findViewById<TextView>(R.id.txtLogo)
        logo.text = api.nombre.take(1)
        logo.pintarFondo(api.color)
        fila.findViewById<TextView>(R.id.txtNombre).text = api.nombre
        fila.findViewById<TextView>(R.id.txtVendedor).text = "por ${api.vendedor}"
        fila.findViewById<TextView>(R.id.txtCalificacion).text = api.calificacion.toString()
        fila.findViewById<TextView>(R.id.txtPrecio).text = api.precioDesde.pesos()
    }

    private fun abrirDetalle(idApi: Int) {
        val intent = Intent(requireContext(), DetalleApiActivity::class.java)
        intent.putExtra("idApi", idApi)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
