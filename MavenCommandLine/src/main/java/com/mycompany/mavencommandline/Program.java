package com.mycompany.mavencommandline;

public class Program
{

    public static void main(String[] args) {

        try {
            ReformatCSV.RevormatCSV("c:/sandboxes/di/WY_FM_20170716.txt", "c:/sandboxes/di/WY_FM_20170716.csv");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Done");
    }
}
