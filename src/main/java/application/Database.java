package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String,String> critici;
    private Map<String,String> ristoratori;
    private static Database instance=null;

    private Database() {
        this.critici = new HashMap<>();
        this.ristoratori = new HashMap<>();
        try {
            setUpCritiques("critici.txt");
            setUpRistoranti("ristoranti_ridotto.txt");
            setUpRistoratori("ristoratori.txt");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static synchronized Database getInstance(){
        if(instance == null)
            instance = new Database();
        return instance;
    }

    public synchronized boolean logInCritico(String username,String psw){
        if(critici.containsKey(username) && critici.get(username).equals(psw))
            return true;
        return false;
    }

    public synchronized boolean logInRistoratore(String username, String psw){
        if(ristoratori.containsKey(username) && ristoratori.get(username).equals(psw))
            return true;
        return false;
    }

    private void setUpCritiques(String fileName) throws IOException{
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bf.readLine()) != null){
            String [] token = line.split("&");
            critici.put(token[0],token[1]);
        }
        bf.close();
    }

    private void setUpRistoranti(String fileName)throws IOException{
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bf.readLine()) != null){
            String [] token = line.split("&");
            RestaurantCatalogue.getInstance().addRestaurant(token[0],token[1],token[2]);
        }
        bf.close();
    }

    private void setUpRistoratori(String fileName)throws IOException{
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bf.readLine()) != null){
            String [] token = line.split("&");
          ristoratori.put(token[0],token[1]);
        }
        bf.close();
    }
    public void addMenu(int key, String path) throws IOException {
        MenuRead mr = new MenuRead(path);
        HashMap<DishType, ArrayList<MenuEntry>> e = null;
        e = mr.fileRead();
        RestaurantCatalogue.getInstance().addMenu(e, key);
    }
}
