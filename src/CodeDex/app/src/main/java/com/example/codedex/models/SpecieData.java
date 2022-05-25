package com.example.codedex.models;

import java.util.List;

public class SpecieData {

    String base_happiness;
    List<FlavorText> flavor_text_entries;

    private Evolution_chain evolution_chain;

    public String getBaseHappiness() {
        return base_happiness;
    }

    public List<FlavorText> getFlavor_text() {
        return flavor_text_entries;
    }

    public Evolution_chain getEvolution_chain() {
        return evolution_chain;
    }
}
