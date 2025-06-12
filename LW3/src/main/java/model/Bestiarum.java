package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Bestiarum {
    @JsonProperty("bestiarum")
    private MonsterListWrapper wrapper;

    public List<Monster> getMonsters() {
        return wrapper != null ? wrapper.monsters : List.of();
    }

    private static class MonsterListWrapper {
        @JsonProperty("monster")
        private List<Monster> monsters;
    }
    
    public void setMonsters(List<Monster> monsters) {
        if (wrapper == null) wrapper = new MonsterListWrapper();
        wrapper.monsters = monsters;
    }
}