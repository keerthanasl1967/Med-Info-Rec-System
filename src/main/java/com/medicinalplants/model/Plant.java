package com.medicinalplants.model;

public class Plant {
    private int id;
    private String commonName;
    private String scientificName;
    private int categoryId;
    private String categoryName;
    private String description;
    private String uses;
    private String plantParts;
    private String preparation;
    private String precautions;
    private String imagePath;

    public Plant(int id, String commonName, String scientificName, int categoryId, String categoryName, 
                 String description, String uses, String plantParts, String preparation, String precautions, String imagePath) {
        this.id = id;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.uses = uses;
        this.plantParts = plantParts;
        this.preparation = preparation;
        this.precautions = precautions;
        this.imagePath = imagePath;
    }

    public int getId() { return id; }
    public String getCommonName() { return commonName; }
    public String getScientificName() { return scientificName; }
    public int getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public String getDescription() { return description; }
    public String getUses() { return uses; }
    public String getPlantParts() { return plantParts; }
    public String getPreparation() { return preparation; }
    public String getPrecautions() { return precautions; }
    public String getImagePath() { return imagePath; }

    @Override
    public String toString() {
        return commonName + " (" + scientificName + ")";
    }
}
