package co.edu.ucompensar.conectapi.admin

import androidx.fragment.app.Fragment
import co.edu.ucompensar.conectapi.R
import co.edu.ucompensar.conectapi.base.RolActivity
import co.edu.ucompensar.conectapi.comun.PerfilFragment

// Pantalla principal del ADMINISTRADOR (tema grafito)
class AdminActivity : RolActivity() {

    override val menuInferior = R.menu.menu_inferior_admin
    override val menuLateral = R.menu.menu_lateral_admin

    override fun pantallaPara(idOpcion: Int): Fragment? = when (idOpcion) {
        R.id.nav_panel -> PanelAdminFragment()
        R.id.nav_usuarios -> UsuariosFragment()
        R.id.nav_productos -> ProductosAdminFragment()
        R.id.nav_reportes -> ReportesFragment()
        R.id.nav_perfil -> PerfilFragment()
        R.id.nav_categorias -> CategoriasFragment() // solo esta en el menu lateral
        else -> null
    }
}
