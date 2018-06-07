package filesprocessing;// this are the java util functions we are going to use

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.*;
import java.io.*;

import filesprocessing.Exceptions.ReverseEx;


/**
 * the father class for all the orders we will want to use, extends the filesprocessing.DirectoryProcessor class
 */
public class Order {

    /**
     * this are the permanent parameters for our class
     */
    private static final String REVERSE = "REVERSE";
    private static final String REGULAR = "";
    private static final String SEPARATOR = ".";
    private static final String TYPE_SEPARATOR = "\\.";


    /**
     * this is the enum we are going to use once we call the class, comes instead of a switch case.
     * further more, helps us save memory space by not creating new objects every time.
     */
    public enum OrderQ {
        // name for filesprocessing.Order enums that will hold the filesprocessing.Order objects.
        ABS("abs", new Abs()),
        TYPE("type", new Type()),
        SIZE("size", new Size()),
        DEFAULT_ORDER("default", new Abs());

        // this are the two parameters we give the enum.
        private final String oName;
        private final OrderInterface orderObject;

        // the default constructor for our enum, with the string and the filesprocessing.Order object.
        OrderQ(String orderName, OrderInterface orderObject) {
            this.oName = orderName;
            this.orderObject = orderObject;
        }

        // the getter func for the string representing the filesprocessing.Order name.
        public String getoName() {
            return oName;
        }

        // the getter func for the object representing the filesprocessing.Order we want.
        public OrderInterface getOrderObject() {
            return orderObject;
        }
    }

    /**
     * this will be the function that simply checks what order we need,
     * and then builds the order object we were asked for with the right method.
     *
     * @param order - a string representing the order we are asked for
     * @return the OrderQ object, that holds the name of the order and the order object,
     * and which is going to be the enum.
     */
    public static OrderQ orderBuilder(String order) {
        if (order.equals(OrderQ.ABS.getoName())) {
            return OrderQ.ABS;
        } else if (order.equals(OrderQ.TYPE.getoName())) {
            return OrderQ.TYPE;
        } else if (order.equals(OrderQ.SIZE.getoName())) {
            return OrderQ.SIZE;
        } else {
            return OrderQ.DEFAULT_ORDER;
        }
    }


    /**
     * this function simply checks that the 'reversed' parameter is valid,
     * has only one of two values possible.
     *
     * @param reversed - the parameter that indicates if we should reverse the order.
     */
    private static void CheckValidity(String reversed) {
        if (!reversed.equals(REVERSE) && !reversed.equals(REGULAR)) {
            throw new ReverseEx();
        }
    }

    /**
     * the func that gives us the suffix of the file.
     *
     * @param file - the file object we are examining.
     * @return the suffix of the file.
     */
    private String fileSuffix(File file) {
        if (!file.getName().contains(SEPARATOR)) {
            return REGULAR;
        }
        int file1Separator = file.getName().lastIndexOf(SEPARATOR);
        return file.getName().substring(file1Separator);
    }

    /**
     * this func checks if the reversed parameter indicates that the order should be reversed,
     * and if it does indicates that it reverses the order.
     *
     * @param files    - the array list of files we want to order.
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
    protected static class Abs implements OrderInterface {

        /**
         * the function that organizes the files by abs order.
         *
         * @param files    - the array list of files
         * @param reversed - the reversed parameter.
         * @return the files ordered.
         */
        public void orderFiles(ArrayList<File> files, String reversed) {
            CheckValidity(reversed);
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    return f1.getName().compareTo(f2.getName());
                }
            });
            if (reversed.equals(REVERSE)) {
                Collections.reverse(files);
            }
        }
    }

    /**
     * this will be our size class that basically holds the func for the size order.
     */
    protected static class Size extends Abs {

        /**
         * the function that organizes the files by size order.
         *
         * @param files    - the array list of files
         * @param reversed - the reversed parameter.
         * @return the files ordered.
         */
        public void orderFiles(ArrayList<File> files, String reversed) {
            CheckValidity(reversed);
            super.orderFiles(files, REGULAR);
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    Long file1Size = file1.length();
                    return file1Size.compareTo(file2.length());
                }
            });
            if (reversed.equals(REVERSE)) {
                Collections.reverse(files);
            }
        }
    }

    /**
     * this will be our type class that basically holds the func for the type order.
     */
    protected static class Type extends Abs {

        /**
         * the function that organizes the files by type order.
         *
         * @param files    - the array list of files
         * @param reversed - the reversed parameter.
         * @return the files ordered.
         */
        public void orderFiles(ArrayList<File> files, String reversed) {
            CheckValidity(reversed);
            super.orderFiles(files, REGULAR);
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    String file1Suffix = fileSuffix(file1.getName());
                    String file2Suffix = fileSuffix(file2.getName());
                    return file1Suffix.compareTo(file2Suffix);
                }
            });
        }

        /**
         * the function that helps us get the file's suffix
         *
         * @param name - the full name of the file (prefix and suffix)
         * @return a string representing the suffix
         */
        private String fileSuffix(String name) {
            String[] splitted = name.split(TYPE_SEPARATOR);
            if (splitted.length == 0 || splitted.length == 1 || (splitted.length == 2 && splitted[0].equals(REGULAR))) {
                return REGULAR;
            }
            return splitted[splitted.length - 1];
        }
    }
}
