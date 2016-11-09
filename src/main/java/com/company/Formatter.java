package com.company;

import java.io.IOException;

/**
 * code formatter.
 */
final class Formatter implements IFormatter {
    /**
     * next & last considered char from file.
     */
    private int level;
    /**
     * nextchar.
     */
    private char nextChar;
    /**
     * result.
     */
    private StringBuilder result;
    /**
     * current string.
     */
    private String currStr;
    /**
     * flags.
     */
    private boolean isString, isComment;

    /**
     * constructor lego.
     */
    Formatter() {
        result = new StringBuilder();
    }

    /**
     * format code.
     *
     * @param r input
     * @param w output
     * @throws IOException err
     */
    public void format(final IReader r, final IWriter w) throws IOException {
        while (r.hasChars()) {
            currStr = safeReadString(r);
            for (int j = 0; j < currStr.length(); ++j) {
                char ch = currStr.charAt(j);
                switch (ch) {
                    case '{':
                        if (isComment || isString) {
                            result.append(ch);
                            break;
                        }
                        level++;
                        result.append(ch).append('\n');
                        writeTabs();
                        break;
                    case '}':
                        if (isComment || isString) {
                            result.append(ch);
                            break;
                        }
                        level--;
                        result.append('\n');
                        writeTabs();
                        result.append(ch);
                        break;
                    case ';':
                        if (isComment || isString) {
                            result.append(ch);
                            break;
                        }
                        result.append(ch);
                        if (!currStr.contains("/")) {
                            result.append('\n');
                            writeTabs();
                        }
                        break;
                    case '/':
                        if (currStr.charAt(j + 1) == '/') {
                            isComment = true;
                            result.append(ch);
                            break;
                        } else if (currStr.charAt(j + 1) == '*') {
                            result.append("/*");
                            j++;
                            break;
                        }
                        result.append(ch);
                        break;
                    case '*':
                        result.append(ch);
                        break;
                    case '\n':
                        if (isComment || isString) {
                            isComment = false;
                            result.append(ch);
                        }
                        writeTabs();
                        break;
                    case '"':
                        if (isString && currStr.charAt(j - 1) == '\\') {
                            result.append(ch);
                            break;
                        }
                        isString = !isString;
                    default:
                        result.append(ch);
                        break;
                }
            }
            w.writeString(result.toString());
            result.setLength(0);
        }
        w.close();
    }

    /**
     * writes tabs.
     */
    private void writeTabs() {
        for (int i = 0; i < level; ++i) {
            result.append('\t');
        }
    }

    /**
     * safe read string to avoid errors in format().
     *
     * @param r reader
     * @return string from file
     * @throws IOException error
     */
    String safeReadString(final IReader r) throws IOException {
        StringBuilder buff = new StringBuilder();
        while (r.hasChars()) {
            nextChar = r.readChar();
            if (nextChar != '}' && nextChar != '/' && nextChar != ' '
                    && nextChar != '\\' && nextChar != '"'
                    && nextChar != '\t' && nextChar != '\n'
                    && nextChar != ')' && nextChar != '*' && nextChar != ';') {
                break;
            }
            buff.append(nextChar);
        }
        buff.append(nextChar);
        return buff.toString().replaceAll(" +", " ");
    }

}
