package com.isep.ead.utils;

import com.isep.ead.EADApplication;
import com.isep.ead.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SceneManager {

    private Stage mainStage;
    private final Map<String, LoadedView> viewCache = new HashMap<>();

    public SceneManager(Stage stage){
        this.setMainStage(stage);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public LoadedView loadPage(String view) {
        if (this.viewCache.containsKey(view)) {
            return this.viewCache.get(view);
        }

        LoadedView root = this.loadTemplate(view);

        this.viewCache.put(view, root);
        return root;
    }


    public LoadedView loadTemplate(String view) {
        try {
            FXMLLoader loader = this.loadPath(view);
            Parent root = Objects.requireNonNull(loader).load();
            Controller controller = loader.getController();
            if (controller != null) {
                controller.setSceneManager(this);
            }

            return new LoadedView(root, controller);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load template: " + view, e);
        }
    }



    public Controller switchTo(LoadedView view) {
        Scene scene = new Scene(view.getRoot());
        this.mainStage.setScene(scene);
        this.mainStage.sizeToScene();
        return view.getController();

    }


    private FXMLLoader loadPath(String view) {
        URL viewPath = EADApplication.class.getResource(view + ".fxml");
        if (viewPath == null) {
            System.out.println("The view " + view + " does not exists !");
            return null;
        }
        return new FXMLLoader(viewPath);
    }
}
