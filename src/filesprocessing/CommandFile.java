package filesprocessing;

/**
 * the command file class
 */
public class CommandFile {

    /**
     * this are the permanent parameters for our class
     */
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
    private static final String BAD_FORMAT_ERROR = "ERROR: Problem with format of Commands File";
    private static final String BAD_SUBSECTION_ERROR = "ERROR: Problem with subsection name";
    private static final int ORDER_LENGTH = 2;

    /**
     * checks if the commands in the command file are valid and legal
     *
     * @param data - a list of strings representing the commands
     * @throws Exceptions.Type2Exception
     */
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

    /**
     * this func throws and prints out the errors.
     *
     * @param lineNumber - the number of the line with the error
     * @param filter     - the filter command we want to check
     * @param order      - the order command we want to check
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
     * this func takes the string representing the name of the command
     * and separates it into a list.
     *
     * @param idx    - a pointer
     * @param string - the name of the command (not sliced and with '#")
     * @return - the element in the list using the pointer, or an empty string.
     */
    static String sliceString(int idx, String string) {
        String[] array = string.split(SEPARATOR);
        if (array.length > idx) {
            return array[idx];
        }
        return EMPTY;
    }

    /**
     * checks if the order is valid
     *
     * @param orderType - the type of the order given
     * @return - empty string if the order is not valid, if it is valid we return the order type.
     */
    static String isOrderValid(String orderType) {
        String[] array = orderType.split(SEPARATOR);
        if (array.length > 1 && !(array[1].equals(REVERSE))) {
            return EMPTY;
        }
        return orderType;
    }

    /**
     * checks if the filter is valid
     *
     * @param filterType - the type of the filter given
     * @return - empty string if the filter is not valid, if it is valid we return the filter type,
     * and if the filter is the filter "notBetween" return the given "notBetween" filter.
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
