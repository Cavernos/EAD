package com.isep.ead.model;

/**
 * Classe abstraite de base pour tous les modèles métier.
 * Fournit le contrat de sérialisation CSV.
 */
public abstract class Model {

    /**
     * Sérialise l'objet en ligne CSV.
     *
     * @return représentation CSV de l'objet
     */
    public abstract String toCSV();

    /**
     * Désérialise une ligne CSV en objet Model.
     * Chaque sous-classe doit implémenter sa propre version statique.
     *
     * @param csv la ligne CSV à parser
     * @return l'instance Model correspondante
     */
    public static Model fromCSV(String csv) {
        throw new UnsupportedOperationException("fromCSV doit être surchargé dans la sous-classe.");
    }
}

