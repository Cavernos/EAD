package com.isep.ead.util;
import com.isep.ead.models.energy.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class CSVHandler {
    public enum EnergyType { ELECTRICITY, WATER, GAS, CLIMATISATION }
    public void exportReadings(List<Energy> readings, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Energy energy : readings) {
                writer.write(energy.toCSV());
                writer.newLine();
            }
        }
    }
    public List<Energy> importReadings(String filePath, EnergyType type) throws IOException {
        List<Energy> readings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    readings.add(parseLine(line, type));
                } catch (Exception e) {
                    System.err.println("[CSVHandler] Ligne ignoree : " + line);
                }
            }
        }
        return readings;
    }
    private Energy parseLine(String line, EnergyType type) {
        return switch (type) {
            case ELECTRICITY   -> Electricity.fromCSV(line);
            case WATER         -> Water.fromCSV(line);
            case GAS           -> Gas.fromCSV(line);
            case CLIMATISATION -> Climatisation.fromCSV(line);
        };
    }
}
