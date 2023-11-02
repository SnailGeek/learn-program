package com.snail.BufferAndFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TestFilesWalkFile {
    public static void main(String[] args) throws IOException {
        String source = "F:\\项目测试文件";
        String target = "F:\\项目测试文件-bak";
        Stream<Path> paths = Files.walk(Paths.get(source));
        paths.forEach(path -> {
            try {
                String targetName = path.toString().replace(source, target);
                if (Files.isDirectory(path)) {
                    Files.createDirectory(Paths.get(targetName));
                } else if (Files.isRegularFile(path)) {
                    Files.copy(path, Paths.get(targetName));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
