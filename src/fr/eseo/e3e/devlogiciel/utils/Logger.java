package fr.eseo.e3e.devlogiciel.utils;

/**
 * Utilitaire centralisé pour gérer tous les affichages et erreurs de l'application.
 */
public class Logger {

    private Logger() {}

    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void succes(String message) {
        System.out.println("[OK] " + message);
    }

    public static void avertissement(String message) {
        System.out.println("[ATTENTION] " + message);
    }

    public static void erreur(String message) {
        System.err.println("[ERREUR] " + message);
    }
}