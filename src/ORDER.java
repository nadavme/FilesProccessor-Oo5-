import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.*;
import java.text.*;
import java.lang.*;
import java.io.*;

public class ORDER extends DirectoryProcessor {

    private static final String REVERSE = "REVERSE";
    private static final String REGULAR = "";

    private ArrayList<File> fileArray;

    private void CheckValidity(String reversed){
        if (!reversed.equals(REVERSE) && !reversed.equals(REGULAR)){
            throw new UnsupportedOperationException();
        }
    }

    private ArrayList<File> isReversed(ArrayList<File> files, String  reversed){
        if (reversed.equals(REVERSE)){
            Collections.reverse(files);
        }
        return files;
    }

    protected class Abs{

        private ArrayList<File> AbsOrder(ArrayList<File> files, String  reversed){
            CheckValidity(reversed);
            Collections.sort(files);
            return isReversed(files, reversed);
        }
    }

    protected class Size{

        private ArrayList<File> SizeOrder(ArrayList<File> files, String reversed){
            CheckValidity(reversed);
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    Long file1Size = file1.length();
                    return file1Size.compareTo(file2.length());
                }
            });
            return isReversed(files, reversed);
        }
    }

    protected class Type{

        private ArrayList<File> TypeOrder(ArrayList<File> files, String reversed){
            CheckValidity(reversed);
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    int file1Seperator = file1.getName().lastIndexOf('.');
                    String file1Suffix = file1.getName().slc(file1Seperator+1 , file1.getName().length());

                }
            });
            return isReversed(files, reversed);
        }
    }
}
