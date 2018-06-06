package filesprocessing;// this are the java util functions we are going to use
import java.lang.*;
import java.io.*;
import java.util.ArrayList;

/**
 * the father class for all the filters we will want to use, extends the filesprocessing.DirectoryProcessor class
 */
public abstract class Filter {
    protected static final String NEGATIVE = "NOT";
    protected static final String REGULAR = "";


    public abstract void filterFiles(ArrayList<File> files, String filter, String negative) throws Exceptions.Type1Exception;

    protected void checkValidity(String value, String negative) throws Exceptions.Type1Exception{
        if (!negative.equals(REGULAR) && !negative.equals(NEGATIVE)){
            throw new Exceptions.NegativeSuffixEx();
        }
    }


    /**
     * this is the enum we are going to use once we call the class, comes instead of a switch case.
     * further more, helps us save memory space by not creating new objects every time.
     */
    public enum FilterQ {
        BETWEEN("between", new Between()),
        NO_BET("not between", new NotBetween()),
        GREATER_THAN("greater_than", new GreaterThan()),
        SMALLER_THAN("smaller_than", new SmallerThan()),
        NAME("file", new FileName()),
        CONTAINS("contains", new Contains()),
        PREFIX("prefix", new Prefix()),
        SUFFIX("suffix", new Suffix()),
        WRITABLE("writable", new Writable()),
        HIDDEN("hidden", new Hidden()),
        EXECUTABLE("executable", new Executable());

        // this are the two parameters we give the enum.
        protected final String fName;
        protected final Filter fObject;

        // the default constructor for our enum, with the string and the filesprocessing.Filter object.
        FilterQ(String filterName, Filter filterObject) {
            this.fName = filterName;
            this.fObject = filterObject;}


        // the getter func for the string representing the filesprocessing.Filter name.
        public String getfName() {
            return fName;
        }

        // the getter func for the object representing the filesprocessing.Filter we want.
        public Filter getfObject() {
            return fObject;
        }


    }

    /**
     * this will be the function that simply checks what filter we need,
     * and then builds the filter object we were asked for with the right method.
     * @param filter - a string representing the filter we are asked for
     * @return the FilterQ object, that holds the name of the filter and the filter object,
     * and which is going to be the enum.
     */
    protected static FilterQ filterBuilder(String filter) {
        if (filter.equals(FilterQ.BETWEEN.getfName())) {
            return FilterQ.BETWEEN;
        } else if (filter.equals(FilterQ.NO_BET.getfName())) {
            return FilterQ.NO_BET;
        } else if (filter.equals(FilterQ.GREATER_THAN.getfName())) {
            return FilterQ.GREATER_THAN;
        } else if (filter.equals(FilterQ.SMALLER_THAN.getfName())) {
            return FilterQ.SMALLER_THAN;
        } else if (filter.equals(FilterQ.NAME.getfName())) {
            return FilterQ.NAME;
        } else if (filter.equals(FilterQ.CONTAINS.getfName())) {
            return FilterQ.CONTAINS;
        } else if (filter.equals(FilterQ.PREFIX.getfName())) {
            return FilterQ.PREFIX;
        } else if (filter.equals(FilterQ.SUFFIX.getfName())) {
            return FilterQ.SUFFIX;
        } else if (filter.equals(FilterQ.WRITABLE.getfName())) {
            return FilterQ.WRITABLE;
        } else if (filter.equals(FilterQ.HIDDEN.getfName())) {
            return FilterQ.HIDDEN;
        } else if (filter.equals(FilterQ.EXECUTABLE.getfName())) {
            return FilterQ.EXECUTABLE;
        } else {
            return null;
        }
    }


    static boolean isNegative(String negative){
        return negative.equals(NEGATIVE); }

    public abstract class sizeHelper extends Filter{

        private static final float KB = 1024;

        protected void checkValidity(String filter, String negative) throws Exceptions.Type1Exception{
            super.checkValidity(filter, negative);
            try{
                double val = Double.parseDouble(filter);
                if (val< 0){
                    throw new Exceptions.InputValEx();
                }
            } catch (NumberFormatException e){
                throw new Exceptions.InputValEx();
            }
        }

        public void filterFiles(ArrayList<File> files, String filter, String negative) throws Exceptions.Type1Exception{
            checkValidity(filter, negative);
            double val = Double.parseDouble(filter);
            if (Filter.isNegative(negative)){
                files.removeIf(f -> (f.length()/ KB) > val);
            }
            else{
                files.removeIf(f -> (f.length()/ KB)< val);
            }
        }


    }


    /**
     * this will be our GreaterThan class that basically holds the greater_than func.
     */
    public class GreaterThan extends sizeHelper {
        public void filterFiles(ArrayList<File> files, String filter, String negative){
            super.filterFiles(files, filter, negative);
        }
    }

    /**
     * this will be our SmallerThan class that basically holds the smaller_than func.
     */
    protected class SmallerThan extends sizeHelper {
        public void filterFiles(ArrayList<File> files, String filter, String negative) throws Exceptions.Type1Exception{
            super.checkValidity(filter, negative);
            if (Filter.isNegative(negative)){
                super.filterFiles(files, filter, REGULAR);
            }else{
                super.filterFiles(files, filter, negative);
            }
        }
    }

    /**
     * this will be our Between class that basically holds the between func.
     */
    protected class Between extends sizeHelper {
        public void filterFiles(ArrayList<File> files, String first, String second){
            isRangeValid(first, second);
            super.filterFiles( files, first, REGULAR);
            super.filterFiles(files, second, NEGATIVE);

        }

        private void isRangeValid(String first, String second)throws Exceptions.Type1Exception {
            if (Double.parseDouble(first) > Double.parseDouble(second)){
                throw new Exceptions.RangeEx();
            }

        }
    }

    private class NotBetween extends Between {
        
    }
    /**
     * this will be our FileName class that basically holds the file func.
     */
    protected static class FileName extends Filter {
        protected boolean file(File file, String value) {
            return file.getName().equals(value);
        }
    }

    /**
     * this will be our Contains class that basically holds the contains func.
     */
    protected static class Contains extends Filter {
        protected boolean contains(File file, String value) {
            return file.getName().contains(value);
        }
    }

    /**
     * this will be our Prefix class that basically holds the prefix func.
     */
    protected static class Prefix extends Filter {
        protected boolean prefix(File file, String value) {
            return file.getName().startsWith(value);
        }
    }

    /**
     * this will be our Suffix class that basically holds the suffix func.
     */
    protected static class Suffix extends Filter {
        protected boolean suffix(File file, String value) {
            return file.getName().endsWith(value);
        }
    }

    /**
     * this will be our Writable class that basically holds the writable func.
     */
    protected static class Writable extends Filter {
        protected boolean writable(File file) {
            return file.canWrite();
        }
    }

    /**
     * this will be our Executable class that basically holds the executable func.
     */
    protected static class Executable extends Filter {
        protected boolean executable(File file) {
            return file.canExecute();
        }
    }

    /**
     * this will be our Hidden class that basically holds the hidden func.
     */
    protected static class Hidden extends Filter {
        protected boolean hidden(File file) {
            return file.isHidden();
        }
    }

    /**
     * this will be our All class that basically holds the all func.
     */
    protected static class All extends Filter {
        protected boolean all(File fatherFile) {
            return true;
        }
    }
}
