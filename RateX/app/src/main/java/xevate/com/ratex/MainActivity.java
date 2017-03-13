package xevate.com.ratex;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class MainActivity  extends Activity {

    private String mCurrencyDataJSON;
    ListView listv;
    EditText priceEditText;
    Button btnok;
    Timer timer;
    TimerTask timerTask;
    Spinner spinner;
    final Handler handler = new Handler();
    public GetRateSync SilentLoad;
    ArrayList<CurIndex_Value> ind_val_ArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        priceEditText = (EditText) findViewById(R.id.priceinputTxtEdit);
        spinner = (Spinner) findViewById(R.id.spinner1) ;
        btnok = (Button) findViewById(R.id.okbtn);

        listv = (ListView) findViewById(android.R.id.list);

        priceEditText.setText("1.00");
        spinner.setSelection(30);

        priceEditText.addTextChangedListener(new MoneyTextWatcher(priceEditText));

        ind_val_ArrayList = Singleton.getInstance().getUSDBaseArrayList(); // initiate

        btnok.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ListUpdateResponsive();
                return true;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ListUpdateResponsive();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    public void ListUpdateResponsive(){
        String your_cur = spinner.getSelectedItem().toString();
        String money_str = priceEditText.getText().toString().replaceAll( "[^\\d.]/g", "" );//replaceAll("[^\d.-]/g", "");
//
        int len =  ind_val_ArrayList.size();
        String[] Index_Country = new String[len];
        Double a = 1.00;
        for(int i=0;i<len;i++){
            System.out.println("ind_val_ArrayList.get(i) =" + ind_val_ArrayList.get(i));
            if(your_cur.contains(ind_val_ArrayList.get(i).getIndex())){
                a = ind_val_ArrayList.get(i).getVal();
                System.out.println("a="+a);
                break;
            }
        }

        Double money = Double.parseDouble(money_str.replaceAll(",","").substring(1));
        if(money_str.contains("1.00")){
            money=1.0;
        }

        for(int i=0;i<len;i++){
            Double converted_amount = money*ind_val_ArrayList.get(i).getVal()/a;
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
           // System.out.println(formatter.format(converted_amount));

            Index_Country[i]=ind_val_ArrayList.get(i).getIndex() + " : "  + formatter.format(converted_amount);

            System.out.println("convert = " + money + " " +  your_cur +" to " + converted_amount +" "+ ind_val_ArrayList.get(i).getIndex() );

        }



        updatelistview(Index_Country);
    }

    public void updatelistview(String[] Index_Country){
        TienArrayAdapter setListAdapter = new TienArrayAdapter(this, Index_Country);


        listv.setAdapter(setListAdapter);
    }
    ///////////////
    @Override
    protected void onResume() {
        super.onResume();
        //onResume we start our timer so it can start when the app comes from the background
        startTimer();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 120000ms (2m)
        timer.schedule(timerTask, 200, 1800000); //every 30 mins=1800000
    }

    public void stoptimertask(View v) {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        SilentLoad = new GetRateSync();
                        SilentLoad.execute();

                    }
                });
            }
        };
    }

    //////////////////////////////////
    public class GetRateSync extends AsyncTask<String, Integer, String> {
        //        private int flag_number = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String longprofeed = "0";
            //String currencyUrl = "http://api.fixer.io/latest?base=" + base;
            String currencyUrl = "http://api.fixer.io/latest?base=USD";

            if (isNetworkAvailable()) {
                //Initialize client object
                OkHttpClient client = new OkHttpClient();
                //Initialize request object
                Request request = new Request.Builder()
                        .url(currencyUrl).build();
                //Initialize call object to send the request
                okhttp3.Call call = client.newCall(request);
            /*
                enqueue() method supports asynchronous processing (process in background thread) so that user
                can still interact with the app while it is getting the data from the API
            */
                call.enqueue(new Callback() {

                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        // To execute certain action in the UI thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Opps! Please try again later.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {

                        try {
                            // Get the data in JSON format from the response object
                            mCurrencyDataJSON = response.body().string();
                            // To check the status of the response object
                            if (response.isSuccessful()) {
                                // To execute certain action in the UI thread
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                       //Log.d(TAG, "mCurrencyDataJSON caught: " + mCurrencyDataJSON);
                                        JSONObject jsonobj = null;//Whole Obj
                                        try {
                                            jsonobj = new JSONObject(mCurrencyDataJSON);
                                            updateRates(jsonobj);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                // To execute certain action in the UI thread
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Opps! Please try again later.", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "Exception caught: ", e);
                        }
                    }
                });
            }
        return "OK";
        }
        //////////////////////////////////////////////////////////////////////////////////
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            Log.d(TAG, "result caught: " + result);
          //  ArrayList<CurIndex_Value> USDBaseArrayList = Singleton.getInstance().getUSDBaseArrayList();

            int len =  ind_val_ArrayList.size();
            String[] Index_Country = new String[len];
            for(int i=0;i<len;i++){
                Index_Country[i]=ind_val_ArrayList.get(i).getIndex() + " : " + +ind_val_ArrayList.get(i).getVal();
                //System.out.println(ind_val_ArrayList.get(i).getIndex() + " / " +ind_val_ArrayList.get(i).getVal());
            }

           // MainActivity.updatelistview(Index_Country);
            updatelistview(Index_Country);

//            for(int i=0;i<len;i++){
//                System.out.println(ind_val_ArrayList.get(i).getIndex() + " / " +ind_val_ArrayList.get(i).getVal());
//            }


        }
        private boolean isNetworkAvailable() {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            boolean isAvailable = false;
            if (networkInfo != null && networkInfo.isConnected()) {
                isAvailable = true;
            }

            return isAvailable;
        }

        private void updateRates(JSONObject objwholedata) {
            try {
                JSONObject obj_rates = objwholedata.getJSONObject("rates"); //SubObj
             //   Log.d(TAG, "AUD=" +  obj_rates.getString("AUD"));
                Iterator<String> iter = obj_rates.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    try {
                        Object value = obj_rates.get(key);
                       // Log.i(TAG, "Update key/val = " +key +"/" +value.toString());
//                        Log.i(TAG, "value: " +  value.toString());
                        double val = Double.parseDouble(value.toString());
                        CurIndex_Value index_val_i = new CurIndex_Value();
                        index_val_i.setUsdCur(key,val);
                       // .set()
                 //   ind_val_ArrayList.addUSDBaseArrayList(index_val_i);
                        Singleton.getInstance().addUSDBaseArrayList(index_val_i);
                    } catch (JSONException e) {
                        // Something went wrong!
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}