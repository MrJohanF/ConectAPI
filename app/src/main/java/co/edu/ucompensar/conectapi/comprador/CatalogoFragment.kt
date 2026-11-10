package co.edu.ucompensar.conectapi.comprador

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.AdaptadorSimple
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.FragmentCatalogoBinding
import co.edu.ucompensar.conectapi.datos.Api
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.pesos
import co.edu.ucompensar.conectapi.pintarFondo
import com.google.android.material.chip.Chip

// Catalogo de productos: buscador, filtro por categoria y cuadricula de APIs
class CatalogoFragment : Fragment() {

    private var _vista: FragmentCatalogoBinding? = null
    private val vista get() = _vista!!

    private lateinit var adaptador: AdaptadorSimple<Api>
    private var categoriaElegida = "Todas"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentCatalogoBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adaptador = AdaptadorSimple(R.layout.item_api_cuadro, DatosDemo.apisAprobadas()) { fila, api ->
            val logo = fila.findViewById<TextView>(R.id.txtLogo)
            logo.text = api.nombre.take(1)
            logo.pintarFondo(api.color)
            fila.findViewById<TextView>(R.id.txtNombre).text = api.nombre
            fila.findViewById<TextView>(R.id.txtCategoria).text = "${api.categoria} · ★ ${api.calificacion}"
            fila.findViewById<TextView>(R.id.txtDescripcion).text = api.descripcion
            fila.findViewById<TextView>(R.id.txtPrecio).text =
                if (api.precioDesde == 0) "Gratis" else "Desde ${api.precioDesde.pesos()}"
            fila.setOnClickListener {
                val intent = Intent(requireContext(), DetalleApiActivity::class.java)
                intent.putExtra("idApi", api.id)
                startActivity(intent)
            }
        }
        vista.listaApis.adapter = adaptador

        crearChips()
        vista.campoBuscar.doAfterTextChanged { filtrar() }
    }

    // Crea un chip por cada categoria usando el estilo ChipFiltro
    private fun crearChips() {
        val nombres = listOf("Todas") + DatosDemo.categorias.map { it.nombre }
        nombres.forEachIndexed { posicion, nombre ->
            val chip = Chip(requireContext(), null, R.attr.estiloChipFiltro)
            chip.id = View.generateViewId()
            chip.text = nombre
            chip.isCheckable = true
            chip.isChecked = posicion == 0
            chip.setOnClickListener {
                categoriaElegida = nombre
                filtrar()
            }
            vista.grupoCategorias.addView(chip)
        }
    }

    private fun filtrar() {
        val texto = vista.campoBuscar.text.toString().trim().lowercase()
        val resultado = DatosDemo.apisAprobadas().filter { api ->
            (categoriaElegida == "Todas" || api.categoria == categoriaElegida) &&
                api.nombre.lowercase().contains(texto)
        }
        adaptador.cambiarDatos(resultado)
        vista.txtVacio.visibility = if (resultado.isEmpty()) View.VISIBLE else View.GONE
        vista.listaApis.visibility = if (resultado.isEmpty()) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
