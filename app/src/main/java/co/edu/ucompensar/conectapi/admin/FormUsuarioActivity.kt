package co.edu.ucompensar.conectapi.admin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.databinding.ActivityFormUsuarioBinding
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.datos.Rol
import co.edu.ucompensar.conectapi.datos.Sesion
import co.edu.ucompensar.conectapi.datos.Usuario
import co.edu.ucompensar.conectapi.mensaje
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// A3 · Formulario de usuario: crear, editar y eliminar (CRUD de usuarios)
class FormUsuarioActivity : AppCompatActivity() {

    private lateinit var vista: ActivityFormUsuarioBinding
    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityFormUsuarioBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.barra.setNavigationOnClickListener { finish() }

        val id = intent.getIntExtra("idUsuario", 0)
        usuario = DatosDemo.usuarios.firstOrNull { it.id == id }

        val actual = usuario
        if (actual == null) {
            // Usuario nuevo: valores por defecto
            vista.campoRol.setText(Rol.COMPRADOR.nombre, false)
            vista.campoEstado.setText("Activo", false)
        } else {
            vista.barra.title = "Editar usuario"
            vista.txtIniciales.text = Sesion.iniciales(actual.nombre)
            vista.campoNombre.setText(actual.nombre)
            vista.campoCorreo.setText(actual.correo)
            vista.campoRol.setText(actual.rol.nombre, false)
            vista.campoEstado.setText(actual.estado, false)
            vista.txtNota.text = "Cada cambio queda registrado: quién lo hizo, cuándo y por qué."
            vista.btnEliminar.visibility = View.VISIBLE
        }

        vista.btnGuardar.setOnClickListener { guardar() }

        vista.btnEliminar.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("¿Eliminar a ${actual?.nombre}?")
                .setMessage("La cuenta se desactiva por 30 días y luego se borra.")
                .setPositiveButton("Eliminar") { _, _ ->
                    DatosDemo.usuarios.remove(actual)
                    mensaje("Usuario eliminado")
                    finish()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    private fun guardar() {
        val nombre = vista.campoNombre.text.toString().trim()
        val correo = vista.campoCorreo.text.toString().trim()
        if (nombre.isEmpty()) {
            vista.campoNombre.error = "Escribe el nombre"
            return
        }
        if (!correo.contains("@")) {
            vista.campoCorreo.error = "Escribe un correo válido"
            return
        }
        // Se busca el rol que tiene el mismo nombre que la opcion elegida en la lista
        val rol = Rol.values().first { it.nombre == vista.campoRol.text.toString() }
        val estado = vista.campoEstado.text.toString()

        val actual = usuario
        if (actual == null) {
            DatosDemo.usuarios.add(Usuario(DatosDemo.siguienteIdUsuario(), nombre, correo, rol, estado))
            mensaje("Usuario creado")
        } else {
            actual.nombre = nombre
            actual.correo = correo
            actual.rol = rol
            actual.estado = estado
            mensaje("Cambios guardados")
        }
        finish()
    }
}
