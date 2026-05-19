package fr.eseo.e3e.devlogiciel.fraude;

import java.time.LocalDate;

public class FraudePapier extends Fraude {

    /**
     * Dimensions du papier
     */
    private String dimensions;

    /**
     * Pliage du papier
     */
    private boolean estPlie;

    /**
     * Crée une nouvelle fraude avec du papier avec des informations par défaut.
     */
    public FraudePapier()  {
        super();
        this.dimensions = "";
        this.estPlie = false;
    }

    /**
     * Crée une nouvelle fraude avec du papier avec toutes ces informations.
     *
     * @param dateReleve               La date de la fraude
     * @param description            La description de la fraude
     * @param contenu               Le contenu de la fraude
     * @param dimensions           La dimension du papier
     * @param estPlie             la feuille est plié.
     */
    public FraudePapier(LocalDate dateReleve, String description, String contenu, String dimensions, boolean estPlie) {
        super(dateReleve, description, contenu);
        this.setDimensions(dimensions);
        this.setEstPlie(estPlie);
    }

    /**
     * Récupère la dimension du papier
     *
     * @return La dimension du papier
     */
    public String getDimensions() {
        return dimensions;
    }

    /**
     * Définit la dimension du papier.
     *
     * @param dimensions La nouvelle dimension à définir
     */
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Récupère est plié du papier
     *
     * @return est plié du papier
     */
    public boolean getEstPlie() {
        return estPlie;
    }

    /**
     * Définit estPlie du papier.
     *
     * @param estPlie estPlie à définir
     */
    public void setEstPlie(boolean estPlie) {
        this.estPlie = estPlie;
    }
}
