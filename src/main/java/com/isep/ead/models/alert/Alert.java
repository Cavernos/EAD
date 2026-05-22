package com.isep.ead.models.alert;

import java.time.LocalDate;

/**
 * Représente une alerte de consommation associée à un bâtiment.
 */
public class Alert {

    private int id;
    private String message;
    private AlertLevel level;
    private LocalDate date;
    private boolean isRead;

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    public Alert(String message, AlertLevel level) {
        this.message = message;
        this.level = level;
        this.date = LocalDate.now();
        this.isRead = false;
    }

    public Alert(String message, AlertLevel level, LocalDate date) {
        this.message = message;
        this.level = level;
        this.date = date;
        this.isRead = false;
    }

    // ── Actions ───────────────────────────────────────────────

    /** Marque l'alerte comme lue. */
    public void markAsRead() {
        this.isRead = true;
    }

    // ── CSV ───────────────────────────────────────────────────

    /** Format : id,message,level,date,isRead */
    public String toCSV() {
        return id + "," + message + "," + level + "," + date + "," + isRead;
    }

    public static Alert fromCSV(String csv) {
        String[] p = csv.split(",", 5);
        Alert a = new Alert(p[1], AlertLevel.valueOf(p[2]), LocalDate.parse(p[3]));
        a.setId(Integer.parseInt(p[0]));
        a.isRead = Boolean.parseBoolean(p[4]);
        return a;
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public AlertLevel getLevel() { return level; }
    public void setLevel(AlertLevel level) { this.level = level; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public boolean isRead() { return isRead; }

    @Override
    public String toString() {
        return "[" + level + "] " + date + " – " + message + (isRead ? " (lu)" : " (non lu)");
    }
}

