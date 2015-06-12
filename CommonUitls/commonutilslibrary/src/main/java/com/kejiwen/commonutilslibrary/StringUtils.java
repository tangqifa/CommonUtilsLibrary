package com.kejiwen.commonutilslibrary;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import org.apache.http.NameValuePair;

import java.text.DecimalFormat;
import java.util.List;

public class StringUtils {

    public static String formatFloat(float f, int pos) {
        float p = 1f;
        StringBuilder format = new StringBuilder("#0");
        for (int i = 0; i < pos; i++) {
            if (i == 0) {
                format.append('.');
            }
            p *= 10f;
            format.append('0');
        }
        f = Math.round(f * p) / p;
        DecimalFormat formatter = new DecimalFormat(format.toString());
        return formatter.format(f);
    }

    /**
     * Highlight part of a string.
     */
    public static SpannableStringBuilder highlight(String text, int start, int end, int color) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static int parseInt(String s, int def) {
        try {
            int i = Integer.parseInt(s);
            return i;
        } catch (Exception e) {
            return def;
        }
    }

    public static int parseInt(String s) {
        return parseInt(s, 0);
    }

    public static long parseLong(String value, long def) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return def;
        }
    }

    public static float parseFloat(String s) {
        if (s != null) {
            try {
                float i = Float.parseFloat(s);
                return i;
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public static boolean isEmpty(String s) {
        if (s != null) {
            for (int i = 0, count = s.length(); i < count; i++) {
                char c = s.charAt(i);
                if (c != ' ' && c != '\t' && c != '\n' && c != '\r') {
                    return false;
                }
            }
        }
        return true;
    }

    public static String getFormatUrlFormParams(String url, List<NameValuePair> params) {
        if (params == null || params.isEmpty()) return url;
        StringBuilder result = new StringBuilder();
        boolean first = true;
        result.append(url + "?");
        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");
            if (pair.getValue() == null)
                continue;
            result.append(pair.getName());
            result.append("=");
            result.append(pair.getValue());
        }
        return result.toString();
    }

    public static boolean checkPassword(String value) {
        return value.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$");
    }
}
