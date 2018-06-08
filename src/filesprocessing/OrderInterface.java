package filesprocessing;

import java.io.File;
import java.util.ArrayList;

import filesprocessing.Exceptions.ReverseEx;
import filesprocessing.Exceptions.Type1Exception;

/**
 * An interface that defines to each sub-order class to implements the OrderFiles method. it is really easy to work
 * with, when each method gets the same parameters. 
 */
public interface OrderInterface {

    void orderFiles(ArrayList<File> files, String reverse);
}
