package main;

import content.TarjetaElemento;
import input.ValidadorForgiving;
import navigation.BreadcrumbNavigator;
import state.GestorSesionUI;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        casoNavegacion();
        casoProgressiveDisclosure();
        casoForgivingFormat();
        casoCompletenessMeter();
        casoLazyRegistrationYToast();
        casoSalidasSinDarkPatterns();
    }

    // CASO 1
    private static void casoNavegacion() {
        System.out.println("--- 1. PATRON DE NAVEGACION (BREADCRUMBS + TABS) ---");
        BreadcrumbNavigator nav = new BreadcrumbNavigator(
                Arrays.asList("Catalogo", "Reservas", "Mi cuenta"));
        nav.mostrarRuta();

        nav.cambiarPestana("Catalogo");
        nav.entrarSubcategoria("Servidor Principal");
        nav.mostrarRuta();

        nav.cambiarPestana("Reservas");
        nav.entrarSubcategoria("Laboratorio de Redes");
        nav.mostrarRuta();
        System.out.println();
    }

    // CASO 2
    private static void casoProgressiveDisclosure() {
        System.out.println("--- 2. PATRON DE CONTENIDO Y ESTRUCTURA (PROGRESSIVE DISCLOSURE) ---");
        TarjetaElemento tarjeta = new TarjetaElemento(
                "Servidor Principal", "Activo", "192.168.1.10", 8080, 4, 16);

        tarjeta.setVerAvanzado(false);
        System.out.println(tarjeta.render());

        System.out.println("[Usuario solicita ver detalles avanzados...]");
        tarjeta.setVerAvanzado(true);
        System.out.println(tarjeta.render());
        System.out.println();
    }

    // CASO 3
    private static void casoForgivingFormat() {
        System.out.println("--- 3. PATRON DE ENTRADA DE DATOS (FORGIVING FORMAT) ---");
        ValidadorForgiving validador = new ValidadorForgiving();

        String[] entradas = {"+57 (310) 999-8877", "+57 (300) 123-4567", "300.123.4567", "310 999 8877"};
        for (String entrada : entradas) {
            System.out.println("Entrada del usuario: \"" + entrada + "\"");
            System.out.println("Telefono normalizado sin error: \""
                    + validador.normalizarTelefono(entrada) + "\"");
        }

        String fecha = "05/11/2026";
        System.out.println("Entrada del usuario: \"" + fecha + "\"");
        System.out.println("Fecha normalizada sin error: \""
                + validador.normalizarFecha(fecha).orElse("(sin interpretar, se mantiene el texto)") + "\"");
        System.out.println();
    }

    // CASO 4
    private static void casoCompletenessMeter() {
        System.out.println("--- 4. PATRON DE FEEDBACK Y ESTADO (COMPLETENESS METER) ---");
        GestorSesionUI gestor = new GestorSesionUI(
                Arrays.asList("Telefono", "Fecha de reserva", "Correo de contacto"));

        gestor.actualizarCampo("Telefono", "3109998877");
        gestor.mostrarProgreso();

        gestor.actualizarCampo("Fecha de reserva", "2026-11-05");
        gestor.mostrarProgreso();

        System.out.println("Usuario ingresa correo: \"estudiante@udistrital.edu.co\"");
        gestor.actualizarCampo("Correo de contacto", "estudiante@udistrital.edu.co");
        gestor.mostrarProgreso();
        System.out.println();
    }

    // CASO 5
    private static void casoLazyRegistrationYToast() {
        System.out.println("--- 5. LAZY REGISTRATION + TOAST ---");
        GestorSesionUI gestor = new GestorSesionUI(
                Arrays.asList("Telefono", "Fecha de reserva", "Correo de contacto"));
        gestor.actualizarCampo("Telefono", "3109998877");
        gestor.actualizarCampo("Fecha de reserva", "2026-11-05");
        gestor.actualizarCampo("Correo de contacto", "estudiante@udistrital.edu.co");

        System.out.println("Usuario intenta guardar sin haber iniciado sesion (usuarioActivo = false)...");
        gestor.intentarGuardar();

        gestor.completarRegistro();

        System.out.println("Usuario intenta guardar de nuevo (usuarioActivo = true)...");
        gestor.intentarGuardar();
        System.out.println();
    }

    // Restriccion etica: salidas en un solo paso y con mensajes neutros
    private static void casoSalidasSinDarkPatterns() {
        System.out.println("--- EXTRA. SALIDAS SIN DARK PATTERNS ---");
        GestorSesionUI gestor = new GestorSesionUI(Arrays.asList("Telefono"));
        gestor.setUsuarioActivo(true);
        gestor.actualizarCampo("Telefono", "3109998877");

        System.out.println("Usuario pulsa 'Cancelar reserva' (un solo paso):");
        gestor.cancelarReserva();
        System.out.println("Usuario pulsa 'Cerrar sesion' (un solo paso):");
        gestor.cerrarSesion();
    }
}
