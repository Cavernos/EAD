package com.isep.ead.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {
    private final String filename;

    public CSVHandler(String filename) {
        this.filename = filename;
    }

    public void write(String row) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename, true))){
            writer.write(row);
            writer.newLine();
        } catch (IOException e) {
            System.err.printf("Erreur lors de l'écriture du fichier : %s (e : %s)", this.filename, e.getMessage());
            System.err.println();
        }
    }
    public void write(String[] rows) {
        for (String row: rows) {
            this.write(row);
        }
    }

    public void remove(int id) {
        String[] rows = this.read();
        ArrayList<String> rowsList = new ArrayList<>(List.of(rows));
        rowsList.removeIf(row -> {
            String[] parts = row.split(",");
            try { return parts.length > 0 && id == Integer.parseInt(parts[0].trim()); }
            catch (NumberFormatException e) { return false; }
        });
        flushFile(rowsList.toArray(new String[0]));
    }

    public String update(int id, String newRow) {

        String[] rows = this.read();
        for (int i = 0; i< rows.length; i++) {
            try {
                if(Integer.parseInt(rows[i].split(",")[0].trim()) == id) {
                    rows[i] = newRow;
                }
            } catch (NumberFormatException ignored) {}
        }
        flushFile(rows);
        return newRow;
    }

    private void flushFile(String[] rows) {
        try (FileWriter fw = new FileWriter(this.filename, false)) {
            fw.flush();
        } catch (IOException e) {
            System.err.printf("Erreur lors de la réinitialisation du fichier : %s (e : %s)", this.filename, e.getMessage());
            System.err.println();
        }
        this.write(rows);
    }

    public String getLastRow() {
        String[] rows = this.read();
        if (rows.length > 0) {
            return rows[rows.length - 1];
        }
        return "";
    }

    public String[] read() {
        ArrayList<String> rows = new ArrayList<>();
        if(!(new File(this.filename)).exists()) return new String[0];
        try (BufferedReader reader = new BufferedReader(new
                FileReader(this.filename))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) rows.add(line);
            }
        } catch (IOException e) {
            System.err.printf("Erreur lors de la lecture du fichier : %s (e : %s)", this.filename, e.getMessage());
            System.err.println();
        }
        return rows.toArray(new String[0]);

    }
}
