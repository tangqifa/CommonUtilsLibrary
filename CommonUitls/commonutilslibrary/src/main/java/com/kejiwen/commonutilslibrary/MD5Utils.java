package com.kejiwen.commonutilslibrary;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tangqifa on 15/6/9.
 */
public class MD5Utils {

    public static String getMD5(String str) {
        try {
            MessageDigest message = MessageDigest.getInstance("MD5");
            message.update(str.getBytes());
            byte[] b = new byte[16];
            b = message.digest();
            String digestHexStr = "";
            for (int i = 0; i < 16; i++)
                digestHexStr += byteHEX(b[i]);
            return digestHexStr;
        } catch (NoSuchAlgorithmException e2) {
            System.out.println("error:" + e2);
            return null;
        }
    }

    public static String byteHEX(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }
}
