package co.edu.ucompensar.conectapi.comprador

import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.base.RolActivity
import co.edu.ucompensar.conectapi.comun.PerfilFragment
import co.edu.ucompensar.conectapi.datos.DatosDemo

// Pantalla principal del COMPRADOR (tema azul)
class CompradorActivity : RolActivity() {

    override val menuInferior = R.menu.menu_inferior_comprador
    override val menuLateral = R.menu.menu_lateral_comprador

    override fun pantallaPara(idOpcion: Int): Fragment? = when (idOpcion) {
        R.id.nav_inicio -> InicioFragment()
        R.id.nav_catalogo -> CatalogoFragment()
        R.id.nav_carrito -> CarritoFragment()
        R.id.nav_compras -> ComprasFragment()
        R.id.nav_perfil -> PerfilFragment()
        else -> null
    }

    override fun onResume() {
        super.onResume()
        // Al volver de la pasarela con el pago aprobado se abre "Compras"
        if (DatosDemo.pagoReciente) irA(R.id.nav_compras)
    }
}
