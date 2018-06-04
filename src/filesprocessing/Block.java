package filesprocessing;
import jdk.nashorn.internal.ir.Block;

import java.util.ArrayList;

public class Block extends DirectoryProcessor {
        protected int size;
        protected String[] list;
        protected   Block block = new Block(4);
        protected Block(int size){
            block.size = size;
            block.list = new String[size];
        }

    public Block(int lineNumber, String[] data) throws NoSuchFieldException, NullPointerException {
    }

    private void doAction(ArrayList<Block> commandBlocks) {
            int i = 0;
            while (i < commandBlocks.size()) {
                Block tempBlock = commandBlocks.get(i);
                String filterParam = tempBlock.list[1];
                String orderParam = tempBlock.list[3];
                Filter filter = new Filter();
                Order order = new Order();
                Filter.FilterQ filterQ = new Filter.FilterQ(filterParam, filter);
                filterQ = Filter.filterBuilder(filterParam);
                Order.OrderQ orderQ = new Order.OrderQ(orderParam, order);
                orderQ = Order.orderBuilder(orderParam);

            }
        }
}