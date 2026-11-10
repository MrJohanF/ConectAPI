package co.edu.ucompensar.conectapi.acceso

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.databinding.ActivitySplashBinding

// G1 · Pantalla de bienvenida. Muestra el logo y despues de un momento abre el inicio de sesion.
class SplashActivity : AppCompatActivity() {

    private lateinit var vista: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(vista.root)

        // Animacion de la barra de carga de 0 a 100
        ObjectAnimator.ofInt(vista.barraCarga, "progress", 0, 100).setDuration(1600).start()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1800)
    }
}
