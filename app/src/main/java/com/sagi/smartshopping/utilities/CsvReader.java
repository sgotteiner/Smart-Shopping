package com.sagi.smartshopping.utilities;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CsvReader extends AsyncTask<Void, Void, Void> {


    private Context mContext;
    private CallbackOnLoadedCsv mListener;
    private ArrayList<String[]> rowsList = new ArrayList<>();
    private String mFileCsvName;


    public CsvReader(Context context, String fileCsvName, CallbackOnLoadedCsv callbackOnLoadedCsv) {
        this.mContext = context.getApplicationContext();
        this.mFileCsvName = fileCsvName;
        this.mListener = callbackOnLoadedCsv;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        start();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (mListener != null)
            mListener.onFinish(rowsList);

    }

    private void start() {
        try {
            InputStream inputStream = mContext.getAssets().open(mFileCsvName);


            InputStreamReader csvStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(csvStreamReader);
            String line;
            String csvSplitBy = ",";


            StringBuilder all = new StringBuilder();


            //header
            br.readLine();

            while ((line = br.readLine()) != null) {
               // String[] row = line.split(csvSplitBy);
//                rowsList.add(row);

                all.append(line);
//                for (int i = 0; i < row.length; i++) {
//                    all.append(row[i]);
//                }
            }

            String allData = all.toString().replaceAll("\n", "");
            String[] row = allData.split(csvSplitBy);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public class UserCsv {
//        private String name;
//        private String lastName;
//        private String city;
//
//        public UserCsv(String[] raw) {
//            name = raw[0];
//            lastName = raw[1];
//            city = raw[2];
//        }
//
//        @Override
//        public String toString() {
//            return "UserCsv{" +
//                    "name='" + name + '\'' +
//                    ", lastName='" + lastName + '\'' +
//                    ", city='" + city + '\'' +
//                    '}'
//                    + "\n\n";
//        }
//    }

    public interface CallbackOnLoadedCsv {
        void onFinish(ArrayList<String[]> rows);
    }
}
