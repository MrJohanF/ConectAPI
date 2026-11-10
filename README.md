# ConectAPI

Tienda virtual Android de APIs para desarrolladores. Proyecto de la asignatura **Desarrollo de Aplicaciones Móviles Nativas** (Fundación Universitaria Compensar).

En esta entrega está hecha toda la parte visual (frontend) de la app: las pantallas, la navegación y los recursos. Los datos son de ejemplo y se guardan en memoria; la conexión con Firebase y la pasarela de pagos se hace en la siguiente entrega.

**Integrante:** Harold Johan Fernández Hernández

## Cómo abrirlo

1. Abrir la carpeta en Android Studio.
2. Esperar a que Gradle sincronice.
3. Ejecutar en un emulador o celular con Android 8.0 o superior.

Para generar el APK: `./gradlew assembleDebug` (queda en `app/build/outputs/apk/debug/`).

En la pantalla de inicio de sesión se escribe cualquier correo y una contraseña de 8 caracteres o más, y se elige con qué rol entrar (Comprador, Vendedor o Admin).

## Pantallas

| Rol | Pantallas |
|---|---|
| Todos | Splash, iniciar sesión, crear cuenta, recuperar contraseña, ingreso con huella, menú lateral y menú inferior |
| Comprador (azul) | Inicio, catálogo, detalle del producto, carrito, pasarela de pagos, mis compras y perfil |
| Vendedor (verde) | Panel, mis APIs (crear, editar y eliminar), planes de precio, pedidos recibidos y perfil |
| Administrador (grafito) | Dashboard, usuarios (listar, crear, editar y eliminar), productos, categorías, reporte de ventas y perfil |

## Tecnologías

- Kotlin y layouts en XML
- Material Design 3 (Material Components)
- ViewBinding
- Activities + Fragments con DrawerLayout y BottomNavigationView
- RecyclerView
- BiometricPrompt para la huella

## Organización del código

```
app/src/main/java/co/edu/ucompensar/conectapi/
  acceso/      splash, login, registro, recuperar contraseña, huella
  base/        RolActivity (menú lateral + menú inferior) y navegación
  comprador/   pantallas del comprador
  vendedor/    pantallas del vendedor
  admin/       pantallas del administrador
  comun/       perfil (lo usan los tres roles)
  datos/       modelos y datos de ejemplo

app/src/main/res/
  layout/      pantallas (activity_, fragment_) y filas de listas (item_)
  drawable/    fondos, formas, degradados e iconos vectoriales
  values/      colores, temas por rol, estilos, textos, tamaños y listas
  menu/        menú inferior y lateral de cada rol
  color/       selectores de color
```

Cada rol tiene su propio tema (`Theme.ConectAPI`, `Theme.ConectAPI.Vendedor` y `Theme.ConectAPI.Admin`). Los layouts usan `?attr/colorPrimary`, así la misma pantalla cambia de color según el rol.
