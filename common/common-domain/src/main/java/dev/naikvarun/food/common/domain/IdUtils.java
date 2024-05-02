package dev.naikvarun.food.common.domain;

import java.security.SecureRandom;
import java.util.Random;

public final class IdUtils {
    private IdUtils() {}

    private static final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();
    public static final char[] DEFAULT_ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static final int DEFAULT_SIZE = 6;

    public static String getOrderId() {
        return getEntityId("ORD");
    }
    public static String getProductId() {
        return getEntityId("PRD");
    }

    public static String getRestaurantId() {
        return getEntityId("RST");
    }
    public static String getCustomerId() {
        return getEntityId("CST");
    }


    public static String getEntityId(String entityPrefix) {
        return entityPrefix + "-" + randomId();
    }

    private static String randomId() {

        if (IdUtils.DEFAULT_ALPHABET.length == 0 || IdUtils.DEFAULT_ALPHABET.length >= 256) {
            throw new IllegalArgumentException("alphabet must contain between 1 and 255 symbols.");
        }

        if (IdUtils.DEFAULT_SIZE <= 0) {
            throw new IllegalArgumentException("size must be greater than zero.");
        }

        final int mask = (2 << (int) Math.floor(Math.log(IdUtils.DEFAULT_ALPHABET.length - 1) / Math.log(2))) - 1;
        final int step = (int) Math.ceil(1.6 * mask * IdUtils.DEFAULT_SIZE / IdUtils.DEFAULT_ALPHABET.length);

        final StringBuilder idBuilder = new StringBuilder();

        while (true) {

            final byte[] bytes = new byte[step];
            ((Random) IdUtils.DEFAULT_NUMBER_GENERATOR).nextBytes(bytes);

            for (int i = 0; i < step; i++) {

                final int alphabetIndex = bytes[i] & mask;

                if (alphabetIndex < IdUtils.DEFAULT_ALPHABET.length) {
                    idBuilder.append(IdUtils.DEFAULT_ALPHABET[alphabetIndex]);
                    if (idBuilder.length() == IdUtils.DEFAULT_SIZE) {
                        return idBuilder.toString();
                    }
                }

            }

        }

    }
}
