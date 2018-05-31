import java.lang.*;
import java.io.*;

/**
 *
 */
public class Filter extends DirectoryProcessor {

    /**
     *
     */
    public Filter() {
        Filter filter = new Filter();
    }

    /**
     *
     */
    public enum FilterQ {
        // size filter group 1-3
        BETWEEN("between", new Between()),
        GREATER_THAN("greater_than", new GreaterThan()),
        SMALLER_THAN("smaller_than", new SmallerThan()),

        // name filter  group 4-7
        NAME("file", new FileName()),
        CONTAINS("contains", new Contains()),
        PREFIX("prefix", new Prefix()),
        SUFFIX("suffix", new Suffix()),

        // permission filter group 8-10
        WRITABLE("writable", new Writable()),
        HIDDEN("hidden", new Hidden()),
        EXECUTABLE("executable", new Executable());

        FilterQ(String orderName, Filter filterObject) {
            this.fName = orderName;
            this.fObject = filterObject;
        }

        final String fName;

        final Filter fObject;

        public String getfName() {
            return fName;
        }

        public Filter getfObject() {
            return fObject;
        }

    }

    /**
     *
     * @param filter
     * @return
     */
    public FilterQ filterFactory(String filter) {
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
     *
     */
    public static class GreaterThan extends Filter {
        private boolean greater_than(File file, double Bytes) {
            return ((double) (file.length() / 1024) > Bytes);
        }
    }

    /**
     *
     */
    protected static class SmallerThan extends Filter {
        private boolean smaller_than(File file, double Bytes) {
            return ((double) (file.length() / 1024) < Bytes);
        }
    }

    /**
     *
     */
    protected static class Between extends Filter {
        private boolean between(File file, double upperBytes, double lowerBytes) {
            return ((double) (file.length() / 1024) >= lowerBytes && (double) (file.length() / 1024) <= upperBytes);
        }
    }

    /**
     *
     */
    protected static class FileName extends Filter {
        private boolean file(File file, String value) {
            return file.getName().equals(value);
        }
    }

    /**
     *
     */
    protected static class Contains extends Filter {
        private boolean contains(File file, String value) {
            return file.getName().contains(value);
        }
    }

    /**
     *
     */
    protected static class Prefix extends Filter {
        private boolean prefix(File file, String value) {
            return file.getName().startsWith(value);
        }
    }

    /**
     *
     */
    protected static class Suffix extends Filter {
        private boolean suffix(File file, String value) {
            return file.getName().endsWith(value);
        }
    }

    /**
     *
     */
    protected static class Writable extends Filter {
        private boolean writable(File file) {
            return file.canWrite();
        }
    }

    /**
     *
     */
    protected static class Executable extends Filter {
        private boolean executable(File file) {
            return file.canExecute();
        }
    }

    /**
     *
     */
    protected static class Hidden extends Filter {
        private boolean hidden(File file) {
            return file.isHidden();
        }
    }

    /**
     * 
     */
    protected static class All extends Filter {
        private boolean all(File fatherFile) {
            return true;
        }
    }
}
