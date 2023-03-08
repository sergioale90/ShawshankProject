/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringManager {
    public static final String ALL_WHITE_SPACES_REGEX = "\\s+";
    public static final String PARENTHESIS_REGEX = "[()]";
    public static final String HTML_TAGS_REGEX = "<[^>]*>";
    public static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss z";
    public static final int ORIGIN = 48;
    public static final int BOUND = 122;
    public static final int CHAR_RANGE_1 = 57;
    public static final int CHAR_RANGE_2 = 65;
    public static final int CHAR_RANGE_3 = 90;
    public static final int CHAR_RANGE_4 = 97;

    public static final int USER_LENGTH = 7;

    public static final int DOMAIN_LENGTH = 4;


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
        Random random = new Random();

        return random.ints(ORIGIN, BOUND + 1)
                .filter(i -> (i <= CHAR_RANGE_1 || i >= CHAR_RANGE_2) && (i <= CHAR_RANGE_3 || i >= CHAR_RANGE_4))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String generateEmailString() {
        Random random = new Random();

        String randomUser = random.ints(CHAR_RANGE_4, BOUND + 1)
                .limit(USER_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        String randomDomain = random.ints(CHAR_RANGE_4, BOUND + 1)
                .limit(DOMAIN_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return randomUser + '@' + randomDomain + ".com";
    }
}
