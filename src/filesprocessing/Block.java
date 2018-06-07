package filesprocessing;

import java.io.File;
import java.util.ArrayList;

/**
 * the block class
 */
public class Block {
    /**
     * Our constant fields of this class, mostly exception prints.
     */
    private String[] block;
    private Order.OrderQ order;
    private Filter.FilterQ filter;
    private int lineNumber;
    /**
     * this are the permanent parameters for our class
     */
    protected static final String DEFAULTIVE_ORDER = "";
    protected static final int ORDER_INPUT = 3;
    protected static final int FILTER_INPUT = 1;
    protected static final int NEGATIVE_IDX = 2;

    /**
     * our block constractor
     *
     * @param lineNumber - the line number we will start the building of the block from
     * @param data       - our list of string that will eventually be apart of the block
     * @throws Exceptions.Type2Exception
     */
    Block(int lineNumber, String[] data) throws Exceptions.Type2Exception {
        this.lineNumber = lineNumber;
        this.block = data;
        CommandFile.isValid(data);
        block[ORDER_INPUT] = CommandFile.isOrderValid(block[ORDER_INPUT]);
        block[FILTER_INPUT] = CommandFile.isFilterValid(block[FILTER_INPUT]);
        order = Order.orderBuilder(CommandFile.sliceString(0, block[ORDER_INPUT]));
        filter = Filter.filterBuilder(CommandFile.sliceString(0, block[FILTER_INPUT]));

    }

    /**
     * this method is the one that calls the functions and to do their actions
     *
     * @param files - our list of files to filter and order
     */
    protected void doAction(ArrayList<File> files) {
        filterTheFiles(files);
        orderTheFiles(files);
        CommandFile.Errors(lineNumber, block, filter, order);
        for (File file : files) {
            if (file != null) {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * this is the method that will order the files using the Order class and methods
     *
     * @param files - our list of files to order
     */
    private void orderTheFiles(ArrayList<File> files) {
        String type = CommandFile.sliceString(1, block[ORDER_INPUT]);
        try {
            order.getOrderObject().orderFiles(files, type);
        } catch (Exceptions.Type1Exception e) {
            Order.orderBuilder(DEFAULTIVE_ORDER).getOrderObject().orderFiles(files, DEFAULTIVE_ORDER);
        }

    }

    /**
     * this is the method that will filter the files using the Filter class and methods
     *
     * @param files - our list of files to order
     */
    private void filterTheFiles(ArrayList<File> files) {
        if (filter != null) {
            String type = CommandFile.sliceString(1, block[FILTER_INPUT]);
            String negative = CommandFile.sliceString(NEGATIVE_IDX, block[FILTER_INPUT]);
            try {
                filter.getfObject().filterFiles(files, type, negative);
            } catch (Exceptions.Type1Exception e) {
                filter = null;
            }
        }
    }
}