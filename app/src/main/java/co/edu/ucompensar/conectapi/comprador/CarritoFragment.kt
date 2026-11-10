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
import co.edu.ucompensar.conectapi.databinding.FragmentCarritoBinding
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.datos.ItemCarrito
import co.edu.ucompensar.conectapi.pesos
import co.edu.ucompensar.conectapi.pintarFondo
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// C3 · Carrito de compras (CRUD del carrito: ver, cambiar meses, quitar y vaciar)
class CarritoFragment : Fragment() {

    private var _vista: FragmentCarritoBinding? = null
    private val vista get() = _vista!!

    private lateinit var adaptador: AdaptadorSimple<ItemCarrito>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentCarritoBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adaptador = AdaptadorSimple(R.layout.item_carrito, DatosDemo.carrito.toList()) { fila, item ->
            val logo = fila.findViewById<TextView>(R.id.txtLogo)
            logo.text = item.api.nombre.take(1)
            logo.pintarFondo(item.api.color)
            fila.findViewById<TextView>(R.id.txtNombre).text = "${item.api.nombre} · ${item.plan.nombre}"
            fila.findViewById<TextView>(R.id.txtPrecio).text = "${item.plan.precio.pesos()} / mes"
            fila.findViewById<TextView>(R.id.txtMeses).text = item.meses.toString()

            fila.findViewById<View>(R.id.btnMas).setOnClickListener {
                item.meses++
                actualizar()
            }
            fila.findViewById<View>(R.id.btnMenos).setOnClickListener {
                if (item.meses > 1) item.meses--
                actualizar()
            }
            fila.findViewById<View>(R.id.btnBorrar).setOnClickListener {
                DatosDemo.carrito.remove(item)
                actualizar()
            }
        }
        vista.listaCarrito.adapter = adaptador

        vista.btnVaciar.setOnClickListener {
            // Se pide confirmacion antes de vaciar (RF-33)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("¿Vaciar el carrito?")
                .setMessage("Se quitan todos los productos.")
                .setPositiveButton("Vaciar") { _, _ ->
                    DatosDemo.carrito.clear()
                    actualizar()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        vista.btnPagar.setOnClickListener {
            startActivity(Intent(requireContext(), PagoActivity::class.java))
        }
        vista.btnCatalogo.setOnClickListener {
            (requireActivity() as RolActivity).irA(R.id.nav_catalogo)
        }
    }

    override fun onResume() {
        super.onResume()
        actualizar()
    }

    // Vuelve a dibujar la lista y recalcula subtotal, IVA y total
    private fun actualizar() {
        adaptador.cambiarDatos(DatosDemo.carrito.toList())
        vista.txtSubtotal.text = DatosDemo.subtotal().pesos()
        vista.txtIva.text = DatosDemo.iva().pesos()
        vista.txtTotal.text = DatosDemo.total().pesos()

        val estaVacio = DatosDemo.carrito.isEmpty()
        vista.vacio.visibility = if (estaVacio) View.VISIBLE else View.GONE
        vista.contenido.visibility = if (estaVacio) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
