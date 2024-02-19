package it.unicam.cs.pa.ma114110.parser;

import java.io.File;

public interface Parser<T> {
    /**
     * Parse the file at the given path and return the corresponding {@link T}
     * @param path the path of the file to parse
     * @return the parsed {@link T}
     */
    T parse(String path);

    /**
     * Parse the file at the given path and return the corresponding {@link T}
     * @param file the file to parse
     * @return the parsed {@link T}
     */
    T parse(File file);
}
