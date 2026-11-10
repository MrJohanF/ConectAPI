package co.edu.ucompensar.conectapi.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.base.RolActivity
import co.edu.ucompensar.conectapi.databinding.FragmentPanelAdminBinding
import co.edu.ucompensar.conectapi.datos.DatosDemo

// A1 · Dashboard principal del administrador
class PanelAdminFragment : Fragment() {

    private var _vista: FragmentPanelAdminBinding? = null
    private val vista get() = _vista!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentPanelAdminBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val porRevisar = DatosDemo.apis.count { it.estado == "En revisión" }

        vista.metrica1.txtEtiqueta.text = "Usuarios"
        vista.metrica1.txtValor.text = "1.284"
        vista.metrica2.txtEtiqueta.text = "APIs publicadas"
        vista.metrica2.txtValor.text = "96"
        vista.metrica3.txtEtiqueta.text = "Ventas del mes"
        vista.metrica3.txtValor.text = "$18,4 M"
        vista.metrica4.txtEtiqueta.text = "Por revisar"
        vista.metrica4.txtValor.text = porRevisar.toString()
        vista.metrica4.txtValor.setTextColor(ContextCompat.getColor(requireContext(), R.color.naranja))

        // Accesos rapidos a las otras pantallas del administrador
        val principal = requireActivity() as RolActivity
        vista.btnUsuarios.setOnClickListener { principal.irA(R.id.nav_usuarios) }
        vista.btnProductos.setOnClickListener { principal.irA(R.id.nav_productos) }
        vista.btnReportes.setOnClickListener { principal.irA(R.id.nav_reportes) }
        vista.btnCategorias.setOnClickListener { principal.abrir(CategoriasFragment(), "Categorías") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
