import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.*;
import java.io.*;

public class Order extends DirectoryProcessor {

    private static final String REVERSE = "REVERSE";
    private static final String REGULAR = "";
    private static final String NO_TYPE = "";
    private static final String SEPARATOR = ".";

    public Order() {
        Order order = new Order();
    }

    public enum OrderQ {
        // Order enums.
        ABS("abs", new Abs()),
        TYPE("type", new Type()),
        SIZE("size", new Size()),
        DEFAULT_ORDER("default", new Abs());

        private final String orderName;
        private final Order orderObject;

        OrderQ(String orderName, Order orderObject) {
            this.orderName = orderName;
            this.orderObject = orderObject;
        }

        public String getOrderName() {
            return orderName;
        }

        public Order getOrderObject() {
            return orderObject;
        }
    }

    public OrderQ orderFactory(String Order) {
        if (Order.equals(OrderQ.ABS.getOrderName())) {
            return OrderQ.ABS;
        } else if (Order.equals(OrderQ.TYPE.getOrderName())) {
            return OrderQ.TYPE;
        } else if (Order.equals(OrderQ.SIZE.getOrderName())) {
            return OrderQ.SIZE;
        } else {
            return OrderQ.DEFAULT_ORDER;
        }
    }

    private static void CheckValidity(String reversed) {
        if (!reversed.equals(REVERSE) && !reversed.equals(REGULAR)) {
            throw new UnsupportedOperationException();
        }
    }

    private static String fileSuffix(File file) {
        if (!file.getName().contains(SEPARATOR)) {
            return NO_TYPE;
        }
        int file1Separator = file.getName().lastIndexOf(SEPARATOR);
        return file.getName().substring(file1Separator);
    }

    private static ArrayList<File> isReversed(ArrayList<File> files, String reversed) {
        if (reversed.equals(REVERSE)) {
            Collections.reverse(files);
        }
        return files;
    }

    protected static class Abs extends Order {

        private ArrayList<File> AbsOrder(ArrayList<File> files, String reversed) {
            CheckValidity(reversed);
            Collections.sort(files);
            return isReversed(files, reversed);
        }
    }

    protected static class Size extends Order {

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

    protected static class Type extends Order {

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
