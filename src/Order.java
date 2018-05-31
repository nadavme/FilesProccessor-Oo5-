import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.*;
import java.io.*;

public class Order extends DirectoryProcessor {

    //    private Type type = new Type();
//    private static Order order = new Order();
    private static final String REVERSE = "REVERSE";
    private static final String REGULAR = "";
    private static final String NO_TYPE = "";
    private static final String SEPARATOR = ".";

    public Order() {
        Order order = new Order();
    }

    public enum OrderCase {
        // Order enums.
        ABS("abs", new Abs()),
        TYPE("type", new Type()),
        SIZE("size", new Size()),
        DEFAULT_ORDER("default", new Abs());

        private final String orderName;
        private final Abs orderObject;

        OrderCase(String orderName, Abs orderObject) {
            this.orderName = orderName;
            this.orderObject = orderObject;
        }

        public String getOrderName() {
            return orderName;
        }

        public Abs getOrderObject() {
            return orderObject;
        }
    }

    static OrderCase orderFactory(String Order) {
        if (Order.equals(OrderCase.ABS.getOrderName())) {
            return OrderCase.ABS;
        } else if (Order.equals(OrderCase.TYPE.getOrderName())) {
            return OrderCase.TYPE;
        } else if (Order.equals(OrderCase.SIZE.getOrderName())) {
            return OrderCase.SIZE;
        } else {
            return OrderCase.DEFAULT_ORDER;
        }
    }

    private void CheckValidity(String reversed) {
        if (!reversed.equals(REVERSE) && !reversed.equals(REGULAR)) {
            throw new UnsupportedOperationException();
        }
    }

    private ArrayList<File> isReversed(ArrayList<File> files, String reversed) {
        if (reversed.equals(REVERSE)) {
            Collections.reverse(files);
        }
        return files;
    }

    protected class Abs {

        private ArrayList<File> AbsOrder(ArrayList<File> files, String reversed) {
            CheckValidity(reversed);
            Collections.sort(files);
            return isReversed(files, reversed);
        }
    }

    protected class Size {

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

    protected class Type {

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

    private String fileSuffix(File file) {
        if (!file.getName().contains(SEPARATOR)) {
            return NO_TYPE;
        }
        int file1Separator = file.getName().lastIndexOf(SEPARATOR);
        return file.getName().substring(file1Separator);
    }

//    public static void main(String[] args) {
//        File file2 = new File("data2.text");
//        File file1 = new File("da.z");
//
//        ArrayList<File> files = new ArrayList<File>(2);
//        files.add(file2);
//        files.add(file1);
//        System.out.println(order.type.TypeOrder(files, ""));
//
//    }

}
