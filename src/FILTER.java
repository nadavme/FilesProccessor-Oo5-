import java.util.function.*;
import java.text.*;
import java.lang.*;
import java.io.*;

public class FILTER extends DirectoryProcessor {

    protected class GreaterThan {
        private boolean greater_than(File file, double Bytes) {
            return ((double) (file.length() / 1024) > Bytes);
        }
    }

    protected class SmallerThan {
        private boolean smaller_than(File file, double Bytes) {
            return ((double) (file.length() / 1024) < Bytes);
        }
    }

    protected class Between {
        private boolean between(File file, double upperBytes, double lowerBytes) {
            return ((double) (file.length() / 1024) >= lowerBytes && (double) (file.length() / 1024) <= upperBytes);
        }
    }

    protected class FileName {
        private boolean file(File file, String value) {
            return file.getName().equals(value);
        }
    }

    protected class Contains {
        private boolean contains(File file, String value) {
            return file.getName().contains(value);
        }
    }

    protected class Prefix {
        private boolean prefix(File file, String value) {
            return file.getName().startsWith(value);
        }
    }

    protected class Suffix {
        private boolean suffix(File file, String value) {
            return file.getName().endsWith(value);
        }
    }

    protected class Writable {
        private boolean writable(File file) {
            return file.canWrite();
        }
    }

    protected class Executable {
        private boolean executable(File file) {
            return file.canExecute();
        }
    }

    protected class Hidden {
        private boolean hidden(File file) {
            return file.isHidden();
        }
    }

    protected class All {
        private boolean all(File fatherFile) {
            return true;
        }
    }
}
