import java.lang.*;
import java.io.*;

public class Filter extends DirectoryProcessor {

    public enum FilterCase {
        // size filter group 1-3
        BETWEEN("between", new Between()),
        GREATER_THAN("greater_than", new GreaterThan()),
        SMALLER_THAN("smaller_than", new SmallerThan()),

        // name filter  group 4-7
        NAME("file", new FileName()),
        CONTAINS("contains", new Contains()),
        PREFIX("prefix", new Prefix()),
        SUFFIX("suffix", new Contains()),

        // permission filter group 8-10
        WRITABLE("writable", new Writable()),
        HIDDEN("hidden", new Hidden()),
        EXECUTABLE("executable", new Executable());

        FilterCase(String orderName, Filter filterObject) {
            this.filterName = orderName;
            this.filterObject = filterObject;
        }

        final String filterName;

        final Filter filterObject;

        public String getFilterName() {
            return filterName;
        }

        public Filter getFilterObject() {
            return filterObject;
        }
    }

    public FilterCase filterFactory(String filter) {
        if (filter.equals(FilterCase.BETWEEN.getFilterName())) {
            return FilterCase.BETWEEN;
        } else if (filter.equals(FilterCase.GREATER_THAN.getFilterName())) {
            return FilterCase.GREATER_THAN;
        } else if (filter.equals(FilterCase.SMALLER_THAN.getFilterName())) {
            return FilterCase.SMALLER_THAN;
        } else if (filter.equals(FilterCase.NAME.getFilterName())) {
            return FilterCase.NAME;
        } else if (filter.equals(FilterCase.CONTAINS.getFilterName())) {
            return FilterCase.CONTAINS;
        } else if (filter.equals(FilterCase.PREFIX.getFilterName())) {
            return FilterCase.PREFIX;
        } else if (filter.equals(FilterCase.SUFFIX.getFilterName())) {
            return FilterCase.SUFFIX;
        } else if (filter.equals(FilterCase.WRITABLE.getFilterName())) {
            return FilterCase.WRITABLE;
        } else if (filter.equals(FilterCase.HIDDEN.getFilterName())) {
            return FilterCase.HIDDEN;
        } else if (filter.equals(FilterCase.EXECUTABLE.getFilterName())) {
            return FilterCase.EXECUTABLE;
        } else {
            return null;
        }
    }

    public Filter() {
        Filter filter = new Filter();
    }

    protected class GreaterThan {
        private boolean greater_than(File file, double Bytes) {
            return ((double) (file.length() / 1024) > Bytes);
        }
    }

    protected class SmallerThan {
        private boolean smaller_than(File file, double Bytes) {
            return ((double) (file.length() / 1024) < Bytes);
        }
    }

    protected class Between {
        private boolean between(File file, double upperBytes, double lowerBytes) {
            return ((double) (file.length() / 1024) >= lowerBytes && (double) (file.length() / 1024) <= upperBytes);
        }
    }

    protected class FileName {
        private boolean file(File file, String value) {
            return file.getName().equals(value);
        }
    }

    protected class Contains {
        private boolean contains(File file, String value) {
            return file.getName().contains(value);
        }
    }

    protected class Prefix {
        private boolean prefix(File file, String value) {
            return file.getName().startsWith(value);
        }
    }

    protected class Suffix {
        private boolean suffix(File file, String value) {
            return file.getName().endsWith(value);
        }
    }

    protected class Writable {
        private boolean writable(File file) {
            return file.canWrite();
        }
    }

    protected class Executable {
        private boolean executable(File file) {
            return file.canExecute();
        }
    }

    protected class Hidden {
        private boolean hidden(File file) {
            return file.isHidden();
        }
    }

    protected class All {
        private boolean all(File fatherFile) {
            return true;
        }
    }
}
