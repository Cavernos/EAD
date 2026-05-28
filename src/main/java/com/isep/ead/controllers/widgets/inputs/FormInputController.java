package com.isep.ead.controllers.widgets.inputs;

import com.isep.ead.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalTime;

public class FormInputController extends Controller {

    /** Type de validation attendu pour ce champ */
    public enum FieldType {
        TEXT,         // texte libre non vide
        DOUBLE,       // nombre décimal (ex: 12.5)
        INTEGER,      // entier
        DATE,         // format AAAA-MM-JJ
        TIME          // format HH:MM
    }

    private static final String STYLE_NORMAL  =
        "-fx-background-color: #f8f9fa; -fx-border-color: #dcdde1; -fx-border-radius: 5; -fx-background-radius: 5;";
    private static final String STYLE_ERROR   =
        "-fx-background-color: #fff5f5; -fx-border-color: #e74c3c; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-radius: 5;";
    private static final String STYLE_SUCCESS =
        "-fx-background-color: #f0fff4; -fx-border-color: #27ae60; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-radius: 5;";

    @FXML
    private Label inputName;

    @FXML
    private TextField inputField;

    /** Peut être null si le FXML ne contient pas encore ce label */
    @FXML
    private Label errorLabel;

    private FieldType fieldType = FieldType.TEXT;
    private boolean hinted = false; // validation active uniquement après 1ère frappe

    /**
     * Définit le type de validation et branche le listener temps-réel.
     */
    public void setFieldType(FieldType type) {
        this.fieldType = type;
        this.inputField.textProperty().addListener((_obs, _old, _new) -> {
            hinted = true;
            refreshValidation();
        });
        // Afficher placeholder de format dans le prompt
        String prompt = switch (type) {
            case DOUBLE  -> "ex : 120.5";
            case INTEGER -> "ex : 42";
            case DATE    -> "AAAA-MM-JJ";
            case TIME    -> "HH:MM";
            default      -> "";
        };
        if (!prompt.isEmpty()) this.inputField.setPromptText(prompt);
    }

    /** Retourne true si la valeur actuelle respecte le type attendu. */
    public boolean isValid() {
        String val = inputField.getText();
        if (val == null) return false;
        return switch (fieldType) {
            case TEXT    -> !val.trim().isEmpty();
            case DOUBLE  -> isValidDouble(val);
            case INTEGER -> isValidInteger(val);
            case DATE    -> isValidDate(val);
            case TIME    -> isValidTime(val);
        };
    }

    /**
     * Force la validation visuelle (à appeler avant submit).
     * @return true si valide
     */
    public boolean validateAndShow() {
        hinted = true;
        refreshValidation();
        return isValid();
    }

    // ── Méthodes privées ───────────────────────────────────────────────

    private void refreshValidation() {
        if (!hinted) return;
        String val = inputField.getText();
        boolean empty = val == null || val.trim().isEmpty();
        if (empty) {
            inputField.setStyle(fieldType == FieldType.TEXT ? STYLE_ERROR : STYLE_NORMAL);
            showError(fieldType == FieldType.TEXT ? "Ce champ est obligatoire." : "");
        } else if (isValid()) {
            inputField.setStyle(STYLE_SUCCESS);
            hideError();
        } else {
            inputField.setStyle(STYLE_ERROR);
            showError(getErrorMessage());
        }
    }

    private String getErrorMessage() {
        return switch (fieldType) {
            case TEXT    -> "Ce champ est obligatoire.";
            case DOUBLE  -> "Nombre décimal attendu (ex : 120.5).";
            case INTEGER -> "Nombre entier attendu (ex : 42).";
            case DATE    -> "Format AAAA-MM-JJ attendu (ex : 2025-01-15).";
            case TIME    -> "Format HH:MM attendu (ex : 08:30).";
        };
    }

    private void showError(String msg) {
        if (errorLabel == null) return;
        if (msg == null || msg.isEmpty()) { hideError(); return; }
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void hideError() {
        if (errorLabel == null) return;
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    // ── Helpers de parsing ─────────────────────────────────────────────

    private static boolean isValidDouble(String v) {
        try { Double.parseDouble(v.replace(",", ".")); return true; }
        catch (NumberFormatException e) { return false; }
    }

    private static boolean isValidInteger(String v) {
        try { Integer.parseInt(v); return true; }
        catch (NumberFormatException e) { return false; }
    }

    private static boolean isValidDate(String v) {
        try { LocalDate.parse(v); return true; }
        catch (Exception e) { return false; }
    }

    private static boolean isValidTime(String v) {
        try { LocalTime.parse(v); return true; }
        catch (Exception e) { return false; }
    }

    // ── Getters / Setters ─────────────────────────────────────────────

    public String getValue() {
        return this.inputField.getText();
    }

    public void setText(String name) {
        this.inputName.setText(name);
    }

    public void setDefaultValue(String value) {
        this.inputField.setText(value);
    }
}
