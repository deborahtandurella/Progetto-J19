package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author cl445589
 */
public class RestaurantRead {
    private String line;
    private FileReader file;
    private BufferedReader buffer;

    public RestaurantRead(String nomeFile) throws FileNotFoundException {
        this.line = null;
        this.file = new FileReader(nomeFile);
        this.buffer = new BufferedReader(this.file);
    }
    protected String [] fileRead() throws IOException{
        this.line = this.buffer.readLine();
         String[] result = null;
        if (this.line !=  null ) {
            result = line.split("\\t");
            
        }
        return result;
    }
    
    
    
}