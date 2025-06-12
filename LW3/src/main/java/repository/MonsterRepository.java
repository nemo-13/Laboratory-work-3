package repository;

import model.Monster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Хранилище для управления данными о чудовищах.
 * Поддерживает разделение данных по источникам (файлам).
 */
public class MonsterRepository {
    // Коллекция: источник (имя файла) -> список чудовищ
    private final Map<String, List<Monster>> storage = new HashMap<>();

    /**
     * Добавляет список чудовищ из указанного файла.
     * @param sourceFile Имя файла-источника
     * @param monsters Список чудовищ для добавления
     */
    public void addMonstersFromSource(String sourceFile, List<Monster> monsters) {
        if (sourceFile == null || monsters == null) {
            throw new IllegalArgumentException("Некорректные аргументы");
        }
        storage.put(sourceFile, new ArrayList<>(monsters));
    }

    /**
     * Обновляет данные для существующего источника.
     * @param sourceFile Имя файла-источника
     * @param monsters Новый список чудовищ
     */
    public void updateSource(String sourceFile, List<Monster> monsters) {
        if (!storage.containsKey(sourceFile)) {
            throw new IllegalArgumentException("Источник не найден: " + sourceFile);
        }
        storage.put(sourceFile, new ArrayList<>(monsters));
    }

    /**
     * Возвращает все чудовища из всех источников.
     */
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

    /**
     * Возвращает чудовища из конкретного источника.
     * @param sourceFile Имя файла-источника
     */
    public List<Monster> getMonstersBySource(String sourceFile) {
        if (!storage.containsKey(sourceFile)) {
            throw new IllegalArgumentException("Источник не найден: " + sourceFile);
        }
        return new ArrayList<>(storage.get(sourceFile));
    }

    /**
     * Удаляет все данные из указанного источника.
     */
    public void removeSource(String sourceFile) {
        storage.remove(sourceFile);
    }

    /**
     * Очищает хранилище.
     */
    public void clear() {
        storage.clear();
    }
}