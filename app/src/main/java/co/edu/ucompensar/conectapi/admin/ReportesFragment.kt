package co.edu.ucompensar.conectapi.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.FragmentReportesBinding
import co.edu.ucompensar.conectapi.dibujarBarras
import co.edu.ucompensar.conectapi.mensaje

// A6 · Reporte de ventas con filtro por periodo
class ReportesFragment : Fragment() {

    private var _vista: FragmentReportesBinding? = null
    private val vista get() = _vista!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentReportesBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vista.metrica1.txtEtiqueta.text = "Ventas"
        vista.metrica2.txtEtiqueta.text = "Comisión (15 %)"

        vista.grupoPeriodo.setOnCheckedStateChangeListener { _, _ -> mostrarPeriodo() }
        mostrarPeriodo()

        vista.btnDescargar.setOnClickListener { requireContext().mensaje("Reporte PDF generado para contabilidad") }
    }

    // Cambia los numeros y la grafica segun el chip elegido
    private fun mostrarPeriodo() {
        when (vista.grupoPeriodo.checkedChipId) {
            R.id.chipTrimestre -> {
                vista.metrica1.txtValor.text = "$49,7 M"
                vista.metrica2.txtValor.text = "$7,46 M"
                dibujarBarras(vista.grafica, listOf("ago" to 70, "sep" to 84, "oct" to 100))
            }
            R.id.chipAnio -> {
                vista.metrica1.txtValor.text = "$162 M"
                vista.metrica2.txtValor.text = "$24,3 M"
                dibujarBarras(vista.grafica, listOf("T1" to 45, "T2" to 62, "T3" to 80, "T4" to 100))
            }
            else -> {
                vista.metrica1.txtValor.text = "$18,4 M"
                vista.metrica2.txtValor.text = "$2,76 M"
                dibujarBarras(vista.grafica, listOf("sem 1" to 55, "sem 2" to 72, "sem 3" to 68, "sem 4" to 100))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
