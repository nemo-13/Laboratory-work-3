package parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import model.Monster;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.List;

public class XmlParser implements ParserHandler {
    private ParserHandler nextHandler;

    @Override
    public void setNextHandler(ParserHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public List<Monster> parse(String filePath) throws Exception {
        if (filePath.endsWith(".xml")) {
            XmlMapper mapper = new XmlMapper();
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            return mapper.readValue(
                new File(filePath),
                mapper.getTypeFactory().constructCollectionType(List.class, Monster.class)
                );
        } else if (nextHandler != null) {
            return nextHandler.parse(filePath);
        }
        throw new UnsupportedOperationException("Формат не поддерживается");
    }
    
    @Override
    public void export(List<Monster> monsters, String filePath) throws Exception {
        XmlMapper mapper = new XmlMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), monsters);
    }
}