package model;

import model.dao.UserDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterHelper {
    private static RegisterHelper ourInstance;

    private RegisterHelper() {}

    public static RegisterHelper getInstance() {
        if(ourInstance == null)
            ourInstance = new RegisterHelper();
        return ourInstance;
    }

    public boolean checkPasswordStrength(String password) {
        char[] test = password.toCharArray();

        boolean lowercase = false;
        boolean uppercase = false;
        boolean number = false;

        if(test.length < 8)
            return false;

        for (char aTest : test) {
            if (aTest >= 65 && aTest <= 90)
                lowercase = true;
            if (aTest >= 97 && aTest <= 122)
                uppercase = true;
            if (aTest >= 48 && aTest <= 57)
                number = true;
        }

        return !(!lowercase || !uppercase || !number);

    }

    public static String md5(String password) {
        try {

            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}