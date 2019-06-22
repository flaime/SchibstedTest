package Schibsted.test.filesearch;


import Schibsted.test.filesearch.view.InteractiveDirectorySearch;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("You to add folder path as argument.");
        }else{
            Path directory = Paths.get(args[0]);
            if (directory.toFile().isDirectory())
                new InteractiveDirectorySearch(directory, System.in, System.out).run();
            else
                System.out.println("Arg is not an folder.");
        }

    }
}
