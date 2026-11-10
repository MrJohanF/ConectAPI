package co.edu.ucompensar.conectapi.datos

import co.edu.ucompensar.conectapi.R

// Datos de ejemplo guardados en memoria para poder mostrar las pantallas con informacion.
// Mas adelante (siguiente actividad) estos datos vendran de Firebase.
object DatosDemo {

    const val MI_EMPRESA = "FinDev SAS"

    val apis = mutableListOf(
        Api(1, "PagoFácil API", "FinDev SAS", "Pagos",
            "Cobra con tarjeta, PSE y Nequi desde tu app con pocas líneas de código.",
            4.8, R.color.morado, "Aprobada",
            mutableListOf(Plan("Gratis", 500, 0), Plan("Básico", 20000, 49900), Plan("Pro", 200000, 149900)),
            48, "https://api.pagofacil.co/v2", "https://docs.pagofacil.co"),
        Api(2, "GeoRuta Colombia", "MapaCo", "Mapas",
            "Calcula rutas y tiempos de entrega entre direcciones de Colombia.",
            4.6, R.color.cian, "Aprobada",
            mutableListOf(Plan("Gratis", 1000, 0), Plan("Pro", 50000, 79900)), 31),
        Api(3, "FacturaYa", "Contadev", "Facturación",
            "Genera facturas electrónicas válidas ante la DIAN en segundos.",
            4.7, R.color.ambar, "Aprobada",
            mutableListOf(Plan("Básico", 300, 39900), Plan("Pro", 3000, 89900)), 22),
        Api(4, "TextoIA", "DataPaz", "IA",
            "Resume y corrige textos en español con inteligencia artificial.",
            4.5, R.color.rosa, "Aprobada",
            mutableListOf(Plan("Gratis", 1000, 0), Plan("Básico", 10000, 29900)), 17),
        Api(5, "ValidaCorreo", "FinDev SAS", "Mensajería",
            "Revisa si un correo existe antes de registrarlo en tu app.",
            0.0, R.color.cian, "En revisión",
            mutableListOf(Plan("Gratis", 500, 0), Plan("Básico", 10000, 29900)), 0,
            "https://api.validacorreo.co/v1", "https://docs.validacorreo.co"),
        Api(6, "SMS Rápido", "FinDev SAS", "Mensajería",
            "Envía mensajes de texto a celulares de Colombia.",
            0.0, R.color.rosa, "Rechazada", mutableListOf(Plan("Básico", 1000, 19900))),
        Api(7, "TasaDólar", "FinDev SAS", "Pagos",
            "Consulta la TRM del día y de fechas anteriores.",
            0.0, R.color.pizarra, "Borrador", mutableListOf()),
        Api(8, "ClimaCO", "DataPaz", "Clima",
            "Pronóstico del clima por ciudad y municipio.",
            0.0, R.color.morado, "En revisión", mutableListOf(Plan("Gratis", 2000, 0)))
    )

    val categorias = mutableListOf(
        Categoria("Pagos", 18), Categoria("Mapas", 12), Categoria("IA", 21),
        Categoria("Mensajería", 15), Categoria("Facturación", 9)
    )

    val carrito = mutableListOf<ItemCarrito>()

    // Se pone en true cuando se acaba de pagar, para mostrar el aviso en Mis compras
    var pagoReciente = false

    val compras = mutableListOf(
        Compra(apis[2], apis[2].planes[1], "12 ene 2027", "pk_live_••••••••••••8c1d", 12)
    )

    val ventas = mutableListOf(
        Venta("PagoFácil · Básico", "L. Gómez", "12 oct", 49900, "Pagado"),
        Venta("PagoFácil · Pro", "DevAndes", "10 oct", 149900, "Pagado"),
        Venta("PagoFácil · Básico", "J. Ruiz", "8 oct", 49900, "Pagado"),
        Venta("PagoFácil · Pro", "TiendaYa", "3 oct", 149900, "Retracto")
    )

    val usuarios = mutableListOf(
        Usuario(1, "Laura Gómez", "laura@correo.com", Rol.COMPRADOR, "Activo"),
        Usuario(2, "FinDev SAS", "ventas@findev.co", Rol.VENDEDOR, "Activo"),
        Usuario(3, "DataPaz", "contacto@datapaz.co", Rol.VENDEDOR, "Pendiente"),
        Usuario(4, "spam01", "spam01@correo.com", Rol.COMPRADOR, "Bloqueado"),
        Usuario(5, "Carlos Admin", "admin@conectapi.co", Rol.ADMIN, "Activo")
    )

    // Valores de la grafica de ventas (ultimos 6 meses), en porcentaje de altura
    val ventasPorMes = listOf("may" to 40, "jun" to 55, "jul" to 48, "ago" to 68, "sep" to 82, "oct" to 100)

    fun apisAprobadas() = apis.filter { it.estado == "Aprobada" }
    fun misApis() = apis.filter { it.vendedor == MI_EMPRESA }
    fun buscarApi(id: Int) = apis.firstOrNull { it.id == id }
    fun siguienteIdApi() = (apis.maxOfOrNull { it.id } ?: 0) + 1
    fun siguienteIdUsuario() = (usuarios.maxOfOrNull { it.id } ?: 0) + 1

    // Cuentas del carrito
    fun subtotal() = carrito.sumOf { it.plan.precio * it.meses }
    fun iva() = (subtotal() * 0.19).toInt()
    fun total() = subtotal() + iva()
}
