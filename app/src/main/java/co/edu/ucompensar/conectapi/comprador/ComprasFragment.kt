package co.edu.ucompensar.conectapi.comprador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.AdaptadorSimple
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.FragmentComprasBinding
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.mensaje
import co.edu.ucompensar.conectapi.miles
import co.edu.ucompensar.conectapi.mostrarEstado
import co.edu.ucompensar.conectapi.pintarFondo
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// C5 · Mis compras y llaves de acceso
class ComprasFragment : Fragment() {

    private var _vista: FragmentComprasBinding? = null
    private val vista get() = _vista!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentComprasBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Aviso verde que solo sale justo despues de pagar
        if (DatosDemo.pagoReciente) {
            vista.txtAviso.visibility = View.VISIBLE
            DatosDemo.pagoReciente = false
        }

        vista.listaCompras.adapter = AdaptadorSimple(R.layout.item_compra, DatosDemo.compras) { fila, compra ->
            val logo = fila.findViewById<TextView>(R.id.txtLogo)
            logo.text = compra.api.nombre.take(1)
            logo.pintarFondo(compra.api.color)
            fila.findViewById<TextView>(R.id.txtNombre).text = "${compra.api.nombre} · ${compra.plan.nombre}"
            fila.findViewById<TextView>(R.id.txtVence).text = "Vence: ${compra.vence}"
            fila.findViewById<TextView>(R.id.txtEstado).mostrarEstado("Activa")
            fila.findViewById<TextView>(R.id.txtLlave).text = compra.llave

            val usado = compra.plan.llamadas * compra.uso / 100
            fila.findViewById<TextView>(R.id.txtUso).text = "${usado.miles()} / ${compra.plan.llamadas.miles()}"
            fila.findViewById<ProgressBar>(R.id.barraUso).progress = compra.uso

            fila.findViewById<View>(R.id.btnCopiar).setOnClickListener {
                requireContext().mensaje("Para copiar la llave se pide la huella")
            }
            fila.findViewById<View>(R.id.btnRetracto).setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Pedir retracto")
                    .setMessage("Tienes 5 días hábiles para pedirlo, si no has empezado a usar la API (Ley 1480 de 2011).")
                    .setPositiveButton("Enviar solicitud") { _, _ -> requireContext().mensaje("Solicitud enviada") }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
