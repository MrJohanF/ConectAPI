package co.edu.ucompensar.conectapi.comun

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.base.cerrarSesion
import co.edu.ucompensar.conectapi.databinding.FragmentPerfilBinding
import co.edu.ucompensar.conectapi.datos.Rol
import co.edu.ucompensar.conectapi.datos.Sesion
import co.edu.ucompensar.conectapi.mensaje
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// Perfil del usuario. Es el mismo fragment para los tres roles.
class PerfilFragment : Fragment() {

    private var _vista: FragmentPerfilBinding? = null
    private val vista get() = _vista!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _vista = FragmentPerfilBinding.inflate(inflater, container, false)
        return vista.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Texto de la opcion que cambia segun el rol
        vista.opExtra.text = when (Sesion.rol) {
            Rol.COMPRADOR -> "Datos de facturación"
            Rol.VENDEDOR -> "Documentos (RUT, cámara de comercio)"
            Rol.ADMIN -> "Registro de acciones (auditoría)"
        }

        vista.opEditar.setOnClickListener {
            startActivity(Intent(requireContext(), EditarPerfilActivity::class.java))
        }
        vista.btnFoto.setOnClickListener { requireContext().mensaje("Aquí se abre la cámara para tomar la foto") }
        vista.opCiudad.setOnClickListener { requireContext().mensaje("Aquí se usa la ubicación para detectar la ciudad") }
        vista.opExtra.setOnClickListener { requireContext().mensaje(vista.opExtra.text.toString()) }
        vista.opDatos.setOnClickListener { requireContext().mensaje("Puedes descargar o corregir tus datos (Ley 1581)") }

        vista.opClave.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Cambiar contraseña")
                .setMessage("Te enviaremos un enlace a ${Sesion.correo} para crear una contraseña nueva.")
                .setPositiveButton("Enviar enlace") { _, _ -> requireContext().mensaje("Enlace enviado") }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        vista.btnSalir.setOnClickListener { requireContext().cerrarSesion() }

        vista.btnEliminar.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("¿Eliminar tu cuenta?")
                .setMessage("La cuenta se desactiva por 30 días y luego se borra. Las facturas se conservan porque la ley lo exige.")
                .setPositiveButton("Eliminar") { _, _ -> requireContext().cerrarSesion() }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    // Se llenan los datos aqui para que se actualicen al volver de "Editar mis datos"
    override fun onResume() {
        super.onResume()
        vista.txtIniciales.text = Sesion.iniciales()
        vista.txtNombre.text = Sesion.nombre
        vista.txtCorreo.text = "${Sesion.correo} · ${Sesion.rol.nombre}"
        vista.opCiudad.text = "Ciudad: ${Sesion.ciudad}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vista = null
    }
}
