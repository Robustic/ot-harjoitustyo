package seacsp.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Class for reading text-files to String.
 */
public class ReadFile {

    /**
     * Constructor.
     */
    public ReadFile() {
    }
    
    /**
     * Check if given file exists.
     * 
     * @param   file   file
     * 
     * @return true if file exists
     */
    public boolean checkThatFileExists(File file) {
        return file.exists();
    }
    
    /**
     * Method to read given text file to string.
     * 
     * @throws IOException  If exception occurred
     * 
     * @param   file   file
     * 
     * @return text file content as string
     */
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
