package co.edu.ucompensar.conectapi.base

import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.databinding.ActivityRolBinding
import co.edu.ucompensar.conectapi.datos.Sesion
import co.edu.ucompensar.conectapi.mensaje

// Clase base de las tres pantallas principales (Comprador, Vendedor y Administrador).
// Aqui esta todo lo que es igual para los tres: barra superior, menu inferior y menu lateral.
// Cada rol solo dice que menus usa y que fragment se abre con cada opcion.
abstract class RolActivity : AppCompatActivity() {

    protected lateinit var vista: ActivityRolBinding

    abstract val menuInferior: Int
    abstract val menuLateral: Int
    abstract fun pantallaPara(idOpcion: Int): Fragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityRolBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.txtRol.text = Sesion.rol.nombre
        vista.barra.setNavigationOnClickListener { vista.cajon.openDrawer(GravityCompat.START) }

        // Menu inferior (Bottom Navigation)
        vista.menuInferior.inflateMenu(menuInferior)
        vista.menuInferior.setOnItemSelectedListener { opcion ->
            mostrar(opcion.itemId, opcion.title.toString())
            true
        }

        // Menu lateral (Drawer)
        vista.menuLateral.inflateMenu(menuLateral)
        val encabezado = vista.menuLateral.getHeaderView(0)
        encabezado.findViewById<TextView>(R.id.txtIniciales).text = Sesion.iniciales()
        encabezado.findViewById<TextView>(R.id.txtNombre).text = Sesion.nombre
        encabezado.findViewById<TextView>(R.id.txtCorreo).text = "${Sesion.correo} · ${Sesion.rol.nombre}"

        vista.menuLateral.setNavigationItemSelectedListener { opcion ->
            vista.cajon.closeDrawer(GravityCompat.START)
            when {
                opcion.itemId == R.id.nav_salir -> cerrarSesion()
                opcion.itemId == R.id.nav_ayuda -> mensaje("Ayuda y PQR: te respondemos en máximo 15 días hábiles")
                // Si la opcion tambien esta en el menu inferior, se marca alla
                vista.menuInferior.menu.findItem(opcion.itemId) != null -> irA(opcion.itemId)
                else -> mostrar(opcion.itemId, opcion.title.toString())
            }
            true
        }

        // El boton atras primero cierra el menu lateral si esta abierto
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (vista.cajon.isDrawerOpen(GravityCompat.START)) {
                    vista.cajon.closeDrawer(GravityCompat.START)
                } else {
                    finish()
                }
            }
        })

        if (savedInstanceState == null) {
            val primera = vista.menuInferior.menu.getItem(0)
            mostrar(primera.itemId, primera.title.toString())
        }
    }

    // Cambia de pestaña desde cualquier fragment (por ejemplo los accesos rapidos)
    fun irA(idOpcion: Int) {
        vista.menuInferior.selectedItemId = idOpcion
    }

    // Abre una pantalla que no esta en el menu inferior
    fun abrir(fragment: Fragment, titulo: String) {
        vista.barra.title = titulo
        supportFragmentManager.beginTransaction().replace(R.id.contenedor, fragment).commit()
    }

    private fun mostrar(idOpcion: Int, titulo: String) {
        val fragment = pantallaPara(idOpcion) ?: return
        abrir(fragment, if (idOpcion == R.id.nav_inicio) getString(R.string.app_name) else titulo)
    }
}
