package com.ryan.c1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author kq
 * 2024-09-27 09:41
 **/
public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("~/Documents"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("=======>" + dir);
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file.getFileName());
                return super.visitFile(file, attrs);
            }
        });
    }
}
