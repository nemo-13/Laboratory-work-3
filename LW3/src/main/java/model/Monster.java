package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Monster {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("function")
    private String function;

    @JsonProperty("danger")
    private int dangerLevel;

    @JsonProperty("habitat")
    private String habitat;

    @JsonProperty("first_mention")
    private String firstMentioned;

    @JacksonXmlElementWrapper(localName = "immunity")
    @JacksonXmlProperty(localName = "immunity")
    @JsonProperty("immunity")
    private List<String> immunities;
    
    public List<String> getImmunities() {
        return immunities;
    }

    public void setImmunities(List<String> immunities) {
        this.immunities = immunities;
    }

    @JsonProperty("height")
    private double height;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("activity_time")
    private String activityTime;
    
    @JsonProperty("source")
    private String source;

    @JsonProperty("recept")
    private Recipe recipe;
 
    public static class Recipe {
        @JacksonXmlElementWrapper(localName = "ingredient")
        @JacksonXmlProperty(localName = "ingredient")
        @JsonProperty("ingredient")
        private List<String> ingredients;

        @JsonProperty("preparation_time")
        private int preparationTime;

        @JsonProperty("effectiveness")
        private String effectiveness;

        public List<String> getIngredients() { return ingredients; }
        public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
        public int getPreparationTime() { return preparationTime; }
        public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }
        public String getEffectiveness() { return effectiveness; }
        public void setEffectiveness(String effectiveness) { this.effectiveness = effectiveness; }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFunction() { return function; }
    public void setFunction(String function) { this.function = function; }
    public int getDangerLevel() { return dangerLevel; }
    public void setDangerLevel(int dangerLevel) { this.dangerLevel = dangerLevel; }
    public String getHabitat() { return habitat; }
    public void setHabitat(String habitat) { this.habitat = habitat; }
    public String getFirstMentioned() { return firstMentioned; }
    public void setFirstMentioned(String firstMentioned) { this.firstMentioned = firstMentioned; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }
    public String getActivityTime() { return activityTime; }
    public void setActivityTime(String activityTime) { this.activityTime = activityTime; }
    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    @Override
    public String toString() {
        return name;
    }
}