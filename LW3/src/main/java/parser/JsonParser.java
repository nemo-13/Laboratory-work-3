package parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Monster;
import model.Bestiarum;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.List;

public class JsonParser implements ParserHandler {
    private ParserHandler nextHandler;

    @Override
    public void setNextHandler(ParserHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public List<Monster> parse(String filePath) throws Exception {
        if (filePath.endsWith(".json")) {
            ObjectMapper mapper = new ObjectMapper();
            Bestiarum bestiarum = mapper.readValue(new File(filePath), Bestiarum.class);
            return bestiarum.getMonsters();
        } else if (nextHandler != null) {
            return nextHandler.parse(filePath);
        }
        throw new UnsupportedOperationException("Формат не поддерживается");
    }
    
    @Override
    public void export(List<Monster> monsters, String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), monsters);
    }
}