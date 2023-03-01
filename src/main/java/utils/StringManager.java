package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringManager {
    public static final String ALL_WHITE_SPACES_REGEX = "\\s+";
    public static final String PARENTHESIS_REGEX = "[()]";
    public static final String HTML_TAGS_REGEX = "<[^>]*>";
    public static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss z";
    public static String convertStringUsingRegex(String text, String regex, String replacementText) {
        return text.replace(regex, replacementText);
    }
    public static String removeWhiteSpaces(String text) {
        return convertStringUsingRegex(text, ALL_WHITE_SPACES_REGEX, "");
    }
    public static String removeParenthesis(String text) {
        return convertStringUsingRegex(text, PARENTHESIS_REGEX, "");
    }
    public static String removeHTMLTags(String text) {
        return convertStringUsingRegex(text, HTML_TAGS_REGEX, "");
    }
    public static String generateStringDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        return formatter.format(date);
    }
    public static String generateAlphanumericString(int length) {
        int origin = 48;
        int bound = 122;
        Random random = new Random();

        return random.ints(origin, bound+1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
