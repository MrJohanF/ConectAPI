package co.edu.ucompensar.conectapi.comun

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.ActivityEditarPerfilBinding
import co.edu.ucompensar.conectapi.datos.Rol
import co.edu.ucompensar.conectapi.datos.Sesion
import co.edu.ucompensar.conectapi.mensaje

// Formulario para actualizar los datos del perfil
class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var vista: ActivityEditarPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Esta pantalla la usan los tres roles, por eso el tema se pone por codigo
        setTheme(
            when (Sesion.rol) {
                Rol.COMPRADOR -> R.style.Theme_ConectAPI
                Rol.VENDEDOR -> R.style.Theme_ConectAPI_Vendedor
                Rol.ADMIN -> R.style.Theme_ConectAPI_Admin
            }
        )
        super.onCreate(savedInstanceState)
        vista = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.barra.setNavigationOnClickListener { finish() }

        vista.campoNombre.setText(Sesion.nombre)
        vista.campoCorreo.setText(Sesion.correo)
        vista.campoTelefono.setText(Sesion.telefono)
        vista.campoCiudad.setText(Sesion.ciudad)

        vista.btnGuardar.setOnClickListener {
            if (vista.campoNombre.text.isNullOrBlank()) {
                vista.campoNombre.error = "El nombre no puede quedar vacío"
                return@setOnClickListener
            }
            Sesion.nombre = vista.campoNombre.text.toString().trim()
            Sesion.correo = vista.campoCorreo.text.toString().trim()
            Sesion.telefono = vista.campoTelefono.text.toString().trim()
            Sesion.ciudad = vista.campoCiudad.text.toString().trim()
            mensaje("Datos actualizados")
            finish()
        }
    }
}
