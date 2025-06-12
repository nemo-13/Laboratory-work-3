package gui;

import model.Monster;
import parser.*;
import repository.MonsterRepository;
import util.FileUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class MainFrame extends JFrame {
    private final MonsterRepository repository = new MonsterRepository();
    private ParserHandler parserChain;
    private MonsterTree monsterTree;

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
            for (File file : fileChooser.getSelectedFiles()) {
                try {
                    List<Monster> monsters = parserChain.parse(file.getPath());
                    repository.addMonstersFromSource(file.getName(), monsters);
                    monsterTree.updateTree();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Ошибка импорта: " + ex.getMessage());
                }
            }
        }
    }

    private void handleExport(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(FileUtils.getCurrentDirectory());
        fileChooser.setDialogTitle("Экспорт данных");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "JSON/XML/YAML файлы", "json", "xml", "yaml", "yml"
        ));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getPath();

            String format = "json";
            if (filePath.endsWith(".xml")) {
                format = "xml";
            } else if (filePath.endsWith(".yaml") || filePath.endsWith(".yml")) {
                format = "yaml";
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