package filesprocessing;

import java.io.File;
import java.util.ArrayList;
import filesprocessing.Exceptions.ReverseEx;
import filesprocessing.Exceptions.Type1Exception;

public interface OrderInterface {

    public void orderFiles(ArrayList<File> files, String reverse);
}
