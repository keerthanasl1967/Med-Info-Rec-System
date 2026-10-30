package com.medicinalplants.model;

import java.util.List;

public class Recommendation {
    private Plant plant;
    private double matchScore;
    private List<String> matchedSymptoms;

    public Recommendation(Plant plant, double matchScore, List<String> matchedSymptoms) {
        this.plant = plant;
        this.matchScore = matchScore;
        this.matchedSymptoms = matchedSymptoms;
    }

    public Plant getPlant() { return plant; }
    public double getMatchScore() { return matchScore; }
    public List<String> getMatchedSymptoms() { return matchedSymptoms; }
}
