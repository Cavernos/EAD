package com.isep.ead.controllers.templates;

import com.isep.ead.controllers.Controller;
import com.isep.ead.controllers.crud.CrudController;

public class TemplateController extends Controller {

    private CrudController crudController;
    protected int itemId;

    public void setData(String view) {
        this.crudController = (CrudController) sceneManager.loadPage(view).getController();
    }


    protected void modify() {
        if(this.crudController != null) this.crudController.modify(this.itemId);
    }

    protected void delete() {
        if(this.crudController != null) this.crudController.delete(this.itemId);
    }
}
