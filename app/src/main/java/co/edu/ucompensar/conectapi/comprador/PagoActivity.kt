package co.edu.ucompensar.conectapi.comprador

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.edu.ucompensar.conectapi.databinding.ActivityPagoBinding
import co.edu.ucompensar.conectapi.datos.Compra
import co.edu.ucompensar.conectapi.datos.DatosDemo
import co.edu.ucompensar.conectapi.mensaje
import co.edu.ucompensar.conectapi.pesos

// C4 · Pasarela de pagos. El pago real se conecta con Wompi o Mercado Pago en la siguiente entrega;
// aqui se simula la respuesta para mostrar el recorrido completo.
class PagoActivity : AppCompatActivity() {

    private lateinit var vista: ActivityPagoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityPagoBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.barra.setNavigationOnClickListener { finish() }

        vista.txtProductos.text = DatosDemo.carrito.joinToString("\n") {
            "• ${it.api.nombre} · ${it.plan.nombre} × ${it.meses} mes(es) — ${it.api.vendedor}"
        }
        vista.txtIva.text = "Subtotal ${DatosDemo.subtotal().pesos()} + IVA ${DatosDemo.iva().pesos()}"
        vista.txtTotal.text = DatosDemo.total().pesos()
        vista.btnPagar.text = "Pagar ${DatosDemo.total().pesos()}"

        vista.btnPagar.setOnClickListener {
            if (!vista.checkCondiciones.isChecked) {
                mensaje("Marca la casilla de condiciones para continuar")
                return@setOnClickListener
            }
            procesarPago()
        }
    }

    private fun procesarPago() {
        // Se bloquea el boton para que no se pueda pagar dos veces (RNF-03)
        vista.btnPagar.isEnabled = false
        vista.btnPagar.text = "Procesando pago…"
        vista.cargando.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            // Cada producto pagado se convierte en una compra con su llave de acceso
            DatosDemo.carrito.forEach { item ->
                val llave = "pk_live_••••••••••••" + (1000..9999).random()
                DatosDemo.compras.add(0, Compra(item.api, item.plan, "12 dic 2026", llave, 0))
            }
            DatosDemo.carrito.clear()
            DatosDemo.pagoReciente = true
            finish()
        }, 1500)
    }
}
