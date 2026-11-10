package co.edu.ucompensar.conectapi

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

// Funciones de ayuda que se usan en varias pantallas

// Convierte 49900 en "$49.900"
fun Int.pesos(): String {
    if (this == 0) return "Gratis"
    val formato = NumberFormat.getNumberInstance(Locale("es", "CO"))
    return "$" + formato.format(this)
}

fun Int.miles(): String = NumberFormat.getNumberInstance(Locale("es", "CO")).format(this)

fun Context.mensaje(texto: String) = Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()

// Cambia el color de fondo de una vista que usa un drawable de forma
fun View.pintarFondo(colorRes: Int) {
    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
}

// Pone el texto y los colores de la etiqueta segun el estado
fun TextView.mostrarEstado(estado: String) {
    text = estado
    val (fondo, letra) = when (estado) {
        "Aprobada", "Activo", "Activa", "Pagado" -> R.color.verde_claro to R.color.verde
        "En revisión", "Pendiente" -> R.color.naranja_claro to R.color.naranja
        "Rechazada", "Bloqueado", "Retracto" -> R.color.rojo_claro to R.color.rojo
        else -> R.color.borde to R.color.texto_suave
    }
    pintarFondo(fondo)
    setTextColor(ContextCompat.getColor(context, letra))
}

// Adaptador general para los RecyclerView.
// Recibe el layout de cada fila y una funcion que llena la fila con los datos.
class AdaptadorSimple<T>(
    private val layoutFila: Int,
    private var datos: List<T>,
    private val llenar: (vista: View, dato: T) -> Unit
) : RecyclerView.Adapter<AdaptadorSimple.Fila>() {

    class Fila(vista: View) : RecyclerView.ViewHolder(vista)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Fila {
        val vista = LayoutInflater.from(parent.context).inflate(layoutFila, parent, false)
        return Fila(vista)
    }

    override fun onBindViewHolder(holder: Fila, position: Int) = llenar(holder.itemView, datos[position])

    override fun getItemCount() = datos.size

    fun cambiarDatos(nuevos: List<T>) {
        datos = nuevos
        notifyDataSetChanged()
    }
}

// Dibuja una grafica de barras sencilla. Cada dato es (texto, porcentaje de 0 a 100).
fun dibujarBarras(contenedor: android.widget.LinearLayout, datos: List<Pair<String, Int>>) {
    contenedor.removeAllViews()
    val inflater = LayoutInflater.from(contenedor.context)
    val densidad = contenedor.resources.displayMetrics.density
    datos.forEach { (texto, porcentaje) ->
        val columna = inflater.inflate(R.layout.item_barra, contenedor, false)
        columna.findViewById<TextView>(R.id.txtMes).text = texto
        val barra = columna.findViewById<View>(R.id.barra)
        barra.layoutParams.height = (porcentaje * 1.1f * densidad).toInt() // 100 % = 110dp
        contenedor.addView(columna)
    }
}
