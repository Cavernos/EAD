package com.isep.ead.models.alert;

import java.time.LocalDate;


public class Alert {

    private int id;
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
        return id + "," + message + "," + level + "," + date + "," + isRead;
    }

    public Alert fromCSV(String[] fields) {
        Alert alert = new Alert(fields[1], AlertLevel.valueOf(fields[2]), LocalDate.parse(fields[3]));
        alert.setId(Integer.parseInt(fields[0]));
        alert.isRead = Boolean.parseBoolean(fields[4]);
        return alert;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "[" + level + "] " + date + " – " + message + (isRead ? " (lu)" : " (non lu)");
    }
}

