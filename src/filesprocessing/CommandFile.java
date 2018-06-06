package filesprocessing;

/**
 *
 */
public class CommandFile {

    private static final String FILTER = "FILTER";
    private static final String ORDER = "ORDER";
    private static final String NEGATIVE = "NOT";
    private static final String REVERSE = "REVERSE";
    private static final String SEPARATOR = "#";
    private static final String EMPTY = "";
    private static final String BETWEEN = "between";
    private static final int FILTER_LENGTH = 3;
    private static final String NO_BET = "not between";
    private static final String DEFAULT_FILTER = "all";

    private static final int ORDER_LINE_NUM = 4;
    private static final int FILTER_LINE_NUM = 2;

    /**
     * *****************************Error messages***********************************************
     */
    private static final String WARNING = "Warning in line";
    //    private static final String BAD_COMMAND_FILE_ERROR = "ERROR: The command file doesn't " +
//            "exist or it's a directory";
    private static final String BAD_FORMAT_ERROR = "ERROR: Problem with format of Commands File";
    private static final String BAD_SUBSECTION_ERROR = "ERROR: Problem with subsection name";
    //    private static final String BAD_INPUT_ERROR = "ERROR: Wrong usage.Should recive 2 arguments";
//    private static final String BAD_COMMANDS = "invalid command input! there should be ONLY two commands.";
    private static final int ORDER_LENGTH = 2;


    static void isValid(String[] data) throws Exceptions.Type2Exception {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                throw new Exceptions.SectionEx(BAD_FORMAT_ERROR);
            }
        }
        if (!(data[0].equals(FILTER))) {
            throw new Exceptions.FilterEx(BAD_SUBSECTION_ERROR);
        } else if (!(data[2].equals(ORDER))) {
            throw new Exceptions.OrderEx(BAD_SUBSECTION_ERROR);
        }
    }
//    /**
//     *
//     * @param filterInput
//     * @param filter
//     * @return
//     */


    /**
     * @param lineNumber
     * @param filter
     * @param order
     */
    static void Errors(int lineNumber, String[] block, Filter.FilterQ filter, Order.OrderQ order) {
        if (filter == null && !block[1].equals(DEFAULT_FILTER)) {
            System.err.println(WARNING + lineNumber + FILTER_LINE_NUM);
        }
        if (order.getoName().equals(EMPTY)) {
            System.err.println(WARNING + lineNumber + ORDER_LINE_NUM);
        }
    }

    /**
     * @param idx
     * @param string
     * @return
     */
    static String sliceString(int idx, String string) {
        String[] array = string.split(SEPARATOR);
        if (array.length > idx) {
            return array[idx];
        }
        return EMPTY;
    }


//    /**
//     *
//     * @param string
//     * @param array
//     * @return
//     */
//   boolean containsTheString(String string, String[] array){
//       for (String elem:array){
//           if (string.equals(elem)){
//               return true; }
//       }
//       return false;
//   }

    /**
     * @param orderType
     * @return
     */
    static String isOrderValid(String orderType) {
        String[] array = orderType.split(SEPARATOR);
        if (array.length > 1 && !(array[1].equals(REVERSE))) {
            return EMPTY;
        }
        return orderType;
    }

    /**
     * @param filterType
     * @return
     */
    static String isFilterValid(String filterType) {
        String[] array = filterType.split(SEPARATOR);
        if (array[0].equals(BETWEEN) && array.length == FILTER_LENGTH + 1 && array[FILTER_LENGTH].equals(NEGATIVE)) {
            return NO_BET + SEPARATOR + array[1] + SEPARATOR + array[2];
        }
        if (array.length == ORDER_LENGTH + 1 || array.length == ORDER_LENGTH) {
            return filterType;
        } else if (array.length == 1 && filterType.equals(DEFAULT_FILTER)) {
            return filterType;
        }
        return EMPTY;
    }

}
