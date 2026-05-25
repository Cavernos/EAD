package com.isep.ead.widgets;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;


public class FormScene  {
    private final Map<String, String> labels;
    private final Map<String, TextField> fields = new HashMap<>();
    private final VBox root = new VBox();
    private final Button submit = new Button("Submit");

    public FormScene(Map<String, String> labels){
        this.labels = labels;
    }

    public Scene create() {
        for (String fieldId : this.labels.keySet()) {
            Label label = new Label(this.labels.get(fieldId));
            TextField textField = new TextField();
            this.fields.put(fieldId, textField);
            this.root.getChildren().add(new HBox(label, textField));
        }
        this.root.getChildren().add(this.submit);
        return new Scene(this.root, this.root.getWidth(), this.root.getHeight());
    }

    public Map<String, String> getValues() {
        Map<String, String> values = new HashMap<>();
        for(String key : this.fields.keySet()) {
            values.put(key, this.fields.get(key).getText());
        }
        return values;
    }

    public Button getSubmitButton() {
        return this.submit;
    }
}
