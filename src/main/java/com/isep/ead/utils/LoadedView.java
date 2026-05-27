package com.isep.ead.utils;

import com.isep.ead.controllers.Controller;
import javafx.scene.Parent;

public class LoadedView{

    private final Parent root;

    public Controller getController() {
        return controller;
    }

    public Parent getRoot() {
        return root;
    }

    private final Controller controller;

    public LoadedView(Parent root, Controller controller) {
        this.controller = controller;
        this.root = root;
    }


}
