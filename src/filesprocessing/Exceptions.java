package filesprocessing;

/**
 *
 */
public class Exceptions {

    /**
     * *****************************Error messages***********************************************
     */
    private static final String BAD_COMMAND_FILE = "ERROR: The command file doesn't " +
            "exist or it's a directory";
    private static final String BAD_FORMAT_ERROR = "ERROR: Problem with format of Commands File";
    private static final String BAD_SUBSECTION = "ERROR: Problem with subsection name";
    private static final String BAD_INPUT = "ERROR: Wrong usage.Should recive 2 arguments";
    private static final String BAD_COMMANDS = "invalid command input! there should be ONLY two commands.";
    private final static String BAD_SOURCEDIR = "ERROR: No files in sourcedir";

    public abstract class Type1Exception extends RuntimeException {
        protected Type1Exception() {
            super();
        }
    }

    /**
     *
     */
    protected class InputValEx extends Type1Exception{}

    /**
     *
     */
    protected class RangeEx extends Type1Exception{}

    /**
     *
     */
    protected class ReverseEx extends Type1Exception{}

    /**
     *
     */
    protected class NegativeSuffixEx extends Type1Exception{

        public NegativeSuffixEx() {super(); }}

    /**
     *
     */
    protected class NoPermissionEx extends Type1Exception{}


    /**
     *
     */
    public abstract class Type2Exception extends Exception {
        protected Type2Exception(String err) {
            super(err);
        }
    }

    /**
     *
     */
    protected class inputEX extends Type2Exception {

        protected inputEX(String err) {
            super(err);
        }

        protected inputEX() {
            super(BAD_INPUT);
        }
    }

    /**
     *
     */
    protected class SectionEx extends Type2Exception{

        protected SectionEx(String err) { super(err); }
    }

    /**
     *
     */
    protected class SourcedirEx extends Type2Exception{

        protected SourcedirEx() {
            super(BAD_SOURCEDIR);
        }
    }

    /**
     *
     */
    protected class FileEx extends Type2Exception {

        protected FileEx(){super(BAD_COMMAND_FILE);}

    }

    /**
     *
     */
    protected class FilterEx extends Type2Exception{
        protected FilterEx(String err){super(err);}
    }

    /**
     *
     */
    protected class OrderEx extends Type2Exception{
        protected OrderEx(String err) { super(err); }
    }


}




