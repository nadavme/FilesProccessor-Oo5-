package filesprocessing;

import java.io.File;
import java.util.ArrayList;

public class Block  {

    private String[] block;
    private Order.OrderQ order;
    private Filter.FilterQ filter;
    private int lineNumber;

    protected static final String DEFAULTIVE_ORDER = "";
    protected static final int ORDER_INPUT = 3;
    protected static final int FILTER_INPUT = 1;
    protected static final int NEGATIVE_IDX = 2;



    Block(int lineNumber, String[] data) throws Exceptions.Type2Exception {
        this.lineNumber = lineNumber;
        this.block = data;
        CommandFile.isValid(data);
        block[ORDER_INPUT] = CommandFile.isOrderValid(block[ORDER_INPUT]);
        block[FILTER_INPUT] = CommandFile.isFilterValid(block[FILTER_INPUT]);
        order = Order.orderBuilder(CommandFile.sliceString(0, block[ORDER_INPUT]));
        filter = Filter.filterBuilder(CommandFile.sliceString(0, block[FILTER_INPUT]));

    }

    private void doAction(ArrayList<File> files) {
        filterTheFiles(files);
        orderTheFiles(files);
        CommandFile.Errors(lineNumber, block, filter, order);
        for(File file: files){
            if (file != null){
                System.out.println(file.getName());
            }
        }
    }


    private void orderTheFiles(ArrayList<File> files) {

    }

    private void filterTheFiles(ArrayList<File> files) {

    }
}