package xevate.com.ratex;

import java.util.ArrayList;

/**
 * Created by athome on 3/11/2017.
 */

public class Singleton {

    public ArrayList<CurIndex_Value> USDBaseArrayList ;

    private static Singleton instance;

    private Singleton(){
        USDBaseArrayList = new ArrayList<CurIndex_Value>();
     }

    public static Singleton getInstance(){
        if (instance == null){ instance = new Singleton(); }
        return instance;
    }

    public ArrayList<CurIndex_Value> getUSDBaseArrayList() {
        return USDBaseArrayList;
    }
    public void resetUSDBaseArrayList(){
        this.USDBaseArrayList = null;
    }

    public void addUSDBaseArrayList(CurIndex_Value obj) {
        this. USDBaseArrayList.add(obj);
    }

}
