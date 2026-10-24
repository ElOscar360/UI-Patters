package input;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Patron de Entrada de Datos: Forgiving Format.
 * Acepta casi cualquier formato y lo normaliza, sin lanzar excepciones ni culpar al usuario.
 */
public class ValidadorForgiving {

    private static final Pattern NO_DIGITOS = Pattern.compile("\\D");
    private static final Pattern PREFIJO_57 = Pattern.compile("^(?:\\+|00)?57(?=\\d{10}$)");

    // dd/mm/aaaa, dd-mm-aaaa, dd.mm.aaaa, dd mm aaaa
    private static final Pattern FECHA_DMA =
            Pattern.compile("^(\\d{1,2})[\\s/.\\-]+(\\d{1,2})[\\s/.\\-]+(\\d{4})$");
    // aaaa/mm/dd, aaaa-mm-dd, ...
    private static final Pattern FECHA_AMD =
            Pattern.compile("^(\\d{4})[\\s/.\\-]+(\\d{1,2})[\\s/.\\-]+(\\d{1,2})$");

    /**
     * "+57 (310) 999-8877" -> "3109998877".
     * Nunca lanza excepcion: ante entrada nula o vacia devuelve cadena vacia.
     */
    public String normalizarTelefono(String entrada) {
        if (entrada == null) {
            return "";
        }
        // Se quitan separadores comunes, conservando el "+" para reconocer el prefijo +57.
        String limpio = entrada.trim().replaceAll("[\\s()\\-.]", "");
        limpio = PREFIJO_57.matcher(limpio).replaceFirst("");
        return NO_DIGITOS.matcher(limpio).replaceAll("");
    }

    /**
     * Normaliza fechas a formato ISO (aaaa-MM-dd).
     * Si no se puede interpretar, devuelve Optional.empty() (sin error visible para el usuario).
     */
    public Optional<String> normalizarFecha(String entrada) {
        if (entrada == null) {
            return Optional.empty();
        }
        String texto = entrada.trim();

        Matcher dma = FECHA_DMA.matcher(texto);
        if (dma.matches()) {
            return formatear(dma.group(3), dma.group(2), dma.group(1));
        }
        Matcher amd = FECHA_AMD.matcher(texto);
        if (amd.matches()) {
            return formatear(amd.group(1), amd.group(2), amd.group(3));
        }
        return Optional.empty();
    }

    private Optional<String> formatear(String anio, String mes, String dia) {
        int m = Integer.parseInt(mes);
        int d = Integer.parseInt(dia);
        if (m < 1 || m > 12 || d < 1 || d > 31) {
            return Optional.empty();
        }
        return Optional.of(String.format("%s-%02d-%02d", anio, m, d));
    }
}
