package co.edu.ucompensar.conectapi.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.AdaptadorSimple
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.FragmentCategoriasBinding
import co.edu.ucompensar.conectapi.datos.Categoria
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.mensaje
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// A5 · Categorias: crear, ver, editar y borrar
class CategoriasFragment : Fragment() {

    private var _vista: FragmentCategoriasBinding? = null
    private val vista get() = _vista!!

    private lateinit var adaptador: AdaptadorSimple<Categoria>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentCategoriasBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adaptador = AdaptadorSimple(R.layout.item_categoria, DatosDemo.categorias.toList()) { fila, categoria ->
            fila.findViewById<TextView>(R.id.txtNombre).text = "${categoria.nombre}  (${categoria.cantidad} APIs)"
            fila.findViewById<View>(R.id.btnEditar).setOnClickListener { editar(categoria) }
            fila.findViewById<View>(R.id.btnBorrar).setOnClickListener {
                // Regla del RF-18: no se puede borrar una categoria que tenga APIs
                if (categoria.cantidad > 0) {
                    requireContext().mensaje("No se puede borrar: tiene ${categoria.cantidad} APIs")
                } else {
                    DatosDemo.categorias.remove(categoria)
                    actualizar()
                }
            }
        }
        vista.listaCategorias.adapter = adaptador

        vista.btnAgregar.setOnClickListener {
            val nombre = vista.campoNombre.text.toString().trim()
            if (nombre.isEmpty()) {
                vista.campoNombre.error = "Escribe el nombre"
            } else {
                DatosDemo.categorias.add(Categoria(nombre, 0))
                vista.campoNombre.text?.clear()
                actualizar()
            }
        }
    }

    private fun editar(categoria: Categoria) {
        val campo = EditText(requireContext())
        campo.setText(categoria.nombre)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Editar categoría")
            .setView(campo)
            .setPositiveButton("Guardar") { _, _ ->
                categoria.nombre = campo.text.toString().trim().ifEmpty { categoria.nombre }
                actualizar()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun actualizar() = adaptador.cambiarDatos(DatosDemo.categorias.toList())

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
