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

    protected void checkValidity(String value, String negative) throws Exceptions.Type1Exception {
        if (!negative.equals(REGULAR) && !negative.equals(NEGATIVE)) {
            throw new Exceptions.NegativeSuffixEx();
        }
    }


    /**
     * this is the enum we are going to use once we call the class, comes instead of a switch case.
     * further more, helps us save memory space by not creating new objects every time.
     */
    enum FilterQ {
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
            this.fObject = filterObject;
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
     *
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


    static boolean isNegative(String negative) {
        return negative.equals(NEGATIVE);
    }

    public static abstract class sizeHelper extends Filter {

        protected static final float KB = 1024;

        /**
         * @param filter
         * @param negative
         * @throws Exceptions.Type1Exception
         */
        protected void checkValidity(String filter, String negative) throws Exceptions.Type1Exception {
            super.checkValidity(filter, negative);
            try {
                double val = Double.parseDouble(filter);
                if (val < 0) {
                    throw new Exceptions.InputValEx();
                }
            } catch (NumberFormatException e) {
                throw new Exceptions.InputValEx();
            }
        }

        /**
         * @param files
         * @param filter
         * @param negative
         * @throws Exceptions.Type1Exception
         */
        public void filterFiles(ArrayList<File> files, String filter, String negative) throws Exceptions.Type1Exception {
            checkValidity(filter, negative);
            double val = Double.parseDouble(filter);
            if (Filter.isNegative(negative)) {
                files.removeIf(f -> (f.length() / KB) > val);
            } else {
                files.removeIf(f -> (f.length() / KB) < val);
            }
        }


    }


    /**
     * this will be our GreaterThan class that basically holds the greater_than func.
     */
    public static class GreaterThan extends sizeHelper {
        public void filterFiles(ArrayList<File> files, String filter, String negative) {
            super.filterFiles(files, filter, negative);
        }
    }

    /**
     * this will be our SmallerThan class that basically holds the smaller_than func.
     */
    protected static class SmallerThan extends sizeHelper {
        public void filterFiles(ArrayList<File> files, String filter, String negative) throws Exceptions.Type1Exception {
            super.checkValidity(filter, negative);
            if (Filter.isNegative(negative)) {
                super.filterFiles(files, filter, REGULAR);
            } else {
                super.filterFiles(files, filter, negative);
            }
        }
    }

    /**
     * this will be our Between class that basically holds the between func.
     */
    protected static class Between extends sizeHelper {
        public void filterFiles(ArrayList<File> files, String first, String second) {
            isRangeValid(first, second);
            super.filterFiles(files, first, REGULAR);
            super.filterFiles(files, second, NEGATIVE);

        }

        private void isRangeValid(String first, String second) throws Exceptions.Type1Exception {
            if (Double.parseDouble(first) > Double.parseDouble(second)) {
                throw new Exceptions.RangeEx();
            }

        }
    }

    // TODO: 6/6/18 u need a better class name than "not between"
    private static class NotBetween extends Between {
        /**
         * @param files
         * @param first
         * @param second
         */
        public void filterFiles(ArrayList<File> files, String first, String second) {
            super.checkValidity(first, REGULAR);
            super.checkValidity(second, REGULAR);
            super.isRangeValid(first, second);
            double smaller = Double.parseDouble(first);
            double greater = Double.parseDouble(second);
            files.removeIf(f -> ((f.length() / KB) >= smaller && (f.length() / KB) <= greater));
        }


    }

    /**
     *
     */
    public static abstract class stringHelper extends Filter {
        /**
         * @param files
         * @param filter
         * @param negative
         * @throws Exceptions.Type1Exception
         */
        @Override
        public void filterFiles(ArrayList<File> files, String filter, String negative) throws Exceptions.Type1Exception {
            checkValidity(filter, negative);
            filterize(files, filter, isNegative(negative));
        }

        /**
         *
         */
        protected abstract void filterize(ArrayList<File> files, String filter, boolean negative);
    }

    /**
     *
     */
    protected static class FileName extends stringHelper {
        /**
         * @param files
         * @param filter
         * @param negative
         */
        @Override
        protected void filterize(ArrayList<File> files, String filter, boolean negative) {
            if (!negative) {
                files.removeIf(f -> f.getName().equals(filter));
            } else {
                files.removeIf(f -> f.getName().equals(filter));
            }
        }
    }

//        protected boolean file(File file, String value) {
//            return file.getName().equals(value);
//        }
//    }

    /**
     *
     */
    protected static class Contains extends stringHelper {

        /**
         * @param files
         * @param filter
         * @param negative
         */
        @Override
        protected void filterize(ArrayList<File> files, String filter, boolean negative) {
            if (!negative) {
                files.removeIf(f -> !f.getName().contains(filter));
            } else {
                files.removeIf(f -> f.getName().contains(filter));
            }
        }
    }
//    }
//        protected boolean contains(File file, String value) {
//            return file.getName().contains(value);
//        }
//    }

    /**
     *
     */
    protected static class Prefix extends stringHelper {
        /**
         * @param files
         * @param filter
         * @param negative
         */
        @Override
        protected void filterize(ArrayList<File> files, String filter, boolean negative) {
            if (!negative) {
                files.removeIf(f -> !f.getName().startsWith(filter));
            } else {
                files.removeIf(f -> f.getName().startsWith(filter));
            }
        }
    }
//    }
//        protected boolean prefix(File file, String value) {
//            return file.getName().startsWith(value);
//        }
//    }

    /**
     * this will be our Suffix class that basically holds the suffix func.
     */
    protected static class Suffix extends stringHelper {
        /**
         * @param files
         * @param filter
         * @param negative
         */
        @Override
        protected void filterize(ArrayList<File> files, String filter, boolean negative) {
            if (!negative) {
                files.removeIf(f -> !f.getName().endsWith(filter));
            } else {
                files.removeIf(f -> f.getName().endsWith(filter));
            }

        }
    }

//    }
//        protected boolean suffix(File file, String value) {
//            return file.getName().endsWith(value);
//        }
//    }


    public static abstract class permissionHelper extends Filter {
        /**
         * @param value
         * @param negative
         * @throws Exceptions.Type1Exception
         */
        @Override
        protected void checkValidity(String value, String negative) throws Exceptions.Type1Exception {
            super.checkValidity(value, negative);
            if (!value.equals("YES") && !value.equals("NO")) {
                throw new Exceptions.NoPermissionEx();
            }
        }

        /**
         * @param permit
         * @return
         */
        private boolean isPermitted(String permit) {
            return permit.equals("YES");
        }

        /**
         * @param files
         * @param permit
         * @param negative
         */
        protected abstract void filterize(ArrayList<File> files, boolean permit, boolean negative);

        /**
         * @param files
         * @param filter
         * @param negative
         * @throws Exceptions.Type1Exception
         */
        public void filterFiles(ArrayList<File> files, String filter, String negative) throws
                Exceptions.Type1Exception {
            checkValidity(filter, negative);
            filterize(files, isPermitted(filter), isNegative(negative));
        }
    }


    protected static class Writable extends permissionHelper {

        @Override
        protected void filterize(ArrayList<File> files, boolean permit, boolean negative) {
            if ((permit && !negative) || (!permit && negative)) {
                files.removeIf(f -> !f.canWrite());
            } else {
                files.removeIf(f -> f.canWrite());
            }
        }
    }

//        protected boolean writable(File file) {
//            return file.canWrite();
//        }


    /**
     *
     */
    protected static class Executable extends permissionHelper {
        /**
         * @param files
         * @param permit
         * @param negative
         */
        @Override
        protected void filterize(ArrayList<File> files, boolean permit, boolean negative) {
            if ((permit && !negative) || (!permit && negative)) {
                files.removeIf(f -> !f.canExecute());
            } else {
                files.removeIf(f -> f.canExecute());
            }
        }
    }

//        protected boolean executable(File file) {
//            return file.canExecute();
//        }
//    }

    /**
     * this will be our Hidden class that basically holds the hidden func.
     */
    protected static class Hidden extends permissionHelper {
        @Override
        protected void filterize(ArrayList<File> files, boolean permit, boolean negative) {
            if ((permit && !negative) || (!permit && negative)) {
                files.removeIf(f -> !f.isHidden());
            } else {
                files.removeIf(f -> f.isHidden());
            }
        }
    }
}



