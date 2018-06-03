// this are the java util functions we are going to use
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.*;
import java.io.*;

/**
 * the father class for all the orders we will want to use, extends the DirectoryProcessor class
 */
public class Order extends DirectoryProcessor {

    /**
     * this are the permanent parameters for our class
     */
    private static final String REVERSE = "REVERSE";
    private static final String REGULAR = "";
    private static final String SEPARATOR = ".";

    /**
     * the default constructor
     */
    public Order() {
        Order order = new Order();
    }

    /**
     * this is the enum we are going to use once we call the class, comes instead of a switch case.
     * further more, helps us save memory space by not creating new objects every time.
     */
    public enum OrderQ {
        // name for Order enums that will hold the Order objects.
        ABS("abs", new Abs()),
        TYPE("type", new Type()),
        SIZE("size", new Size()),
        DEFAULT_ORDER("default", new Abs());

        // this are the two parameters we give the enum.
        private final String OName;
        private final Order OObject;

        // the default constructor for our enum, with the string and the Order object.
        OrderQ(String orderName, Order orderObject) {
            this.OName = orderName;
            this.OObject = orderObject;
        }

        // the getter func for the string representing the Order name.
        public String getOName() {
            return OName;
        }

        // the getter func for the object representing the Order we want.
        public Order getOObject() {
            return OObject;
        }
    }

    /**
     * this will be the function that simply checks what order we need,
     * and then builds the order object we were asked for with the right method.
     * @param order - a string representing the order we are asked for
     * @return the OrderQ object, that holds the name of the order and the order object,
     * and which is going to be the enum.
     */
    public OrderQ orderBuilder(String order) {
        if (order.equals(OrderQ.ABS.getOName())) {
            return OrderQ.ABS;
        } else if (order.equals(OrderQ.TYPE.getOName())) {
            return OrderQ.TYPE;
        } else if (order.equals(OrderQ.SIZE.getOName())) {
            return OrderQ.SIZE;
        } else {
            return OrderQ.DEFAULT_ORDER;
        }
    }

    /**
     * this function simply checks that the 'reversed' parameter is valid,
     * has only one of two values possible.
     * @param reversed - the parameter that indicates if we should reverse the order.
     */
    private static void CheckValidity(String reversed) {
        if (!reversed.equals(REVERSE) && !reversed.equals(REGULAR)) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * the func that gives us the suffix of the file.
     * @param file - the file object we are examining.
     * @return the suffix of the file.
     */
    private static String fileSuffix(File file) {
        if (!file.getName().contains(SEPARATOR)) {
            return REGULAR;
        }
        int file1Separator = file.getName().lastIndexOf(SEPARATOR);
        return file.getName().substring(file1Separator);
    }

    /**
     * this func checks if the reversed parameter indicates that the order should be reversed,
     * and if it does indicates that it reverses the order.
     * @param files - the array list of files we want to order.
     * @param reversed - the reversed parameter.
     * @return the files ordered.
     */
    private static ArrayList<File> isReversed(ArrayList<File> files, String reversed) {
        if (reversed.equals(REVERSE)) {
            Collections.reverse(files);
        }
        return files;
    }

    /**
     * this will be our Abs class that basically holds the func for the abs order.
     */
    protected static class Abs extends Order {

        /**
         * the function that organizes the files by abs order.
         * @param files - the array list of files
         * @param reversed - the reversed parameter.
         * @return the files ordered.
         */
        private ArrayList<File> AbsOrder(ArrayList<File> files, String reversed) {
            CheckValidity(reversed);
            Collections.sort(files);
            return isReversed(files, reversed);
        }
    }

    /**
     * this will be our size class that basically holds the func for the size order.
     */
    protected static class Size extends Order {

        /**
         * the function that organizes the files by size order.
         * @param files - the array list of files
         * @param reversed - the reversed parameter.
         * @return the files ordered.
         */
        private ArrayList<File> SizeOrder(ArrayList<File> files, String reversed) {
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

    /**
     * this will be our type class that basically holds the func for the type order.
     */
    protected static class Type extends Order {

        /**
         * the function that organizes the files by type order.
         * @param files - the array list of files
         * @param reversed - the reversed parameter.
         * @return the files ordered.
         */
        private ArrayList<File> TypeOrder(ArrayList<File> files, String reversed) {
            CheckValidity(reversed);
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    String file1Suffix = fileSuffix(file1);
                    String file2Suffix = fileSuffix(file2);
                    return file1Suffix.compareTo(file2Suffix);
                }
            });
            return isReversed(files, reversed);
        }
    }
}
