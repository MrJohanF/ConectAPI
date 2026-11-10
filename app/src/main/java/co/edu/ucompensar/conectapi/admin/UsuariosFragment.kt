package co.edu.ucompensar.conectapi.admin

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
import co.edu.ucompensar.conectapi.databinding.FragmentUsuariosBinding
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.datos.Rol
import co.edu.ucompensar.conectapi.datos.Sesion
import co.edu.ucompensar.conectapi.datos.Usuario
import co.edu.ucompensar.conectapi.mostrarEstado
import co.edu.ucompensar.conectapi.pintarFondo

// A2 · Gestion de usuarios: listado con buscador y filtro por rol
class UsuariosFragment : Fragment() {

    private var _vista: FragmentUsuariosBinding? = null
    private val vista get() = _vista!!

    private lateinit var adaptador: AdaptadorSimple<Usuario>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentUsuariosBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adaptador = AdaptadorSimple(R.layout.item_usuario, DatosDemo.usuarios.toList()) { fila, usuario ->
            val iniciales = fila.findViewById<TextView>(R.id.txtIniciales)
            iniciales.text = Sesion.iniciales(usuario.nombre)
            iniciales.pintarFondo(colorDelRol(usuario.rol))
            fila.findViewById<TextView>(R.id.txtNombre).text = usuario.nombre
            fila.findViewById<TextView>(R.id.txtRol).text = "${usuario.rol.nombre} · ${usuario.correo}"
            fila.findViewById<TextView>(R.id.txtEstado).mostrarEstado(usuario.estado)
            fila.setOnClickListener { abrirFormulario(usuario.id) }
        }
        vista.listaUsuarios.adapter = adaptador

        vista.btnNuevo.setOnClickListener { abrirFormulario(0) } // 0 = usuario nuevo
        vista.campoBuscar.doAfterTextChanged { filtrar() }
        vista.grupoRol.setOnCheckedStateChangeListener { _, _ -> filtrar() }
    }

    override fun onResume() {
        super.onResume()
        filtrar()
    }

    private fun filtrar() {
        val texto = vista.campoBuscar.text.toString().trim().lowercase()
        val rol = when (vista.grupoRol.checkedChipId) {
            R.id.chipCompradores -> Rol.COMPRADOR
            R.id.chipVendedores -> Rol.VENDEDOR
            R.id.chipAdmins -> Rol.ADMIN
            else -> null
        }
        adaptador.cambiarDatos(DatosDemo.usuarios.filter { usuario ->
            (rol == null || usuario.rol == rol) &&
                (usuario.nombre.lowercase().contains(texto) || usuario.correo.lowercase().contains(texto))
        })
    }

    private fun colorDelRol(rol: Rol) = when (rol) {
        Rol.COMPRADOR -> R.color.azul
        Rol.VENDEDOR -> R.color.verde
        Rol.ADMIN -> R.color.grafito
    }

    private fun abrirFormulario(idUsuario: Int) {
        val intent = Intent(requireContext(), FormUsuarioActivity::class.java)
        intent.putExtra("idUsuario", idUsuario)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
