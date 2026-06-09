package fr.eseo.e3e.devlogiciel.systeme;

/**
 * Interface définissant les opérations d'analyse statistique sur les fraudes.
 * Utilisée pour satisfaire les exigences pédagogiques de mise en pratique des interfaces.
 */
public interface IAnalyseStatistique {
    /**
     * @return Le nombre total de formulaires enregistrés.
     */
    int getNombreTotalFormulaires();

    /**
     * @return Le nombre d'étudiants distincts impliqués dans au moins une fraude.
     */
    int getNombreEtudiantsDistincts();

    /**
     * @return Le nombre total de fraudes enregistrées.
     */
    int getNombreTotalFraudes();

    /**
     * @return La moyenne du nombre de fraudes par formulaire.
     */
    double getMoyenneFraudesParFormulaire();

    /**
     * @return L'écart-type du nombre de fraudes par formulaire.
     */
    double getEcartTypeFraudesParFormulaire();
}
