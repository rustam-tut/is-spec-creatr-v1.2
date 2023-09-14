package is.infostms.isc.util;

import java.util.HashMap;
import java.util.Map;

public final class StringUtils {

    private static Map<Character, Character> cyrillicToLatin = new HashMap<Character, Character>()
    {{
        put('е', 'e');
        put('Е', 'E');
        put('н', 'h');
        put('Н', 'H');
        put('х', 'x');
        put('Х', 'X');
        put('в', 'b');
        put('В', 'B');
        put('а', 'a');
        put('А', 'A');
        put('р', 'p');
        put('Р', 'P');
        put('о', 'o');
        put('О', 'O');
        put('с', 'c');
        put('С', 'C');
        put('м', 'm');
        put('М', 'M');
        put('т', 't');
        put('Т', 'T');
        put('з', '3');
        put('З', '3');
    }};

    private StringUtils() {}

    public static String replaceCyrillicToLatin(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char strC = str.charAt(i);
            char c = cyrillicToLatin.get(strC) != null ? cyrillicToLatin.get(strC) : strC;
            sb.append(c);
        }
        return sb.toString();
    }
}
