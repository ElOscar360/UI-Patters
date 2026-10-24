package navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Patron de Navegacion: Breadcrumbs + Tabs.
 * Mantiene la ruta jerarquica actual (Inicio > Seccion > Subseccion).
 */
public class BreadcrumbNavigator {

    private static final String RAIZ = "Inicio";

    private final List<String> pestanas;
    private final List<String> ruta = new ArrayList<>();

    public BreadcrumbNavigator(List<String> pestanas) {
        this.pestanas = new ArrayList<>(pestanas);
        reiniciar();
    }

    /** Cambia de pestana: la ruta vuelve a "Inicio > Pestana". */
    public void cambiarPestana(String pestana) {
        if (!pestanas.contains(pestana)) {
            // Sin culpar al usuario: simplemente se mantiene la ruta actual.
            return;
        }
        reiniciar();
        ruta.add(pestana);
    }

    /** Entra a una subcategoria dentro de la ruta actual. */
    public void entrarSubcategoria(String subcategoria) {
        ruta.add(subcategoria);
    }

    /** Sube un nivel (nunca borra "Inicio"). */
    public void volver() {
        if (ruta.size() > 1) {
            ruta.remove(ruta.size() - 1);
        }
    }

    public String getRuta() {
        return String.join(" > ", ruta);
    }

    public void mostrarRuta() {
        System.out.println("Ruta actual: " + getRuta());
    }

    private void reiniciar() {
        ruta.clear();
        ruta.add(RAIZ);
    }
}
