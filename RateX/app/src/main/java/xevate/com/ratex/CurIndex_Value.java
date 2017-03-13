package xevate.com.ratex;

/**
 * Created by athome on 3/11/2017.
 */

public class CurIndex_Value {
    public String Currency_Index;
    public double Currency_value;


    public void setUsdCur(String cur_index, double cur_val) {
        this.Currency_Index = cur_index;
        this.Currency_value = cur_val;
    }

    public String getIndex() {
        return Currency_Index;
    }

    public double getVal() {
        return Currency_value;
    }


}
