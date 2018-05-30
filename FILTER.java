package filesprocessing;
import java.util.function.*;
import java.text.*;
import java.lang.*;
import java.io.*;

public class FILTER extends DirectoryProcessor {

    public boolean greater_then(File file, double Bytes){
        return ((double) (file.length()/1024) > Bytes);
    }

    public boolean smaller_then(File file, double Bytes){
        return  ((double) (file.length()/1024) < Bytes);
    }

    public boolean between(File file, double upperBytes, double lowerBytes){
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
