package co.edu.ucompensar.conectapi.datos

// Guarda quien inicio sesion mientras la app esta abierta
object Sesion {
    var rol: Rol = Rol.COMPRADOR
    var nombre: String = "Laura Gómez"
    var correo: String = "laura@correo.com"
    var ciudad: String = "Bogotá"
    var telefono: String = "300 123 4567"

    fun iniciar(rolElegido: Rol, correoEscrito: String) {
        rol = rolElegido
        correo = correoEscrito
        nombre = when (rolElegido) {
            Rol.COMPRADOR -> "Laura Gómez"
            Rol.VENDEDOR -> DatosDemo.MI_EMPRESA
            Rol.ADMIN -> "Carlos Admin"
        }
    }

    // Primeras letras del nombre para el circulo del perfil
    fun iniciales(texto: String = nombre): String =
        texto.split(" ").filter { it.isNotBlank() }.take(2).joinToString("") { it.first().uppercase() }
}
