package com.isep.ead.models;

/**
 * Contrat de sérialisation CSV pour tous les modèles métier.
 */
public interface IModel {

    /**
     * Sérialise l'objet en ligne CSV (séparateur virgule, sans préfixe de type).
     *
     * @return représentation CSV de l'objet
     */
    String toCSV();
}

