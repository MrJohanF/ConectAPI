package co.edu.ucompensar.conectapi.acceso

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import co.edu.ucompensar.conectapi.base.abrirPanelDelRol
import co.edu.ucompensar.conectapi.databinding.ActivityHuellaBinding

// G4 · Ingreso con huella usando BiometricPrompt (la herramienta oficial de Android)
class HuellaActivity : AppCompatActivity() {

    private lateinit var vista: ActivityHuellaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityHuellaBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.btnHuella.setOnClickListener { pedirHuella() }
        vista.btnUsarClave.setOnClickListener { finish() } // vuelve al login

        pedirHuella()
    }

    private fun pedirHuella() {
        // Primero se revisa si el celular tiene lector y una huella guardada
        val disponible = BiometricManager.from(this).canAuthenticate(BIOMETRIC_WEAK)
        if (disponible != BiometricManager.BIOMETRIC_SUCCESS) {
            vista.txtEstado.text = "Este celular no tiene una huella registrada.\nToca el círculo para continuar en modo demostración."
            vista.btnHuella.setOnClickListener { abrirPanelDelRol() }
            return
        }

        val respuesta = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                abrirPanelDelRol()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                vista.txtEstado.text = errString
            }

            override fun onAuthenticationFailed() {
                vista.txtEstado.text = "No reconocimos la huella. Intenta de nuevo."
            }
        }

        val ventana = BiometricPrompt(this, ContextCompat.getMainExecutor(this), respuesta)
        val datos = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Confirma que eres tú")
            .setSubtitle("Usa tu huella para entrar a ConectAPI")
            .setNegativeButtonText("Usar contraseña")
            .setAllowedAuthenticators(BIOMETRIC_WEAK)
            .build()
        ventana.authenticate(datos)
    }
}
