package com.isep.ead;


import com.isep.ead.dao.DAO;
import com.isep.ead.models.building.Building;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EADApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(EADApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        DAO<Building> buildingDAO = new DAO<>(Building.class);
        for (int i = 0; i<7; i++) {
            buildingDAO.create(
                    new Building("Toto", "a", 33.0)
            );
        }
        buildingDAO.remove(buildingDAO.getById(4));
        Building test = buildingDAO.getById(5);
        test.setName("tata");
        buildingDAO.update(test);


    }
}
