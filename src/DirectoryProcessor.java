import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;
import java.io.*;

/**
 *
 */
public class DirectoryProcessor {

    private static final int optimized_INPUT = 2;
    private static final String NO_FILE_FOUND = "there was no file you annoying person!";
    private static final String NO_COMMANDS = "there are no commands you stupid boy,"+"\n"+
            "i am not your toy.";
    private static final String BAD_COMMANDS = "invalid command input! there should be ONLY two commands.";

    /**
     *
     * @param exeptionText
     */
    public static void printException(String exeptionText) {
        System.out.println();
        System.out.println(exeptionText);
        System.exit(0);
    }

    /**
     *
     * @param path
     * @return
     */
    public ArrayList<String> CommandsList(String path){
        ArrayList<String> commands = new ArrayList<String>();
        File file = new File(path);
        try {
            if (!file.isFile() || !file.exists()) {
                throw new FileNotFoundException();
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                commands.add(scanner.nextLine());
            }
            scanner.close();
        }catch (FileNotFoundException e) {
            printException(NO_FILE_FOUND);
        }
        return commands;
    }

    /**
     *
     * @param commands
     * @return
     */
    public static void inputValidity(ArrayList<String> commands) {
        try {
            if (commands.isEmpty()) {
                throw new Exception();
            }
        } catch (Exception e) {
            printException(NO_COMMANDS);
        }
        try {
            if (commands.size() != optimized_INPUT) {
                throw new Exception();
            }
        } catch (Exception e) {
            printException(BAD_COMMANDS);
        }
    }

    /**
     *
     * @param directoryPath
     * @return
     */
    public ArrayList<File> directoryFileList(String directoryPath) {
        ArrayList<File> files = new ArrayList<File>();
        File fileDirectory = new File(directoryPath);
        try {
            if (!fileDirectory.isDirectory() || fileDirectory.length() == 0) {
                throw new FileNotFoundException();
            }
            File[] fileList = fileDirectory.listFiles();
            for (File file : fileList) {
                if (file.isFile() && file.exists()) {
                    files.add(file);
                }
            }
        } catch (FileNotFoundException e) {
            printException(NO_FILE_FOUND);
        }
        return files;
    }


    /**
     *
     * @param args
     */
    public static void main(String[] args){
        DirectoryProcessor mainProccesor = new DirectoryProcessor();
        inputValidity(args);
    }
}
