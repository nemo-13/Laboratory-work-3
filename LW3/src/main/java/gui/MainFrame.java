package gui;

import javax.swing.filechooser.FileNameExtensionFilter;
import model.Monster;
import parser.*;
import repository.MonsterRepository;
import util.FileUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MainFrame extends JFrame {
    private final MonsterRepository repository = new MonsterRepository();
    private ParserHandler parserChain;
    private MonsterTree monsterTree;
    private JButton exportButton;

    public MainFrame() {
        setTitle("Monster Manager");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initParsers();
        initUI();
    }

    private void initParsers() {
        JsonParser jsonParser = new JsonParser();
        XmlParser xmlParser = new XmlParser();
        YamlParser yamlParser = new YamlParser();
        jsonParser.setNextHandler(xmlParser);
        xmlParser.setNextHandler(yamlParser);
        parserChain = jsonParser;
    }

    private void initUI() {
        JPanel buttonPanel = new JPanel();
        JButton importButton = new JButton("Импорт");
        JButton exportButton = new JButton("Экспорт");
        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);
        //exportButton.setEnabled(false);

        monsterTree = new MonsterTree(repository);
        JScrollPane treeScroll = new JScrollPane(monsterTree);

        importButton.addActionListener(this::handleImport);
        exportButton.addActionListener(this::handleExport);

        add(buttonPanel, BorderLayout.NORTH);
        add(treeScroll, BorderLayout.CENTER);
    }

    private void handleImport(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(FileUtils.getCurrentDirectory());
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            boolean imported = false;
            for (File file : fileChooser.getSelectedFiles()) {
                try {
                    List<Monster> monsters = parserChain.parse(file.getPath());
                    repository.addMonstersFromSource(file.getName(), monsters);
                    monsterTree.updateTree();
                    imported = true;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Ошибка импорта: " + ex.getMessage());
                }
            }
            if (imported) {
                exportButton.setEnabled(!repository.getAllMonsters().isEmpty());
            }
        }
    }

    private void handleExport(ActionEvent e) {
        if (repository.getAllMonsters().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Нет данных для экспорта");
            return;
        }

        JFileChooser fileChooser = new JFileChooser(FileUtils.getCurrentDirectory());
        fileChooser.setDialogTitle("Экспорт данных");

        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("YAML Files", "yaml", "yml"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String format = "json";

            FileNameExtensionFilter filter = (FileNameExtensionFilter) fileChooser.getFileFilter();
            if (filter.getDescription().contains("XML")) format = "xml";
            else if (filter.getDescription().contains("YAML")) format = "yaml";

            String filePath = selectedFile.getPath();
            if (!filePath.endsWith("." + format)) {
                filePath += "." + format;
            }

            try {
                List<Monster> monsters = repository.getAllMonsters();
                exportData(monsters, filePath, format);
                JOptionPane.showMessageDialog(this, "Экспорт успешно завершен!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка экспорта: " + ex.getMessage());
            }
        }
    }

    private void exportData(List<Monster> monsters, String filePath, String format) throws Exception {
        switch (format) {
            case "json" -> {
                JsonParser jsonParser = new JsonParser();
                jsonParser.export(monsters, filePath);
            }
            case "xml" -> {
                XmlParser xmlParser = new XmlParser();
                xmlParser.export(monsters, filePath);
            }
            case "yaml" -> {
                YamlParser yamlParser = new YamlParser();
                yamlParser.export(monsters, filePath);
            }
            default -> throw new IllegalArgumentException("Неподдерживаемый формат");
        }
    }
}