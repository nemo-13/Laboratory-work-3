package gui;

import model.Monster;
import javax.swing.*;
import java.awt.*;

public class MonsterDetailFrame extends JFrame {
    private final Monster monster;

    public MonsterDetailFrame(Monster monster) {
        this.monster = monster;
        setTitle("Детали: " + monster.getName());
        setSize(800, 600);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        addField(infoPanel, "ID", String.valueOf(monster.getId()), false);
        addField(infoPanel, "Название", monster.getName(), false);
        addField(infoPanel, "Описание", monster.getDescription(), false);
        addField(infoPanel, "Функция", monster.getFunction(), false);
        addField(infoPanel, "Уровень опасности", String.valueOf(monster.getDangerLevel()), true);
        addField(infoPanel, "Место обитания", monster.getHabitat(), false);
        addField(infoPanel, "Первое упоминание", monster.getFirstMentioned(), false);
        addField(infoPanel, "Иммунитеты", String.join(", ", monster.getImmunities()), false);
        addField(infoPanel, "Рост", monster.getHeight() + " м", false);
        addField(infoPanel, "Вес", monster.getWeight(), false);
        addField(infoPanel, "Активность", monster.getActivityTime(), false);

        JPanel recipePanel = createRecipePanel();
        
        mainPanel.add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        mainPanel.add(recipePanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JPanel createRecipePanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Рецепт"));

        Monster.Recipe recipe = monster.getRecipe();
        addField(panel, "Ингредиенты", String.join(", ", recipe.getIngredients()), false);
        addField(panel, "Время приготовления", recipe.getPreparationTime() + " мин", false);
        addField(panel, "Эффективность", recipe.getEffectiveness(), false);

        return panel;
    }

    private void addField(JPanel panel, String label, String value, boolean editable) {
        panel.add(new JLabel(label + ":"));
        JTextField textField = new JTextField(value != null ? value : "");
        textField.setEditable(editable);
        if (editable) {
            textField.addActionListener(e -> updateField(label, textField.getText()));
        }
        panel.add(textField);
    }

    private void updateField(String fieldName, String value) {
        
        try {
            switch (fieldName) {
                case "Уровень опасности" -> monster.setDangerLevel(Integer.parseInt(value));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка формата!");
        }
        // System.out.println(monster.getDangerLevel());
    }
}