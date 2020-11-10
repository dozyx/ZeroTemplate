package java.lang;

import androidx.annotation.NonNull;

import java.nio.charset.Charset;
import java.util.Formatter;
import java.util.Locale;

/*public class String implements CharSequence {
    public String() {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(byte bytes[]) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public int length() {
        return 10;
    }

    public String substring(int beginIndex, int endIndex) {
        return "111";
    }

    public native String intern();

    public byte[] getBytes(Charset charset) {
        return null;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @NonNull
    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    public int lastIndexOf(String str) {
        return 10;
    }

    public String substring(int beginIndex) {
        return "222";
    }

    public static String format(Locale l, String format, Object... args) {
        return new Formatter(l).format(format, args).toString();
    }

    public byte[] getBytes() {
        return getBytes(Charset.defaultCharset());
    }

    public static String format(String format, Object... args) {
        return new Formatter().format(format, args).toString();
    }

    public String toUpperCase() {
        return "toUpperCase(Locale.getDefault())";
    }

    public int indexOf(int ch) {
        return 1;
    }

    public String replace(CharSequence target, CharSequence replacement) {
        return "";
    }

    public int indexOf(String str) {
        return 1;
    }

    public static String valueOf(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }

    public native char[] toCharArray();
}*/
