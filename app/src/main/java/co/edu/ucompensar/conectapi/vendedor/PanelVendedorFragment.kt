package co.edu.ucompensar.conectapi.vendedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.databinding.FragmentPanelVendedorBinding
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.datos.Sesion
import co.edu.ucompensar.conectapi.dibujarBarras

// V1 · Panel del vendedor: numeros del mes, grafica de ventas y avisos
class PanelVendedorFragment : Fragment() {

    private var _vista: FragmentPanelVendedorBinding? = null
    private val vista get() = _vista!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentPanelVendedorBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vista.txtSaludo.text = "Hola, ${Sesion.nombre}"

        // Las 4 tarjetas usan el mismo layout (tarjeta_metrica.xml) con <include>
        vista.metrica1.txtEtiqueta.text = "Ventas del mes"
        vista.metrica1.txtValor.text = "$2.340.000"
        vista.metrica2.txtEtiqueta.text = "Saldo a recibir"
        vista.metrica2.txtValor.text = "$1.989.000"
        vista.metrica3.txtEtiqueta.text = "Clientes activos"
        vista.metrica3.txtValor.text = "48"
        vista.metrica4.txtEtiqueta.text = "Calificación"
        vista.metrica4.txtValor.text = "★ 4.8"

        dibujarBarras(vista.grafica, DatosDemo.ventasPorMes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
