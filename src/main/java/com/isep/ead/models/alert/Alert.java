package com.isep.ead.models.alert;
import java.time.LocalDate;
public class Alert {
    private int id;
    private String message;
    private AlertLevel level;
    private LocalDate date;
    private boolean read;
    public Alert(String message, AlertLevel level) {
        this.message = message;
        this.level = level;
        this.date = LocalDate.now();
        this.read = false;
    }
    public Alert(String message, AlertLevel level, LocalDate date) {
        this.message = message;
        this.level = level;
        this.date = date;
        this.read = false;
    }
    public void markAsRead() { this.read = true; }
    public String toCSV() {
        return id + "," + message + "," + level + "," + date + "," + read;
    }
    public static Alert fromCSV(String csv) {
        String[] parts = csv.split(",", 5);
        Alert alert = new Alert(parts[1], AlertLevel.valueOf(parts[2]), LocalDate.parse(parts[3]));
        alert.setId(Integer.parseInt(parts[0]));
        alert.read = Boolean.parseBoolean(parts[4]);
        return alert;
    }
    public int getId()                     { return id; }
    public void setId(int id)              { this.id = id; }
    public String getMessage()             { return message; }
    public void setMessage(String message) { this.message = message; }
    public AlertLevel getLevel()           { return level; }
    public void setLevel(AlertLevel level) { this.level = level; }
    public LocalDate getDate()             { return date; }
    public void setDate(LocalDate date)    { this.date = date; }
    public boolean isRead()                { return read; }
    @Override
    public String toString() {
        return "[" + level + "] " + date + " - " + message + (read ? " (lu)" : "");
    }
}
