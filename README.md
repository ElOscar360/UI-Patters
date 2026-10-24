# Taller para la Casa – User Interface Patterns (Java)

Controlador de interfaz en consola para un **Sistema de Configuración y Reservas Universitarias**,
que aplica un patrón de cada categoría de UI Patterns y evita los Dark Patterns.

## Estructura del proyecto

```
src/
├── navigation/BreadcrumbNavigator.java   # Breadcrumbs + Tabs
├── content/TarjetaElemento.java          # Progressive Disclosure (Acordeón)
├── input/ValidadorForgiving.java         # Forgiving Format
├── state/GestorSesionUI.java             # Lazy Registration + Completeness Meter + Toast
└── main/Main.java                        # Casos de prueba en consola
```


## Patrones implementados

| Categoría | Patrón | Clase |
|---|---|---|
| Navegación | Breadcrumbs + Tabs | `BreadcrumbNavigator` |
| Contenido y Estructura | Progressive Disclosure | `TarjetaElemento` |
| Entrada de Datos | Forgiving Format | `ValidadorForgiving` |
| Entrada de Datos | Lazy Registration | `GestorSesionUI` |
| Feedback y Estado | Completeness Meter + Toast | `GestorSesionUI` |

## Casos de prueba (en `Main`)

1. **Navegación:** la ruta cambia a `Inicio > Catalogo > Servidor Principal` y luego `Inicio > Reservas > Laboratorio de Redes`.
2. **Progressive Disclosure:** con `verAvanzado = false` solo se ve nombre y estado; con `true` se despliega IP, puerto y RAM.
3. **Forgiving Format:** `"+57 (310) 999-8877"` → `"3109998877"` sin excepciones.
4. **Completeness Meter:** el progreso sube 33% → 67% → 100% al llenar los campos.
5. **Lazy Registration + Toast:** sin sesión se guarda un borrador y se pide crear cuenta; con sesión activa se muestra el Toast de éxito.

## Reflexión ética: cero Dark Patterns

La implementación no usa validaciones rígidas: el teléfono y la fecha se aceptan en cualquier formato y se
normalizan en silencio, así que nunca se culpa al usuario (sin *Misdirection* ni errores intimidantes). El usuario
puede armar su reserva sin registrarse y solo se le pide cuenta al guardar, conservando su avance en un borrador.
Cancelar la reserva y cerrar sesión son acciones de **un solo paso** (sin *Roach Motel*), con mensajes neutros
como "Reserva cancelada." y sin textos que avergüencen al usuario (sin *Confirmshaming*). Además, el sistema
no muestra costos ocultos ni cargos adicionales a último momento (sin *Drip Pricing*).
