package parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import model.Monster;
import model.Bestiarum;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.List;

public class YamlParser implements ParserHandler {
    private ParserHandler nextHandler;

    @Override
    public void setNextHandler(ParserHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public List<Monster> parse(String filePath) throws Exception {
        if (filePath.endsWith(".yaml") || filePath.endsWith(".yml")) {
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            Bestiarum bestiarum = yamlMapper.readValue(new File(filePath), Bestiarum.class);
            return bestiarum.getMonsters();
        } else if (nextHandler != null) {
            return nextHandler.parse(filePath);
        }
        throw new UnsupportedOperationException("Формат не поддерживается");
    }

    @Override
    public void export(List<Monster> monsters, String filePath) throws Exception {
        YAMLFactory yamlFactory = new YAMLFactory()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper yamlMapper = new ObjectMapper(yamlFactory);

        Bestiarum bestiarum = new Bestiarum();
        bestiarum.setMonsters(monsters);

        try {
            yamlMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(filePath), bestiarum);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка экспорта YAML: " + e.getMessage());
            throw e;
        }
    }
}