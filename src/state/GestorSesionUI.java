package state;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Patron de Feedback y Estado: Completeness Meter + Toast.
 * Tambien controla la Lazy Registration (borrador temporal hasta crear la cuenta).
 */
public class GestorSesionUI {

    private final Map<String, String> campos = new LinkedHashMap<>();
    private Map<String, String> borrador = null;
    private boolean usuarioActivo = false;

    public GestorSesionUI(List<String> nombresCampos) {
        for (String nombre : nombresCampos) {
            campos.put(nombre, "");
        }
    }

    // ---------- Completeness Meter ----------

    public void actualizarCampo(String nombre, String valor) {
        if (campos.containsKey(nombre)) {
            campos.put(nombre, valor == null ? "" : valor.trim());
        }
    }

    public List<String> camposFaltantes() {
        List<String> faltantes = new ArrayList<>();
        for (Map.Entry<String, String> e : campos.entrySet()) {
            if (e.getValue().isEmpty()) {
                faltantes.add(e.getKey());
            }
        }
        return faltantes;
    }

    /** Devuelve 0, 33, 67 o 100 (para 3 campos). */
    public int calcularProgreso() {
        int total = campos.size();
        if (total == 0) {
            return 0;
        }
        int completos = total - camposFaltantes().size();
        return (int) Math.round(completos * 100.0 / total);
    }

    public void mostrarProgreso() {
        int total = campos.size();
        List<String> faltantes = camposFaltantes();
        int listos = total - faltantes.size();
        int pct = calcularProgreso();

        if (faltantes.isEmpty()) {
            System.out.println("[Progreso: " + pct + "%] \u00a1Formulario completo!");
        } else {
            System.out.println("[Progreso: " + pct + "%] Campos listos: " + listos
                    + " de " + total + " (Falta: " + String.join(", ", faltantes) + ")");
        }
    }

    // ---------- Lazy Registration ----------

    public boolean isUsuarioActivo() {
        return usuarioActivo;
    }

    public void setUsuarioActivo(boolean usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }

    public boolean hayBorrador() {
        return borrador != null;
    }

    /**
     * Intenta guardar la reserva. Sin sesion, conserva un borrador temporal
     * y solicita crear la cuenta; con sesion, guarda y emite el Toast.
     */
    public boolean intentarGuardar() {
        if (!usuarioActivo) {
            borrador = new LinkedHashMap<>(campos);
            System.out.println("[Lazy Registration] Avance guardado en borrador temporal. "
                    + "Por favor cree su cuenta para finalizar.");
            return false;
        }
        borrador = null;
        enviarToast("Reserva guardada exitosamente.");
        return true;
    }

    /** Registro rapido: activa la sesion y recupera el borrador si existia. */
    public void completarRegistro() {
        usuarioActivo = true;
        System.out.println("Usuario completa registro rapido -> Sesion activa = true");
        if (borrador != null) {
            campos.putAll(borrador);
        }
    }

    // ---------- Toast ----------

    public void enviarToast(String mensaje) {
        System.out.println("[Toast de Estado] " + mensaje);
    }

    // ---------- Salidas sin Dark Patterns (un solo paso, mensajes neutros) ----------

    public void cancelarReserva() {
        for (String nombre : campos.keySet()) {
            campos.put(nombre, "");
        }
        borrador = null;
        enviarToast("Reserva cancelada.");
    }

    public void cerrarSesion() {
        usuarioActivo = false;
        enviarToast("Sesion cerrada.");
    }
}
