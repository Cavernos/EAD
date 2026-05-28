package com.isep.ead.utils;

import com.isep.ead.EADApplication;
import com.isep.ead.controllers.Controller;
import com.isep.ead.widgets.popup.Popup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
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

    /**
     * Retourne la vue depuis le cache UNIQUEMENT si elle a déjà été chargée.
     * Ne crée PAS de nouvelle instance. Utile pour les rafraîchissements conditionnels.
     */
    public LoadedView getCachedPage(String view) {
        return this.viewCache.get(view);
    }

    /**
     * Supprime une vue du cache pour forcer son rechargement au prochain appel de loadPage().
     */
    public void invalidatePage(String view) {
        this.viewCache.remove(view);
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

    public Popup loadPopup(String view) {
        LoadedView loadedView = this.loadTemplate(view);
        Stage stage = new Stage();
        stage.setScene(new Scene(loadedView.getRoot()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        return new Popup(stage, loadedView.getRoot(),loadedView.getController());
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
