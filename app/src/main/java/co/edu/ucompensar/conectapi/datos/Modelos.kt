package co.edu.ucompensar.conectapi.datos

// Clases sencillas que representan la informacion que se muestra en las pantallas.
// Por ahora no hay base de datos: esta actividad es solo la parte visual (frontend).

enum class Rol(val nombre: String) {
    COMPRADOR("Comprador"),
    VENDEDOR("Vendedor"),
    ADMIN("Administrador")
}

data class Plan(
    var nombre: String,
    var llamadas: Int,
    var precio: Int
)

data class Api(
    val id: Int,
    var nombre: String,
    var vendedor: String,
    var categoria: String,
    var descripcion: String,
    var calificacion: Double,
    var color: Int,              // recurso de color del logo
    var estado: String,          // Aprobada, En revisión, Rechazada, Borrador, Pausada
    var planes: MutableList<Plan>,
    var clientes: Int = 0,
    var urlBase: String = "",
    var documentacion: String = ""
) {
    val precioDesde: Int get() = planes.filter { it.precio > 0 }.minOfOrNull { it.precio } ?: 0
}

data class ItemCarrito(val api: Api, val plan: Plan, var meses: Int)

data class Compra(
    val api: Api,
    val plan: Plan,
    val vence: String,
    val llave: String,
    val uso: Int
)

data class Venta(
    val producto: String,
    val comprador: String,
    val fecha: String,
    val valor: Int,
    val estado: String
)

data class Usuario(
    val id: Int,
    var nombre: String,
    var correo: String,
    var rol: Rol,
    var estado: String           // Activo, Pendiente, Bloqueado
)

data class Categoria(var nombre: String, val cantidad: Int)
