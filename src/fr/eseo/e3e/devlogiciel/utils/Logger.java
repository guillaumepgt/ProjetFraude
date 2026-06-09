package fr.eseo.e3e.devlogiciel.utils;

/**
 * Utilitaire centralisé pour gérer tous les affichages et erreurs de l'application dans la console.
 * Permet d'uniformiser le format des messages et de séparer l'affichage de la logique métier.
 */
public class Logger {

    /**
     * Constructeur privé pour empêcher l'instanciation de cette classe utilitaire.
     * Toutes les méthodes étant statiques, il n'est pas nécessaire de créer un objet Logger.
     */
    private Logger() {}

    /**
     * Affiche un message d'information standard dans la console.
     * * @param message Le message d'information à afficher
     */
    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }

    /**
     * Affiche un message confirmant le succès d'une opération.
     * * @param message Le message de succès à afficher
     */
    public static void succes(String message) {
        System.out.println("[OK] " + message);
    }

    /**
     * Affiche un message d'avertissement pour signaler un comportement inattendu mais non bloquant.
     * * @param message Le message d'avertissement à afficher
     */
    public static void avertissement(String message) {
        System.out.println("[ATTENTION] " + message);
    }

    /**
     * Affiche un message d'erreur critique dans la console.
     * Utilise le flux d'erreur standard (System.err).
     * * @param message Le message d'erreur à afficher
     */
    public static void erreur(String message) {
        System.err.println("[ERREUR] " + message);
    }
}