package com.isep.ead.util;

import com.isep.ead.energy.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitaire d'import/export CSV pour les consommations énergétiques.
 * Format CSV attendu : TYPE;id;date;quantity;pricePerUnit;[champs spécifiques]
 */
public class CSVHandler {

    private static final String SEPARATOR = ";";

    /**
     * Importe une liste de consommations énergétiques depuis un fichier CSV.
     *
     * @param filePath chemin vers le fichier CSV
     * @return liste des objets Energy désérialisés
     * @throws IOException en cas d'erreur de lecture
     */
    public List<Energy> importReadings(String filePath) throws IOException {
        List<Energy> readings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Ignorer les lignes vides et les commentaires
                }

                try {
                    Energy energy = parseLine(line);
                    if (energy != null) {
                        readings.add(energy);
                    }
                } catch (Exception e) {
                    System.err.println("[CSVHandler] Ligne " + lineNumber + " ignorée (format invalide) : " + line);
                }
            }
        }

        return readings;
    }

    /**
     * Exporte une liste de consommations énergétiques dans un fichier CSV.
     *
     * @param readings liste des Energy à exporter
     * @param filePath chemin du fichier de destination
     * @throws IOException en cas d'erreur d'écriture
     */
    public void exportReadings(List<Energy> readings, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("# Smart Energy Manager – Export CSV");
            writer.newLine();
            writer.write("# TYPE;id;date;quantity;pricePerUnit;[specific fields]");
            writer.newLine();

            for (Energy energy : readings) {
                writer.write(energy.toCSV());
                writer.newLine();
            }
        }
    }

    // ── Parsing ───────────────────────────────────────────────

    private Energy parseLine(String line) {
        String type = line.split(SEPARATOR)[0].toUpperCase();

        return switch (type) {
            case "ELECTRICITY"   -> Electricity.fromCSV(line);
            case "WATER"         -> Water.fromCSV(line);
            case "GAS"           -> Gas.fromCSV(line);
            case "CLIMATISATION" -> Climatisation.fromCSV(line);
            default -> {
                System.err.println("[CSVHandler] Type inconnu : " + type);
                yield null;
            }
        };
    }
}

