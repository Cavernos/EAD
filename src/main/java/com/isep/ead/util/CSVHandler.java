package com.isep.ead.util;

import com.isep.ead.models.energy.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitaire d'import/export CSV pour les consommations énergétiques.
 *
 * Format CSV : id,date,quantity,pricePerUnit[,champsSpécifiques]
 * Séparateur : virgule — sans préfixe de type.
 *
 * Comme il n'y a pas de préfixe de type dans le CSV, l'import nécessite
 * de préciser la classe cible via {@link EnergyType}.
 */
public class CSVHandler {

    /** Énumère les types d'énergie supportés pour l'import. */
    public enum EnergyType {
        ELECTRICITY, WATER, GAS, CLIMATISATION
    }

    // ── Export ────────────────────────────────────────────────

    /**
     * Exporte une liste de consommations dans un fichier CSV.
     *
     * @param readings liste des Energy à exporter
     * @param filePath chemin du fichier de destination
     * @throws IOException en cas d'erreur d'écriture
     */
    public void exportReadings(List<Energy> readings, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("# Smart Energy Manager – Export CSV");
            writer.newLine();
            writer.write("# id,date,quantity,pricePerUnit[,specific fields]");
            writer.newLine();
            for (Energy energy : readings) {
                writer.write(energy.toCSV());
                writer.newLine();
            }
        }
    }

    // ── Import ────────────────────────────────────────────────

    /**
     * Importe des consommations depuis un fichier CSV en précisant le type d'énergie.
     *
     * @param filePath  chemin vers le fichier CSV
     * @param type      type d'énergie des lignes à lire
     * @return liste des Energy désérialisées
     * @throws IOException en cas d'erreur de lecture
     */
    public List<Energy> importReadings(String filePath, EnergyType type) throws IOException {
        List<Energy> readings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                try {
                    Energy energy = parseLine(line, type);
                    if (energy != null) readings.add(energy);
                } catch (Exception e) {
                    System.err.println("[CSVHandler] Ligne " + lineNumber + " ignorée : " + line + " (" + e.getMessage() + ")");
                }
            }
        }
        return readings;
    }

    // ── Parsing ───────────────────────────────────────────────

    private Energy parseLine(String line, EnergyType type) {
        return switch (type) {
            case ELECTRICITY   -> Electricity.fromCSV(line);
            case WATER         -> Water.fromCSV(line);
            case GAS           -> Gas.fromCSV(line);
            case CLIMATISATION -> Climatisation.fromCSV(line);
        };
    }
}

