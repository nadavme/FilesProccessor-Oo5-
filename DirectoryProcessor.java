package filesprocessing;

import java.nio.file.Path;
import java.util.function.*;
import java.text.*;
import java.lang.*;
import java.io.*;


public class DirectoryProcessor {


        private String inputFileName = "data1.txt";

        private File file = new File(inputFileName);

        private String path = file.getPath();

        private File temp = new File(path);

        private String whatToCheck;

        public boolean check(){
            switch (whatToCheck){
                case "greater_then":
                    return greater_then(file, bytes);
                case "between":
                    return between(file, upperBytes, lowerBytes);
                case "smaller_then":
                    return smaller_then(file, bytes);
            }
        }

        private class FILTER extends DirectoryProcessor {

            private boolean greater_then(File file, double Bytes){
                return ((double) (file.length()/1024) > Bytes);
            }

            private boolean smaller_then(File file, double Bytes){
                return  ((double) (file.length()/1024) < Bytes);
            }

            private boolean between(File file, double upperBytes, double lowerBytes){
                return ((double) (file.length()/1024) >= lowerBytes && (double) (file.length()/1024) <= upperBytes);
            }

            private boolean file(File file, String value){
                return file.getName().equals(value);
            }

            private boolean contains(File file, String value){
                return file.getName().contains(value);
            }

            private boolean prefix(File file, String value){
                String[] fileName = file.getName().split(".");
                for (int i = 0; i < (fileName.length-2); i++) {
                    if (fileName[i].contains(value)){
                        return true;
                    }
                }
                return false;
            }

            private boolean suffix(File file, String value){
                String[] fileName = file.getName().split(".");
                return fileName[fileName.length-1].contains(value);
            }

            private boolean writable(File file){
                return file.canWrite();
            }

            private boolean executable(File file){
                return file.canExecute();
            }

            private boolean hidden(File file){
                return file.isHidden();
            }

            private boolean all(File fatherFile){
                boolean areAllGood = true;
                String[] fileList = fatherFile.list();
                for (String file:fileList
                        ) {
                    while (file.check()) {
                        continue;
                    }
                    areAllGood = false;
                }
                return areAllGood;
            }
        }

//    public static void main(String[] args) {
//        System.out.println(file.length());
//        System.out.println(file.getFreeSpace());
//        System.out.println(file.getName());
//        System.out.println(file.getParentFile());
//        System.out.println(temp.length());
//        System.out.println(temp.getFreeSpace());
//        System.out.println(temp.getName());
//        System.out.println(temp.getParentFile());
//        System.out.println(path);
//        double bytes = temp.length();
//        double kilobytes = (bytes / 1024);
//        double megabytes = (kilobytes / 1024);
//        double gigabytes = (megabytes / 1024);
//        double terabytes = (gigabytes / 1024);
//        double petabytes = (terabytes / 1024);
//        double exabytes = (petabytes / 1024);
//        double zettabytes = (exabytes / 1024);
//        double yottabytes = (zettabytes / 1024);
//
//        System.out.println("bytes : " + bytes);
//        System.out.println("kilobytes : " + kilobytes);
//        System.out.println("megabytes : " + megabytes);
//        System.out.println("gigabytes : " + gigabytes);
//        System.out.println("terabytes : " + terabytes);
//        System.out.println("petabytes : " + petabytes);
//        System.out.println("exabytes : " + exabytes);
//        System.out.println("zettabytes : " + zettabytes);
//        System.out.println("yottabytes : " + yottabytes);
//    }

}


