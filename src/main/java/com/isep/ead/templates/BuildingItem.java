package com.isep.ead.templates;

import com.isep.ead.controllers.templates.BuildingItemController;
import com.isep.ead.models.building.Building;
import com.isep.ead.utils.LoadedView;
import com.isep.ead.utils.SceneManager;
import javafx.scene.Parent;

public class BuildingItem {

    private final LoadedView view;

    public BuildingItem(Building building, SceneManager sceneManager) {
        this.view = sceneManager.loadTemplate("batiment-template-view");
        ((BuildingItemController) this.view.getController()).setData(building);
    }

    public Parent getView() {
        return view.getRoot();
    }
}

