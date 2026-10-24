package content;

/**
 * Patron de Contenido y Estructura: Progressive Disclosure (Acordeon).
 * Muestra primero solo nombre y estado; los detalles tecnicos se revelan bajo demanda.
 */
public class TarjetaElemento {

    private final String nombre;
    private final String estado;
    private final String ip;
    private final int puerto;
    private final int ramUsadaGb;
    private final int ramTotalGb;
    private boolean verAvanzado = false;

    public TarjetaElemento(String nombre, String estado, String ip,
                           int puerto, int ramUsadaGb, int ramTotalGb) {
        this.nombre = nombre;
        this.estado = estado;
        this.ip = ip;
        this.puerto = puerto;
        this.ramUsadaGb = ramUsadaGb;
        this.ramTotalGb = ramTotalGb;
    }

    public void setVerAvanzado(boolean verAvanzado) {
        this.verAvanzado = verAvanzado;
    }

    public boolean isVerAvanzado() {
        return verAvanzado;
    }

    /** Despliega u oculta los detalles con una sola accion. */
    public void alternarDetalles() {
        verAvanzado = !verAvanzado;
    }

    public String render() {
        if (!verAvanzado) {
            return "[Vista Resumen] " + nombre + " \u00b7 Estado: " + estado;
        }
        return "[Acordeon Desplegado] IP: " + ip
                + " | Puerto: " + puerto
                + " | RAM: " + ramUsadaGb + "GB/" + ramTotalGb + "GB";
    }
}
