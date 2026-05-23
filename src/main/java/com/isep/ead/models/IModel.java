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
    static IModel fromCSV(String[] fields) {
        return new Model() {
            @Override
            public String toCSV() {
                return "";
            }

            @Override
            public int getId() {
                return 0;
            }

            @Override
            public void setId(int id) {

            }
        };
    };
    int getId();
    void setId(int id);
}

