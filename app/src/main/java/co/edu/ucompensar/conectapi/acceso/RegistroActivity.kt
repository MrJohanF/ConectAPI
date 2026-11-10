package co.edu.ucompensar.conectapi.acceso

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.databinding.ActivityRegistroBinding
import co.edu.ucompensar.conectapi.mensaje

// G3 · Crear cuenta
class RegistroActivity : AppCompatActivity() {

    private lateinit var vista: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.barra.setNavigationOnClickListener { finish() }

        vista.btnCrear.setOnClickListener {
            when {
                vista.campoNombre.text.isNullOrBlank() -> vista.campoNombre.error = "Escribe tu nombre"
                vista.campoCorreo.text?.contains("@") != true -> vista.campoCorreo.error = "Escribe un correo válido"
                (vista.campoClave.text?.length ?: 0) < 8 -> vista.campoClave.error = "Mínimo 8 caracteres"
                !vista.checkTerminos.isChecked || !vista.checkDatos.isChecked ->
                    mensaje("Debes aceptar los términos y autorizar tus datos")
                else -> {
                    mensaje("Cuenta creada. Revisa tu correo para confirmarla")
                    finish() // vuelve a iniciar sesion
                }
            }
        }
    }
}
