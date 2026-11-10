package co.edu.ucompensar.conectapi.acceso

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.databinding.ActivityRecuperarBinding

// G5 · Recuperar contrasena por correo
class RecuperarActivity : AppCompatActivity() {

    private lateinit var vista: ActivityRecuperarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityRecuperarBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.barra.setNavigationOnClickListener { finish() }
        vista.btnVolver.setOnClickListener { finish() }

        vista.btnEnviar.setOnClickListener {
            val correo = vista.campoCorreo.text.toString().trim()
            if (!correo.contains("@")) {
                vista.cajaCorreo.error = "Escribe un correo válido"
            } else {
                vista.cajaCorreo.error = null
                vista.txtAviso.visibility = View.VISIBLE
                vista.btnEnviar.isEnabled = false
            }
        }
    }
}
