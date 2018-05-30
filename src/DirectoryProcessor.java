import java.nio.file.Path;
import java.util.ArrayList;
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
