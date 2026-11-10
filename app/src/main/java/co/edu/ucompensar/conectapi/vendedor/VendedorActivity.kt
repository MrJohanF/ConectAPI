package co.edu.ucompensar.conectapi.vendedor

import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.base.RolActivity
import co.edu.ucompensar.conectapi.comun.PerfilFragment

// Pantalla principal del VENDEDOR (tema verde)
class VendedorActivity : RolActivity() {

    override val menuInferior = R.menu.menu_inferior_vendedor
    override val menuLateral = R.menu.menu_lateral_vendedor

    override fun pantallaPara(idOpcion: Int): Fragment? = when (idOpcion) {
        R.id.nav_panel -> PanelVendedorFragment()
        R.id.nav_mis_apis -> MisApisFragment()
        R.id.nav_ventas -> VentasFragment()
        R.id.nav_perfil -> PerfilFragment()
        else -> null
    }
}
