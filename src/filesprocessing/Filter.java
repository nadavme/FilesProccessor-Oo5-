package filesprocessing;// this are the java util functions we are going to use
import java.lang.*;
import java.io.*;

/**
 * the father class for all the filters we will want to use, extends the filesprocessing.DirectoryProcessor class
 */
public class Filter extends DirectoryProcessor {

    /**
     * the default constructor
     */
    public Filter() {
        Filter filter = new Filter();
    }

    /**
     * this is the enum we are going to use once we call the class, comes instead of a switch case.
     * further more, helps us save memory space by not creating new objects every time.
     */
    public enum FilterQ {
        BETWEEN("between", new Between()),
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
            if (filterName == null || filterObject == null) {
                this.fName = "temp";
                this.fObject = null;
            } else {
                this.fName = filterName;
                this.fObject = filterObject;
            }
        }

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
    public static FilterQ filterBuilder(String filter) {
        if (filter.equals(FilterQ.BETWEEN.getfName())) {
            return FilterQ.BETWEEN;
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

    /**
     * this will be our GreaterThan class that basically holds the greater_than func.
     */
    public static class GreaterThan extends Filter {
        protected boolean greater_than(File file, double Bytes) {
            return ((double) (file.length() / 1024) > Bytes);
        }
    }

    /**
     * this will be our SmallerThan class that basically holds the smaller_than func.
     */
    protected static class SmallerThan extends Filter {
        protected boolean smaller_than(File file, double Bytes) {
            return ((double) (file.length() / 1024) < Bytes);
        }
    }

    /**
     * this will be our Between class that basically holds the between func.
     */
    protected static class Between extends Filter {
        protected boolean between(File file, double upperBytes, double lowerBytes) {
            return ((double) (file.length() / 1024) >= lowerBytes && (double) (file.length() / 1024) <= upperBytes);
        }
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
