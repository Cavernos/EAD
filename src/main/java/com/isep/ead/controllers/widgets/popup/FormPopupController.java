package com.isep.ead.controllers.widgets.popup;

import com.isep.ead.controllers.widgets.inputs.FormComboController;
import com.isep.ead.controllers.widgets.inputs.FormInputController;
import com.isep.ead.utils.LoadedView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FormPopupController extends PopupController {
    private final Map<String, LoadedView> fields = new HashMap<>();
    private Runnable onSubmitAction;
    @FXML public Label nameLabel;
    @FXML private VBox contentBox;
    /** Label de résumé d'erreur (peut être null si absent du FXML) */
    @FXML private Label validationSummary;

    // ── Construction des champs ────────────────────────────────────────

    private LoadedView addField(String view, String name, String labelText, String defaultValue) {
        LoadedView field = this.sceneManager.loadTemplate(view);
        FormInputController inputController = (FormInputController) field.getController();
        inputController.setText(labelText);
        inputController.setDefaultValue(defaultValue);
        this.contentBox.getChildren().add(field.getRoot());
        this.fields.put(name, field);
        return field;
    }

    /** Champ texte libre (obligatoire). */
    public void addField(String name, String labelText, String defaultValue) {
        LoadedView lv = this.addField("components/input/FormInput", name, labelText, defaultValue);
        ((FormInputController) lv.getController()).setFieldType(FormInputController.FieldType.TEXT);
    }

    public void addField(String name, String labelText) {
        this.addField(name, labelText, "");
    }

    /**
     * Champ typé : TEXT, DOUBLE, INTEGER ou DATE.
     * La validation temps-réel est activée automatiquement.
     */
    public void addField(String name, String labelText, String defaultValue, FormInputController.FieldType type) {
        LoadedView lv = this.addField("components/input/FormInput", name, labelText, defaultValue);
        ((FormInputController) lv.getController()).setFieldType(type);
    }

    public void addField(String name, String labelText, FormInputController.FieldType type) {
        this.addField(name, labelText, "", type);
    }

    public void addComboField(String name, String labelText, ArrayList<String> items, String defaultValue) {
        LoadedView lv = this.addField("components/input/FormCombo", name, labelText, defaultValue);
        ((FormComboController) lv.getController()).setItems(items);
    }

    public void addComboField(String name, String labelText, ArrayList<String> items) {
        this.addComboField(name, labelText, items, "");
    }

    public void removeField(String name) {
        LoadedView lv = this.fields.remove(name);
        if (lv != null) {
            this.contentBox.getChildren().remove(lv.getRoot());
        }
    }

    public void onComboChange(String fieldName, Consumer<String> listener) {
        LoadedView lv = this.fields.get(fieldName);
        if (lv != null) {
            ((FormComboController) lv.getController()).setOnValueChange(listener);
        }
    }

    // ── Getters ────────────────────────────────────────────────────────

    public void setPopupName(String name) {
        this.nameLabel.setText(name);
    }

    public String getValues(String name) {
        if (this.fields.containsKey(name)) {
            return ((FormInputController) this.fields.get(name).getController()).getValue();
        }
        return "";
    }

    public void setOnSubmitAction(Runnable action) {
        this.onSubmitAction = action;
    }

    // ── Validation & submit ────────────────────────────────────────────

    /**
     * Valide tous les champs. Retourne true si tous sont valides.
     * Les champs invalides s'affichent en rouge.
     */
    private boolean validateAll() {
        boolean allValid = true;
        for (LoadedView lv : fields.values()) {
            FormInputController ctrl = (FormInputController) lv.getController();
            boolean ok = ctrl.validateAndShow();
            if (!ok) allValid = false;
        }
        return allValid;
    }

    @FXML
    private void onFormSave() {
        if (!validateAll()) {
            showValidationSummary("⚠ Veuillez corriger les champs en rouge avant de valider.");
            return;
        }
        hideValidationSummary();
        if (this.onSubmitAction != null) {
            this.onSubmitAction.run();
        }
    }

    private void showValidationSummary(String msg) {
        if (validationSummary == null) return;
        validationSummary.setText(msg);
        validationSummary.setVisible(true);
        validationSummary.setManaged(true);
    }

    private void hideValidationSummary() {
        if (validationSummary == null) return;
        validationSummary.setVisible(false);
        validationSummary.setManaged(false);
    }
}
