package seacsp.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class ReadFile {

    public ReadFile() {
    }
    
    public boolean checkThatFileExists(File file) {
        return file.exists();
    }
    
    public String readFileLineByLine(File file) throws Exception {
        StringBuilder contentBuilder = new StringBuilder(); 
        try (Stream<String> stream = Files.lines(Paths.get(file.toString()), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) {
            throw e;
        }
        return contentBuilder.toString();
    }
}
