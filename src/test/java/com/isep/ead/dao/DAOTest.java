package com.isep.ead.dao;


import com.isep.ead.models.building.Building;
import org.junit.jupiter.api.*;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DAOTest {

    private static DAO<Building> dao;
    private final Building building = new Building("Building 1", "10 Boulevard de la manie", 2000.0);

    static Path csvFile;
    @BeforeAll
    static void setup() throws IOException {
        csvFile = Files.createTempFile("test", ".csv");
        dao = new DAO<>(Building.class, csvFile.toString());
    }

    @Test
    @Order(1)
    void create() {
        assertEquals(1, DAO.idSequence);
        dao.create(this.building);
        assertEquals(2, DAO.idSequence);
        this.getById(this.building);
    }

    @Test
    @Order(2)
    void update() {
        Building building = new Building("Building 2", "10 Boulevard de la manie", 2000.0);
        building.setId(1);
        System.out.println(building);
        dao.update(building);
        this.getById(building);
    }


    @Test
    @Order(3)
    void remove() {
        Building building = new Building("Building 1", "10 Boulevard de la manie", 2000.0);
        building.setId(1);
        dao.remove(building);
        assertNull(dao.getById(1));
    }

    private void getById(Building building) {
        Building createdBuilding = dao.getById(1);
        assertEquals(building.getId(), createdBuilding.getId());
        assertEquals(building.getAddress(), createdBuilding.getAddress());
        assertEquals(building.getName(), createdBuilding.getName());
        assertEquals(building.getSurface(), createdBuilding.getSurface());
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(csvFile);
    }

}