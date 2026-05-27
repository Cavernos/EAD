package com.isep.ead.templates;

import com.isep.ead.controllers.templates.OrganizationItemController;
import com.isep.ead.models.organization.Organization;
import com.isep.ead.utils.LoadedView;
import com.isep.ead.utils.SceneManager;
import javafx.scene.Parent;

public class OrganizationItem {

    private final LoadedView view;
    private final OrganizationItemController controller;

    public OrganizationItem(Organization organization, SceneManager sceneManager) {
        String templateView = "organisation-template-view";
        this.view = sceneManager.loadTemplate(templateView);
        this.controller = (OrganizationItemController) this.view.getController();
        this.controller.setData(organization);

    }

    public Parent getView() {
        return view.getRoot();
    }
}
