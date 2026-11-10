package co.edu.ucompensar.conectapi.acceso

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.base.abrirPanelDelRol
import co.edu.ucompensar.conectapi.databinding.ActivityLoginBinding
import co.edu.ucompensar.conectapi.datos.Rol
import co.edu.ucompensar.conectapi.datos.Sesion
import co.edu.ucompensar.conectapi.mensaje

// G2 · Iniciar sesion
class LoginActivity : AppCompatActivity() {

    private lateinit var vista: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.btnEntrar.setOnClickListener {
            if (camposCompletos()) {
                guardarSesion()
                abrirPanelDelRol()
            }
        }

        vista.btnHuella.setOnClickListener {
            guardarSesion()
            startActivity(Intent(this, HuellaActivity::class.java))
        }

        vista.btnOlvide.setOnClickListener {
            startActivity(Intent(this, RecuperarActivity::class.java))
        }

        vista.btnRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        // Estos botones se conectan con Firebase Authentication en la siguiente actividad
        vista.btnGoogle.setOnClickListener { mensaje("Ingreso con Google: se conecta en la siguiente entrega") }
        vista.btnGithub.setOnClickListener { mensaje("Ingreso con GitHub: se conecta en la siguiente entrega") }
    }

    private fun camposCompletos(): Boolean {
        val correo = vista.campoCorreo.text.toString().trim()
        val clave = vista.campoClave.text.toString()
        if (correo.isEmpty() || !correo.contains("@")) {
            vista.campoCorreo.error = "Escribe un correo válido"
            return false
        }
        if (clave.length < 8) {
            vista.campoClave.error = "La contraseña tiene mínimo 8 caracteres"
            return false
        }
        return true
    }

    private fun rolElegido(): Rol = when (vista.grupoRol.checkedButtonId) {
        R.id.btnRolVendedor -> Rol.VENDEDOR
        R.id.btnRolAdmin -> Rol.ADMIN
        else -> Rol.COMPRADOR
    }

    private fun guardarSesion() {
        val correo = vista.campoCorreo.text.toString().trim().ifEmpty { "laura@correo.com" }
        Sesion.iniciar(rolElegido(), correo)
    }
}
