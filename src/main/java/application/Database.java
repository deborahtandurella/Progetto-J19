package application;

import application.database_exception.InvalidUsernameException;

import java.io.*;
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

    // TODO rename in setUpCritics
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

    public void criticSignUp(String username,String password){
        if(this.critici.containsKey(username))
            throw new InvalidUsernameException("Username already taken!");
        this.critici.put(username,password);
        updateDB(this.critici,"critici.txt");
    }

    public void ristoratoriSignUp(String username, String password){
        if(this.ristoratori.containsKey(username))
            throw new InvalidUsernameException("Username already taken!");
        this.ristoratori.put(username,password);
        updateDB(this.ristoratori,"ristoratori.txt");
    }

    private void updateDB(Map<String,String> info,String fileName){
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            for (Map.Entry<String,String> a: info.entrySet()) {
                pw.println(a.getKey()+"&"+a.getValue());
            }

            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
