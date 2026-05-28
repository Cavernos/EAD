package com.isep.ead.models;

/**
 * Contrat de sérialisation CSV pour tous les modèles métier.
 */
public interface IModel<T extends IModel<T>> {

    /**
     * Sérialise l'objet en ligne CSV (séparateur virgule, sans préfixe de type).
     *
     * @return représentation CSV de l'objet
     */
    String toCSV();
    T fromCSV(String[] fields);
    int getId();
    void setId(int id);
}

