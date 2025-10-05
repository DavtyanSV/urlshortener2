package org.example.urlshortener.base62;

public class Base62Converter {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String encode(long number) {
        StringBuilder sb = new StringBuilder();
        if (number == 0) {
            return "0";
        }
        while (number > 0) {
            int remainder = (int) (number % 62);
            sb.append(BASE62.charAt(remainder));
            number /= 62;
        }

        // здесь разворачиваем, так как вычисляем с конца
        return sb.reverse().toString();
    }

    public long decode(String shortUrl) {
        long result = 0;
        for (int i = 0; i < shortUrl.length(); i++) {
            result = result * 62 + BASE62.indexOf(shortUrl.charAt(i));
        }
        return result;
    }
}

