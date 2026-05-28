package com.isep.ead.models.building;


public class House extends Building {

    private int numberOfRooms;
    private boolean hasGarden;


    public House(String name, String address, double surface, int numberOfRooms, boolean hasGarden) {
        super(name, address, surface);
        this.numberOfRooms = numberOfRooms;
        this.hasGarden = hasGarden;
    }

    public House() {

    }


    @Override
    public String toCSV() {
        return id + ",House," + name + "," + address + "," + surface + "," + organizationId + "," + numberOfRooms + "," + hasGarden;
    }

    @Override
    public House fromCSV(String[] fields) {
        // format: id,House,name,address,surface,organizationId[,numberOfRooms,hasGarden]
        House house = new House();
        house.setId(Integer.parseInt(fields[0]));
        house.setName(fields[2]);
        house.setAddress(fields[3]);
        try { house.setSurface(Double.parseDouble(fields[4])); } catch (Exception ignored) {}
        if (fields.length > 5) try { house.setOrganizationId(Integer.parseInt(fields[5])); } catch (Exception ignored) {}
        if (fields.length > 6) try { house.setNumberOfRooms(Integer.parseInt(fields[6])); } catch (Exception ignored) {}
        if (fields.length > 7) try { house.setHasGarden(Boolean.parseBoolean(fields[7])); } catch (Exception ignored) {}
        return house;
    }



    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int n) {
        this.numberOfRooms = n;
    }

    public boolean hasGarden() {
        return hasGarden;
    }

    public void setHasGarden(boolean garden) {
        this.hasGarden = garden;
    }
}

