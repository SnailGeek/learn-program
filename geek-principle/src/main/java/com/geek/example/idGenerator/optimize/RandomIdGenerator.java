package com.geek.example.idGenerator.optimize;

import com.geek.example.idGenerator.exception.IdGenerationFailureException;
import com.google.common.annotations.VisibleForTesting;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @program: RandomIdGenerator
 * @description:
 * @author: wangf-q
 * @date: 2022-12-28 11:37
 **/
public class RandomIdGenerator implements LogTraceIdGenerator {
    @Override
    public String generate() throws IdGenerationFailureException {
        final String substrOfHostName;
        try {
            substrOfHostName = getLastFieldOfHostName();
        } catch (UnknownHostException e) {
            throw new IdGenerationFailureException("host name is empty.");
        }
//        // 明确的告知调用者，这种异常情况
//        if (substrOfHostName == null || substrOfHostName.isEmpty()) {
//            throw new IdGenerationFailureException("host name is empty");
//        }
        final long currentTimeMillis = System.currentTimeMillis();
        final String randomString = generateRandomAlphameric(8);
        final String id = String.format("%s-%d-%s", substrOfHostName, currentTimeMillis, randomString);
        return id;
    }

    private String getLastFieldOfHostName() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        if (hostName == null || hostName.isEmpty()) {
            throw new UnknownHostException("....");
        }
        return getLastSubstrSplittedByDot(hostName);
    }


    @VisibleForTesting
    public String getLastSubstrSplittedByDot(String hostName) {
        if (hostName == null || hostName.isEmpty()) {
            throw new IllegalArgumentException("....");
        }
        String substrOfHostName;
        final String[] tokens = hostName.split("\\.");
        substrOfHostName = tokens[tokens.length - 1];
        return substrOfHostName;
    }

    @VisibleForTesting
    public String generateRandomAlphameric(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("...");
        }
        final char[] randomChars = new char[length];
        int count = 0;
        Random random = new Random();
        while (count < length) {
            int maxAscii = 'z';
            final int randomAscii = random.nextInt(maxAscii);
            final boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
            final boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            final boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count] = (char) (randomAscii);
                ++count;
            }
        }
        return new String(randomChars);
    }
}
