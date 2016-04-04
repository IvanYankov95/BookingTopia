package model.connectionhelper;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by user-17 on 4/2/16.
 */
public class ConnectionHelper {

    private static String REGISTER = "register";
    private static String DELETE   = "delete";
    private static String UPDATE   = "update";

    private static String TYPE_EMAIL = "email";
    private static String TYPE_ID    = "user_id";

    private static String CHECK_LOGIN    = "login";

    private ConnectionHelper() {
    }

    public static String[] registerUser(String userJson){
        String[] strs = alterUser(userJson, REGISTER);
        strs[3] = getUserJson(userJson, TYPE_EMAIL);

        return strs;
    }

    public static void deleteUser(String userJson){
        alterUser(userJson, DELETE);
    }

    public static void updateUser(String userJson){
        alterUser(userJson, UPDATE);
    }

    public static String getUserById(String userJson){
        return getUserJson(userJson, TYPE_ID);
    }

    public static String getUserByEmail(String userJson){
        return getUserJson(userJson, TYPE_EMAIL);
    }

    public static boolean login(String email, String password){
        String[] args = {null, email, password};
        return checkUserData(args, CHECK_LOGIN);
    }

    private static String[] alterUser(String userJson, String type) {
        String[] strs = new String[4];
        strs[0] = "";
        strs[1] = "";
        strs[2] = "";
        strs[3] = "";
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
            if(statusCode == HttpURLConnection.HTTP_OK)
                strs[0] = "true";

            if(statusCode == HttpURLConnection.HTTP_BAD_REQUEST){
                // username
                strs[1] = "false";
            } else  if(statusCode == HttpURLConnection.HTTP_BAD_METHOD){
                // password
                strs[2] = "false";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return strs;
    }


    private static String getUserJson(String userJson, String type) {
        StringBuilder data = new StringBuilder("");
        try {
            URL url = new URL("http://192.168.6.239:8080/Server/GetUserServlet?type=" + type + "&userJson=" + userJson.trim());
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

    private static boolean checkUserData(String[] args, String type){
        StringBuilder data = new StringBuilder("");
        try {
            Log.e("log5", args[0]+ " 1 =" + args[1] + " 2=" + args[2]);
            URL url = new URL("http://192.168.6.239:8080/Server/CheckUserDataServlet?type=" + type + "&username=" + args[0] + "&email=" + args[1] + "&password=" + args[2]);
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
        //Log.e("log5", data.toString());
        return data.toString().equalsIgnoreCase("true");
    }

}
