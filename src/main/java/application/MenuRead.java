package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuRead {
    private String line;
    private final FileReader file;
    private final BufferedReader buffer;

    public MenuRead(String path) throws FileNotFoundException {
        this.line = null;
        this.file = new FileReader(path);
        this.buffer = new BufferedReader(file);
    }
    
    protected HashMap<DishType,ArrayList<MenuEntry>> fileRead() throws IOException{
        HashMap<DishType,ArrayList<MenuEntry>> a = new HashMap<>();
        initializeMenu(a);
        int tmp = 1;
        while ((line = buffer.readLine()) != null) {
            String[] result = line.split(",");
            //System.out.println(result.length+"a");
            MenuEntry e = new MenuEntry( result[1], Double.parseDouble(result[2]), tmp);
            DishType en = switchFumction(result[0]);
            //System.out.println(en);
            a.get(en).add(e);
            tmp++;
        }
        file.close();
        return a;
    }

    private DishType switchFumction(String choice){
        switch (choice){
            case "antipasto" :
                return DishType.ANTIPASTI;
            case "primo": return DishType.PRIMI;
            case "secondo": return DishType.SECONDI;
            case "dolce": return DishType.DOLCI;
            default: return null;
        }
    }
    private void initializeMenu(HashMap<DishType,ArrayList<MenuEntry>> menu){
        menu.put(DishType.ANTIPASTI, new ArrayList<>());
        menu.put(DishType.PRIMI, new ArrayList<>());
        menu.put(DishType.SECONDI, new ArrayList<>());
        menu.put(DishType.DOLCI, new ArrayList<>());
    }
}
