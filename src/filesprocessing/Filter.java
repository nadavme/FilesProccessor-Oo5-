package filesprocessing;// this are the java util functions we are going to use

import java.lang.*;
import java.io.*;
import java.util.ArrayList;

/**
 * the father class for all the filters we will want to use, extends the filesprocessing.DirectoryProcessor class
 */
public abstract class Filter {
    /**
     * this are the permanent parameters for our class
     */
    protected static final String NEGATIVE = "NOT";
    protected static final String REGULAR = "";

    public abstract void filterFiles(ArrayList<File> files, String filter, String negative) throws Exceptions.Type1Exception;

    /**
     * the func that check the validity of the command we are about to execute
     *
     * @param value    - this will be needed when we use this func as super
     * @param negative - the part of the func that indicates if the command is going to be negative
     * @throws Exceptions.Type1Exception
     */
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

    /**
     * the func that checks if the command is negative or not
     *
     * @param negative - the negative value of the command
     * @return true if negative indicates that the command is negative, return false if not
     */
    static boolean isNegative(String negative) {
        return negative.equals(NEGATIVE);
    }

    /**
     * the abstract class that helps us with the size of the file,
     * using a general suitable function that does the filter.
     */
    public static abstract class sizeHelper extends Filter {
        // a simple parameter for our usage
        protected static final float KB = 1024;

        /**
         * a super func that uses check validity and adds to it to check the whole command line
         *
         * @param filter   - the name of the filter command to execute
         * @param negative - the part that indicates if the command is negative or not
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
         * this func checks what files are suitable for the filter and filters the array list of files.
         *
         * @param files    - our array list of files
         * @param filter   - the name of our filter command
         * @param negative - the part that indicates if the command is negative or not
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
     * this will be our GreaterThan class that basically holds the suitable filterFiles func.
     */
    public static class GreaterThan extends sizeHelper {
        public void filterFiles(ArrayList<File> files, String filter, String negative) {
            super.checkValidity(filter, negative);
            if (Filter.isNegative(negative)) {
                super.filterFiles(files, filter, REGULAR);
            } else {
                super.filterFiles(files, filter, negative);
            }
        }
    }

    /**
     * this will be our SmallerThan class that basically holds the suitable filterFiles func.
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
     * this will be our Between class that basically holds the suitable filterFiles func.
     */
    protected static class Between extends sizeHelper {
        public void filterFiles(ArrayList<File> files, String first, String second) {
            isRangeValid(first, second);
            super.filterFiles(files, first, REGULAR);
            super.filterFiles(files, second, NEGATIVE);
        }

        // a helper func to make sure the range for the between filter is valid
        private void isRangeValid(String first, String second) throws Exceptions.Type1Exception {
            if (Double.parseDouble(first) > Double.parseDouble(second)) {
                throw new Exceptions.RangeEx();
            }
        }
    }

    /**
     * this will be our negative Between class that basically holds the suitable filterFiles func.
     */
    private static class NotBetween extends Between {
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
     * this is the abstract class that helps us check the filter and 'filterize' the files array list,
     * using a general suitable function that does the filter.
     */
    public static abstract class stringHelper extends Filter {
        @Override
        public void filterFiles(ArrayList<File> files, String filter, String negative) throws Exceptions.Type1Exception {
            checkValidity(filter, negative);
            // see filterize function below
            filterize(files, filter, isNegative(negative));
        }

        /**
         * we declare the func for later use
         */
        protected abstract void filterize(ArrayList<File> files, String filter, boolean negative);
    }

    /**
     * this is a class that extends the stringHelper class so we will be able to call the function
     * filterize with the right filter and check to see if it's negative or not,
     * using a general suitable function that does the filter.
     */
    protected static class FileName extends stringHelper {
        /**
         * this func checks if the files are suitable with the given filter, and if
         * they are not suitable we remove them.
         *
         * @param files    - the array list of files
         * @param filter   - the filter we want to execute
         * @param negative - the negative or not parameter
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

    /**
     * this will be our Contains class that basically holds the filterize func suitable for contain check.
     */
    protected static class Contains extends stringHelper {
        /**
         * this func checks if the files are suitable with the given filter, and if
         * they are not suitable we remove them.
         *
         * @param files    - the array list of files
         * @param filter   - the filter we want to execute
         * @param negative - the negative or not parameter
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

    /**
     * this will be our Prefix class that basically holds the filterize func suitable for contain check.
     */
    protected static class Prefix extends stringHelper {
        /**
         * this func checks if the files are suitable with the given filter, and if
         * they are not suitable we remove them.
         *
         * @param files    - the array list of files
         * @param filter   - the filter we want to execute
         * @param negative - the negative or not parameter
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

    /**
     * this will be our Suffix class that basically holds the filterize func suitable for contain check.
     */
    protected static class Suffix extends stringHelper {
        /**
         * this func checks if the files are suitable with the given filter, and if
         * they are not suitable we remove them.
         *
         * @param files    - the array list of files
         * @param filter   - the filter we want to execute
         * @param negative - the negative or not parameter
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

    /**
     * this is the abstract class that helps us check the filter and 'filterize' the files array list
     * using a general suitable function that does the filter.
     */
    public static abstract class permissionHelper extends Filter {
        /**
         * the new check validity func suitable to the specific filters.
         *
         * @param value    - the string representing the filter name
         * @param negative - the string representing if the filter is negative or not
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
         * a general func that will check if we want the permitted files or not
         *
         * @param permit - a boolean reference for the permitted or not parameter
         * @return true if permitted, false if not
         */
        private boolean isPermitted(String permit) {
            return permit.equals("YES");
        }

        /**
         * this func checks if the files are suitable with the given filter, and if
         * they are not suitable we remove them.
         *
         * @param files    - the array list of files
         * @param permit   - the permission filter we want to execute
         * @param negative - the negative or not parameter
         */
        protected abstract void filterize(ArrayList<File> files, boolean permit, boolean negative);

        /**
         * this func checks what files are suitable for the filter and filters the array list of files.
         *
         * @param files    - the array list of files
         * @param filter   - the filter to execute
         * @param negative - the negative or not parameter
         * @throws Exceptions.Type1Exception
         */
        public void filterFiles(ArrayList<File> files, String filter, String negative) throws
                Exceptions.Type1Exception {
            checkValidity(filter, negative);
            filterize(files, isPermitted(filter), isNegative(negative));
        }
    }

    /**
     * this will be our Writable class that basically holds the filterize func suitable for contain check.
     */
    protected static class Writable extends permissionHelper {
        /**
         * this func checks if the files are suitable with the given filter, and if
         * they are not suitable we remove them.
         *
         * @param files    - the array list of files
         * @param permit   - the permission filter we want to execute
         * @param negative - the negative or not parameter
         */
        @Override
        protected void filterize(ArrayList<File> files, boolean permit, boolean negative) {
            if ((permit && !negative) || (!permit && negative)) {
                files.removeIf(f -> !f.canWrite());
            } else {
                files.removeIf(f -> f.canWrite());
            }
        }
    }

    /**
     * this will be our Executable class that basically holds the filterize func suitable for contain check.
     */
    protected static class Executable extends permissionHelper {
        /**
         * this func checks if the files are suitable with the given filter, and if
         * they are not suitable we remove them.
         *
         * @param files    - the array list of files
         * @param permit   - the permission filter we want to execute
         * @param negative - the negative or not parameter
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

    /**
     * this will be our Hidden class that basically holds the filterize func suitable for contain check.
     */
    protected static class Hidden extends permissionHelper {
        /**
         * this func checks if the files are suitable with the given filter, and if
         * they are not suitable we remove them.
         *
         * @param files    - the array list of files
         * @param permit   - the permission filter we want to execute
         * @param negative - the negative or not parameter
         */
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



