package com.mycompany.mavencommandline;

import java.io.File;
import java.util.Collection;
import org.apache.commons.io.FileUtils;

public class Program
{

    public static void main(String[] args) {

        File dir = new File("c:/sandboxes/di");
        String[] extensions = {"csv", "txt"};
        Collection files = FileUtils.listFiles(dir, extensions, true);
        Integer count = 0;
        Integer total = files.size();

        for (Object fileName : files) {
            count++;
            String path = fileName.toString();
            try {
                System.out.println("File " + count + "/" + total + "...");
                ReformatCSV.ReformatCSV(path, path + "_ref");
            } catch (Exception ex) {
                System.out.println(path + " : " + ex.getMessage());
            }
        }

        System.out.println("Done");
    }
}
