package model.connectionhelper;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by user-17 on 4/2/16.
 */
public class ConnectionHelper {

    private static String REGISTER = "register";
    private static String DELETE   = "delete";
    private static String UPDATE   = "update";

    private static String TYPE_EMAIL = "email";

    private ConnectionHelper() {
    }

    public static String registerUser(String userJson){
        alterUser(userJson, REGISTER);
        return getUserJson(userJson, TYPE_EMAIL);
    }

    private static void alterUser(String userJson, String type) {
        try {
            URL url = new URL("http://192.168.6.239:8080/Server/PostUserServlet");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            String str = "userJson=" + userJson + "&type=" + type;
            con.setDoOutput(true);
            byte[] outputInBytes = str.getBytes("UTF-8");
            OutputStream os = con.getOutputStream();
            os.write(outputInBytes);
            os.close();

            con.connect();

            final int statusCode = con.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("error" + statusCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static String getUserJson(String userJson, String type) {
        StringBuilder data = new StringBuilder("");
        try {
            URL url = new URL("http://192.168.6.239/:8080/Server/GetUserServlet?type=" + type + "&userJson=" + userJson);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.connect();

            Scanner scanner = new Scanner(con.getInputStream());

            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
        }  catch (IOException e) {
            Log.e(e.toString(), " ");
        }
        return data.toString();
    }

//    private class GetRequest extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            StringBuilder data = new StringBuilder("");
//            try {
//                Log.e("-------da", "http://10.0.3.2:8080/Server/GetUserServlet?type=" + params[0] + "&col=" + params[1] + "&value=" + params[2]);
//                    URL url = new URL("http://10.0.3.2:8080/Server/GetUserServlet?type=" + params[0] + "&col=" + params[1] + "&value=" + params[2]);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("GET");
//
//                    con.connect();
//
//                    Scanner scanner = new Scanner(con.getInputStream());
//
//                    while (scanner.hasNextLine()) {
//                        data.append(scanner.nextLine());
//                }
//
//            } catch (MalformedURLException e) {
//                Log.e(e.toString(), " ");
//            } catch (IOException e) {
//                Log.e(e.toString(), " ");
//            }
//            return data.toString();
//        }
//
//        @Override
//        protected void onPostExecute(String text) {
//
//        }
//    }

}
