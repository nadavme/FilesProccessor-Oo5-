package filesprocessing;
// this are the java util functions we are going to use

import java.util.ArrayList;
import java.lang.*;
import java.io.*;

/**
 * the father class for all the filters and orders we will want to use.
 */
public class DirectoryProcessor {

    /**
     * Our constant fields of this class, mostly exception prints.
     */
    private static final int optimized_INPUT = 2;
    private static final String DEFAULT_COMMAND = "";
    private final static int SOURCEDIR = 0;
    private final static int COMMAND_FILE = 1;
    private final static int BLOCK_SIZE = 4;
    private static final String FILTER = "FILTER";
    private static final String ORDER = "ORDER" ;



    /**
     * *****************************Error messages***********************************************
     */
    private static final String BAD_COMMAND_FILE_ERROR = "ERROR: The command file doesn't " +
                                                         "exist or it's a directory";
    private static final String BAD_FORMAT_ERROR = "ERROR: Problem with format of Commands File";
    private static final String BAD_SUBSECTION_ERROR = "ERROR: Problem with subsection name";
    private static final String BAD_INPUT_ERROR = "ERROR: Wrong usage.Should recive 2 arguments";
    private static final String BAD_COMMANDS = "invalid command input! there should be ONLY two commands.";
    private final static String BAD_SOURCEDIR_ERROR = "ERROR: No files in sourcedir";


    /**
     * this func simply prints the exception we encountered.
     *
     * @param exeptionText - what we want to print.
     */

    protected class handleData(){

    }
    public static void printException(String exeptionText) {
        System.out.println(exeptionText);
        System.exit(0);
    }


    /**
     * checks if the input command file is valid, that there are commands in the command file
     * and that the input is in the right length.
     *
     * @param commands - the list of commands.
     */
    public static void inputValidity(String[] input) {
            if (input.length != 2){
                System.out.println(BAD_INPUT_ERROR);
                System.exit(0);
        }




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
     *
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
            printException(BAD_SOURCEDIR_ERROR);
        }
        return files;
    }

    /**
     * this func takes the file that has the commands and separates each line and
     * putts all of the lines/commands in an array list of strings.
     *
     * @param path - the path we receive for the command file.
     * @return the array list of commands.
     */
    public ArrayList<String> CommandsList(String path) throws Exception {
        ArrayList<String> commands = new ArrayList<String>();
        File file = new File(path);
        try {
            if (!file.isFile() || !file.exists()) {
                throw new FileNotFoundException();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                commands.add(line);
                line = reader.readLine();}
            reader.close();
            return commands;
        } catch (FileNotFoundException e) {
            printException(BAD_COMMAND_FILE_ERROR);
        }
        return commands;

    /**
     * this function will take the list of commands and will put it into blocks of two commands,
     * one filter command and one order command, so we will send these blocks to be executed.
     *
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
                if (commands.get(commandsPointer).equals(FILTER) && commands.get(commandsPointer + 1).equals("ORDER")) {
                    tempBlock.list[tempPointer] = DEFAULT_COMMAND;
                    if (commands.get(commandsPointer).equals(ORDER) && commands.get(commandsPointer + 1).equals("FILTER")) {
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

    /**
     * @param args
     */
    // TODO: 5/31/18 Find a solution to the args issue, it doesnt fit with the ArrayList.
    public static void main(String[] args) {
        DirectoryProcessor mainProccesor = new DirectoryProcessor();
        inputValidity(ArrayList < String > args);
    }
}
