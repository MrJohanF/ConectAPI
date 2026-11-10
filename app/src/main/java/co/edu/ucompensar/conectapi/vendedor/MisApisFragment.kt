package co.edu.ucompensar.conectapi.vendedor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.AdaptadorSimple
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.FragmentMisApisBinding
import co.edu.ucompensar.conectapi.datos.Api
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.mensaje
import co.edu.ucompensar.conectapi.mostrarEstado
import co.edu.ucompensar.conectapi.pintarFondo
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// V2 · Panel de productos del vendedor (CRUD): lista, crear, editar y eliminar
class MisApisFragment : Fragment() {

    private var _vista: FragmentMisApisBinding? = null
    private val vista get() = _vista!!

    private lateinit var adaptador: AdaptadorSimple<Api>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentMisApisBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adaptador = AdaptadorSimple(R.layout.item_mi_api, DatosDemo.misApis()) { fila, api ->
            val logo = fila.findViewById<TextView>(R.id.txtLogo)
            logo.text = api.nombre.take(1)
            logo.pintarFondo(api.color)
            fila.findViewById<TextView>(R.id.txtNombre).text = api.nombre
            fila.findViewById<TextView>(R.id.txtDetalle).text = "${api.planes.size} planes · ${api.clientes} clientes"
            fila.findViewById<TextView>(R.id.txtEstado).mostrarEstado(api.estado)

            fila.findViewById<View>(R.id.btnEditar).setOnClickListener { abrirFormulario(api.id) }
            fila.findViewById<View>(R.id.btnBorrar).setOnClickListener { confirmarEliminar(api) }
        }
        vista.listaApis.adapter = adaptador

        vista.btnNueva.setOnClickListener { abrirFormulario(0) } // 0 = API nueva
    }

    // Al volver del formulario se actualiza la lista
    override fun onResume() {
        super.onResume()
        adaptador.cambiarDatos(DatosDemo.misApis())
    }

    private fun abrirFormulario(idApi: Int) {
        val intent = Intent(requireContext(), FormApiActivity::class.java)
        intent.putExtra("idApi", idApi)
        startActivity(intent)
    }

    private fun confirmarEliminar(api: Api) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("¿Eliminar ${api.nombre}?")
            .setMessage(
                if (api.clientes > 0) "Tiene ${api.clientes} clientes activos: no se borra, se marca como retirada y deja de venderse."
                else "Esta API se quita de la tienda."
            )
            .setPositiveButton("Eliminar") { _, _ ->
                DatosDemo.apis.remove(api)
                adaptador.cambiarDatos(DatosDemo.misApis())
                requireContext().mensaje("API eliminada")
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
