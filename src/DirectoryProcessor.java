// this are the java util functions we are going to use
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.lang.*;
import java.io.*;

/**
 * the father class for all the filters and orders we will want to use.
 */
public class DirectoryProcessor {

    /**
     * our permanent parameters of this class, mostly exception prints.
     */
    private static final int optimized_INPUT = 2;
    private static final String NO_FILE_FOUND = "there was no file you annoying person!";
    private static final String NO_COMMANDS = "there are no commands you stupid boy,"+"\n"+
            "i am not your toy.";
    private static final String BAD_COMMANDS = "invalid command input! there should be ONLY two commands.";
    private static final String DEFAULT_COMMAND = "";

    private class Block {
        private int size;
        private String[] list;
        private Block block = new Block(4);
        private Block(int size){
            block.size = size;
            block.list = new String[size];
        }
    }
    /**
     * this func simply prints the exception we encountered.
     * @param exeptionText - what we want to print.
     */
    public static void printException(String exeptionText) {
        System.out.println();
        System.out.println(exeptionText);
        System.exit(0);
    }

    /**
     * this func takes the file that has the commands and separates each line and
     * putts all of the lines/commands in an array list of strings.
     * @param path - the path we receive for the command file.
     * @return the array list of commands.
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
     * checks if the input command file is valid, that there are commands in the command file
     * and that the input is in the right length.
     * @param commands - the list of commands.
     */
    public static void inputValidity(ArrayList<String> commands) {
        try {
            if (commands.isEmpty()) {
                throw new Exception();
            }
        } catch (Exception e) {
            printException(NO_COMMANDS);
        }
//        try {
//            if (commands.size() != optimized_INPUT) {
//                throw new Exception();
//            }
//        } catch (Exception e) {
//            printException(BAD_COMMANDS);
//        }
    }

    /**
     * takes the file directory we received and checks that it is a directory,
     * and puts all of the names of the files into an array list of file objects.
     * @param directoryPath - the String representing the directory we want.
     * @return the array list of file objects from the file directory.
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
     * this function will take the list of commands and will put it into blocks of two commands,
     * one filter command and one order command, so we will send these blocks to be executed.
     * @param commands - the list of commands.
     * @return an array list of command blocks.
     */
    private ArrayList<Block> splitToBlocks(ArrayList<String> commands) {
        ArrayList<Block> commandBlocks = new ArrayList<Block>();
        int commandsPointer = 0;
        while (commandsPointer < commands.size()) {
            int tempPointer = 0;
            while (tempPointer < 4) {
                Block tempBlock = new Block(4);
                if (commands.get(commandsPointer).equals("FILTER") && commands.get(commandsPointer + 1).equals("ORDER")) {
                    tempBlock.list[tempPointer] = DEFAULT_COMMAND;
                    if (commands.get(commandsPointer).equals("ORDER") && commands.get(commandsPointer + 1).equals("FILTER")) {
                        tempBlock.list[tempPointer] = DEFAULT_COMMAND;
                    } else {
                        tempBlock.list[tempPointer] = commands.get(commandsPointer);
                    }
                    tempPointer++;
                    commandsPointer++;
                    commandBlocks.add(tempBlock);
                }
            }
        }
        return commandBlocks;
    }

    private void doAction (ArrayList < Block > commandBlocks) {
        int i = 0;
        while (i < commandBlocks.size()) {
            Block tempBlock = commandBlocks.get(i);
            String filterParam = tempBlock.list[1];
            String orderParam = tempBlock.list[3];
            Filter filter = new Filter();
            Order order = new Order();
            Filter.FilterQ filterQ = new Filter.FilterQ(filterParam, filter);
            filterQ = Filter.filterBuilder(filterParam);
            Order.OrderQ orderQ = new Order.OrderQ(orderParam, order);
            orderQ = Order.orderBuilder(orderParam);

        }
    }
    
    /**
     *
     * @param args
     */
    // TODO: 5/31/18 Find a solution to the args issue, it doesnt fit with the ArrayList.
    public static void main (String[]args){
        DirectoryProcessor mainProccesor = new DirectoryProcessor();
        inputValidity(ArrayList < String > args);
    }
}
