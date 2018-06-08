package filesprocessing;
// this are the java util functions we are going to use

import java.util.ArrayList;
import java.lang.*;
import java.io.*;
import java.util.Scanner;

/**
 * the father class for all the filters and orders we will want to use.
 */
public class DirectoryProcessor {

    /**
     * Our constant fields of this class, mostly exception prints.
     */
    private static final int OPTIMAL_INPUT = 2;
    private static final String DEFAULT_COMMAND = "";
    private final static int SOURCEDIR = 0;
    private final static int COMMAND_FILE = 1;
    private final static int BLOCK_SIZE = 4;
    private static final String FILTER = "FILTER";
    private static final String ORDER = "ORDER";


    /**
     * this func simply prints the exception we encountered.
     *
     * @param exceptionText - what we want to print.
     */


    public static void printException(String exceptionText) {
        System.err.println(exceptionText);
        System.exit(0);
    }


    /**
     * @param input
     */
    public static void inputValidity(String[] input) throws Exceptions.Type2Exception {
        if (input.length != OPTIMAL_INPUT) {
            throw new Exceptions.inputEX();
        }
    }

    /**
     * takes the file directory we received and checks that it is a directory,
     * and puts all of the names of the files into an array list of file objects.
     *
     * @param directoryPath - the String representing the directory we want.
     * @return the array list of file objects from the file directory.
     */
    public static ArrayList<File> directoryFileList(String directoryPath) throws Exceptions.Type2Exception {
        ArrayList<File> files = new ArrayList<File>();
        File fileDirectory = new File(directoryPath);
        if (!fileDirectory.isDirectory() || fileDirectory.length() == 0) {
            throw new Exceptions.SourcedirEx();
        }
        for (File file : fileDirectory.listFiles()) {
            if (file.isFile()) {
                files.add(file);
            }
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
    private static ArrayList<String> commandsList(String path) throws Exceptions.Type2Exception {
        ArrayList<String> commands = new ArrayList<String>();
        File file = new File(path);
        try {
            if (!file.isFile()) {
                throw new FileNotFoundException();
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                commands.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new Exceptions.FileEx();
        }
        return commands;
    }

    /**
     * this func takes the list of commands and separates it into blocks which will be easier to use.
     *
     * @param commands        - the list of all the commands in the file
     * @param data            - the string list we will set as our new commands, more organized
     * @param commandsPointer - the pointer
     * @return the pointer
     */
    private static int splitToBlocks(ArrayList<String> commands, String[] data, int commandsPointer) {
        int i = 0;
        while (i < BLOCK_SIZE && commandsPointer < commands.size()) {
            if (isDefaultOrder(commands, data, commandsPointer, i)) {
                break;
            }
            data[i] = commands.get(i);
            i++;
            commandsPointer++;
        }
        if (i == BLOCK_SIZE - 1) {
            data[i] = DEFAULT_COMMAND;
        }
        return commandsPointer;
    }

    /**
     * this func makes sure we put the right order, the default order, if the next thing after the
     * ORDER command is a FILTER command.
     *
     * @param commands        - the list of all the commands in the file
     * @param data            - the string list we will set as our new commands, more organized
     * @param commandsPointer - the pointer
     * @param i               - another pointer
     * @return true if we modified the ORDER command to be the default order command, false if not.
     */
    private static boolean isDefaultOrder(ArrayList<String> commands, String[] data, int commandsPointer, int i) {
        if (i == BLOCK_SIZE - 1) {
            if (commands.get(commandsPointer).equals(FILTER)) {
                data[BLOCK_SIZE - 1] = DEFAULT_COMMAND;
                return true;
            }
        }
        return false;
    }

    /**
     * this func checks to make sure we are able to add our new block to the list of blocks we have,
     * and if we are able we add the new block to the list.
     *
     * @param blocks -  the list of blocks
     * @param data   - the string list we will set as our new commands, more organized
     * @param idx    - the pointer
     * @throws Exceptions.Type2Exception
     */
    private static void blocksCreator(ArrayList<Block> blocks, String[] data, int idx) throws Exceptions.Type2Exception {
        blocks.add(new Block(idx, data));
    }

    /**
     * this is the func that builds the block list for our usage.
     *
     * @param commands - the list of all the commands in the file
     * @return the list of blocks, after modification.
     * @throws Exceptions.Type2Exception
     */
    private static ArrayList<Block> blocksArray(ArrayList<String> commands) throws Exceptions.Type2Exception {
        int i = 0;
        int j = 0;
        ArrayList<Block> blocks = new ArrayList<>();
        String[] data;
        while (i < commands.size()) {
            data = new String[BLOCK_SIZE];
            j = splitToBlocks(commands, data, i);
            blocksCreator(blocks, data, i);
            i = j;
        }
        return blocks;
    }

    /**
     * the main func that runs once you ask it to.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            inputValidity(args);
            ArrayList<String> commands = commandsList(args[COMMAND_FILE]);
            ArrayList<Block> blocks = blocksArray(commands);
            ArrayList<File> dirFiles;
            for (Block block : blocks) {
                dirFiles = directoryFileList(args[SOURCEDIR]);
                block.doAction(dirFiles);
            }
        } catch (Exceptions.Type2Exception e) {
            System.err.println(e.getMessage());
            return;
        }
    }
}


