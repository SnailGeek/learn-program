package com.geek.design.singleton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @program: Logger
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 15:49
 **/
public class Logger {

    private FileWriter writer;

    private static final Logger instance = new Logger();

    private Logger() {
        try {
            File file = new File("");
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Logger instance() {
        return instance;
    }

    public void log(String message) {
        try {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
