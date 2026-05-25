package com.isep.ead.models.alert;

import java.time.LocalDate;

public class Alert {
    private String message;
    private AlertLevel level;
    private LocalDate date;
    private boolean isRead;

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

    public void markAsRead() {
        this.isRead = true;
    }

    public String toCSV() {
        return message + "," + level + "," + date + "," + isRead;
    }

    public static Alert fromCSV(String csv) {
        String[] parts = csv.split(",", 4);
        Alert alert = new Alert(parts[0], AlertLevel.valueOf(parts[1]), LocalDate.parse(parts[2]));
        if (Boolean.parseBoolean(parts[3])) alert.markAsRead();
        return alert;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AlertLevel getLevel() {
        return level;
    }

    public void setLevel(AlertLevel level) {
        this.level = level;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isRead() {
        return isRead;
    }

    @Override
    public String toString() {
        return "[" + level + "] " + date + " - " + message + (isRead ? " (lu)" : "");
    }
}
