package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for write to files.
 */
class Writer implements IWriter {
    /**
     * file.
     */
    private BufferedWriter buffer;

    /**
     * @param file file name
     * @throws WriterException err
     */
    Writer(final String file) throws WriterException {
        try {
            buffer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write char to file.
     *
     * @param ch character
     * @throws WriterException err
     */
    public void writeChar(final char ch) throws WriterException {
        try {
            buffer.append(ch);
        } catch (IOException e) {
            throw new WriterException();
        }
    }

    /**
     * close file.
     *
     * @throws WriterException err
     */
    public void close() throws WriterException {
        try {
            buffer.close();
        } catch (IOException e) {
            throw new WriterException();
        }
    }
}
