package repository;

import model.Monster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterRepository {
    // Коллекция: источник (имя файла) -> список чудовищ
    private final Map<String, List<Monster>> storage = new HashMap<>();

    public void addMonstersFromSource(String sourceFile, List<Monster> monsters) {
        if (sourceFile == null || monsters == null) {
            throw new IllegalArgumentException("Некорректные аргументы");
        }
        storage.put(sourceFile, new ArrayList<>(monsters));
    }

    public void updateSource(String sourceFile, List<Monster> monsters) {
        if (!storage.containsKey(sourceFile)) {
            throw new IllegalArgumentException("Источник не найден: " + sourceFile);
        }
        storage.put(sourceFile, new ArrayList<>(monsters));
    }

    public List<Monster> getAllMonsters() {
        List<Monster> allMonsters = new ArrayList<>();
        for (List<Monster> monsters : storage.values()) {
            allMonsters.addAll(monsters);
        }
        return allMonsters;
    }
    
    public Map<String, List<Monster>> getSources() {
        return new HashMap<>(storage);
    }

    public List<Monster> getMonstersBySource(String sourceFile) {
        if (!storage.containsKey(sourceFile)) {
            throw new IllegalArgumentException("Источник не найден: " + sourceFile);
        }
        return new ArrayList<>(storage.get(sourceFile));
    }

    public void removeSource(String sourceFile) {
        storage.remove(sourceFile);
    }

    public void clear() {
        storage.clear();
    }
    
    public boolean isEmpty() {
        return storage.isEmpty();
    }
}