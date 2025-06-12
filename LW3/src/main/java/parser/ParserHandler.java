package parser;

import model.Monster;
import java.util.List;

public interface ParserHandler {
    void setNextHandler(ParserHandler nextHandler);
    List<Monster> parse(String filePath) throws Exception;
    void export(List<Monster> monsters, String filePath) throws Exception;
}