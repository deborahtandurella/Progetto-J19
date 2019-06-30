package application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static Database instance=null;

    private Database() {
        try {
            setUpRistoranti("ristoranti_ridotto.txt");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static synchronized Database getInstance(){
        if(instance == null)
            instance = new Database();
        return instance;
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


    public void addMenu(int key, String path) throws IOException {
        MenuRead mr = new MenuRead(path);
        HashMap<DishType, ArrayList<MenuEntry>> e = null;
        e = mr.fileRead();
        RestaurantCatalogue.getInstance().addMenu(e, key);
    }

}
