package fr.eseo.e3e.devlogiciel.utils;

/**
 * Exception personnalisée pour gérer les erreurs métiers liées aux fraudes.
 * Permet de séparer les erreurs de logique de l'affichage dans la console.
 */
public class FraudeException extends Exception {

    /**
     * Construit une nouvelle exception avec le message spécifié.
     * @param message Le détail de l'erreur rencontrée
     */
    public FraudeException(String message) {
        super(message);
    }
}
