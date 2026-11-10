package co.edu.ucompensar.conectapi.base

import android.content.Context
import android.content.Intent
import co.edu.ucompensar.conectapi.acceso.LoginActivity
import co.edu.ucompensar.conectapi.admin.AdminActivity
import co.edu.ucompensar.conectapi.comprador.CompradorActivity
import co.edu.ucompensar.conectapi.datos.Rol
import co.edu.ucompensar.conectapi.datos.Sesion
import co.edu.ucompensar.conectapi.vendedor.VendedorActivity

// Abre el panel que le corresponde al rol que inicio sesion (RF-04)
fun Context.abrirPanelDelRol() {
    val destino = when (Sesion.rol) {
        Rol.COMPRADOR -> CompradorActivity::class.java
        Rol.VENDEDOR -> VendedorActivity::class.java
        Rol.ADMIN -> AdminActivity::class.java
    }
    val intent = Intent(this, destino)
    // Se limpian las pantallas anteriores para que "atras" no vuelva al login
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

fun Context.cerrarSesion() {
    val intent = Intent(this, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}
