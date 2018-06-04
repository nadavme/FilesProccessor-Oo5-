package filesprocessing;

/**
 *
 */
public class commandFile {

    private static final String WARNING = "Warning in line";
    private static final String NEGATIVE = "#NOT";
    private static final String REVERSE = "#REVERSE";
    private static final String SEPARATOR = "#";
    private static final String EMPTY = "";
    private static final int ORDER_LINE_NUM = 3;
    private static final int FILTER_LINE_NUM = 1;

    /**
     *
     * @param filterInput
     * @param filter
     * @return
     */
   boolean isNegative(String filterInput,String filter){
       if (filter != null){
           return filterInput.contains(NEGATIVE);
       }
       return false;
   }

    /**
     *
     * @param lineNumber
     * @param filter
     * @param order
     */
   void Errors(int lineNumber, Filter.FilterQ filter, Order.OrderQ order){
       if (filter == null){
           System.err.println(WARNING + lineNumber + 1);
       }
       if (order.getOName().equals("")){
           System.err.println(WARNING + lineNumber + 3);
       }
   }

    /**
     *
     * @param idx
     * @param string
     * @return
     */
   String sliceString(int idx, String string){
       String[] array = string.split(SEPARATOR);
       if (array.length > idx){
           return array[idx];
       }
       return EMPTY;


   }

    /**
     *
     * @param string
     * @param array
     * @return
     */
   boolean containsTheString(String string, String[] array){
       for (String elem:array){
           if (string.equals(elem)){
               return true; }
       }
       return false;
   }

    /**
     *
     * @param string
     * @return
     */
   String isOrderValid(String string){
           String[] array = string.split(SEPARATOR);
           if (array.length > 1 && !(array[1].equals(NEGATIVE))){
               return EMPTY;
           }
           return string;}

    /**
     *
     * @param string
     * @return
     */
   String isFilterValid(String string){
       String[] array = string.split(SEPARATOR);
       if (array.length == 3 || array.length == 2){
           return string;
       }
       return EMPTY;
}

}
