package com.snail.BufferAndFile;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {


        Path path = Paths.get("F:\\项目测试文件 - 副本");
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                // 首先会访问文件，因此当退出访问文件夹时，文件夹中的文件已经被删除，因此文件夹可以被删除
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });

    }

    private static void printJavaFile() throws IOException {
        Path path = Paths.get("");
        AtomicInteger javaCount = new AtomicInteger();
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".java")) {
                    System.out.println(file);
                    javaCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("javaCount: " + javaCount);
    }

    private static void printFile() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();

        Path path = Paths.get("");
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("===" + dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("----" + file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);

            }
        });
        System.out.println("dirCount: " + dirCount.get() + ", fileCount: " + fileCount.get());
    }
}
