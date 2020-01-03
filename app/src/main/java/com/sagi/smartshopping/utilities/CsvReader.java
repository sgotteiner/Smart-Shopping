package com.sagi.smartshopping.utilities;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sagi.smartshopping.entities.Shop;
import com.sagi.smartshopping.utilities.constant.FireBaseConstant;

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
            mListener.onFinish(arrShops);

    }

    private ArrayList<Shop> arrShops;

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
            String name, mallName, city, urlLogo, openTime, category, phone, description, address;
            int floor;
            arrShops = new ArrayList<>();
            for (int i = 0; i < row.length; ) {
                name = row[i++];
                mallName = row[i++];
                city = row[i++];
                urlLogo = row[i++];
                openTime = row[i++];
                category = row[i++];
                floor = Integer.valueOf(row[i++]);
                phone = row[i++];
                description = row[i++];
                address = row[i++];
                arrShops.add(new Shop(name, mallName, city, urlLogo, openTime, category, floor, phone, description, address));
            }
            uploadShopsToFirebase(arrShops);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private void uploadShopsToFirebase(ArrayList<Shop> arrShops) {
        for (int i = 0; i < arrShops.size(); i++) {
            String key = myRef.child(FireBaseConstant.SHOPS_TABLE).push().getKey();
            arrShops.get(i).setKey(key);
            assert key != null;
            myRef.child(FireBaseConstant.SHOPS_TABLE).child(key).setValue(arrShops.get(i));
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
        void onFinish(ArrayList<Shop> shops);
    }
}
