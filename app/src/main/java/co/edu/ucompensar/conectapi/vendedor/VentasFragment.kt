package co.edu.ucompensar.conectapi.vendedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.AdaptadorSimple
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.FragmentVentasBinding
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.mostrarEstado
import co.edu.ucompensar.conectapi.pesos

// V5 · Historial de pedidos recibidos, con la comision de la tienda (15 %)
class VentasFragment : Fragment() {

    private var _vista: FragmentVentasBinding? = null
    private val vista get() = _vista!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentVentasBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vista.listaVentas.adapter = AdaptadorSimple(R.layout.item_venta, DatosDemo.ventas) { fila, venta ->
            fila.findViewById<TextView>(R.id.txtProducto).text = venta.producto
            fila.findViewById<TextView>(R.id.txtComprador).text = "Comprador: ${venta.comprador} · ${venta.fecha}"
            fila.findViewById<TextView>(R.id.txtEstado).mostrarEstado(venta.estado)
            fila.findViewById<TextView>(R.id.txtValor).text = venta.valor.pesos()
            fila.findViewById<TextView>(R.id.txtNeto).text = "Neto ${(venta.valor * 0.85).toInt().pesos()}"
        }

        // Solo se suman los pedidos pagados
        val total = DatosDemo.ventas.filter { it.estado == "Pagado" }.sumOf { it.valor }
        val comision = (total * 0.15).toInt()
        vista.txtTotal.text = total.pesos()
        vista.txtComision.text = "–${comision.pesos()}"
        vista.txtSaldo.text = (total - comision).pesos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
