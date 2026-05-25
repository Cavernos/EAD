package com.isep.ead.controllers.crud;

import javafx.fxml.FXML;

public interface ICrudController {
    void index();
    @FXML
    void add();
    @FXML
    void modify();
    @FXML
    void delete();
}
