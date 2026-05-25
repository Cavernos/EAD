package com.isep.ead.controllers;

import com.isep.ead.utils.SceneManager;

public abstract class Controller {
    protected SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
