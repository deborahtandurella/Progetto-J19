package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String,String> critici;
    private static Database instance=null;

    private Database() {
        this.critici = new HashMap<>();
        try {
            setUpCritiques("critici.txt");
            setUpRistoranti("ristoranti.txt");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static synchronized Database getInstance(){
        if(instance == null)
            instance = new Database();
        return instance;
    }

    public synchronized boolean logIn(String username,String psw){
        if(critici.containsKey(username) && critici.get(username).equals(psw))
            return true;
        return false;
    }

    private void setUpCritiques(String fileName) throws IOException{
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bf.readLine()) != null){
            String [] token = line.split(",");
            critici.put(token[0],token[1]);
        }
    }

    private void setUpRistoranti(String fileName)throws IOException{
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bf.readLine()) != null){
            String [] token = line.split("\t");
            RestaurantCatalogue.getInstance().addRestaurant(token[0],token[1]);
        }
    }
}
