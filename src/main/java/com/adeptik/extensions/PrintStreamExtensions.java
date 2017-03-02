package com.adeptik.extensions;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

public final class PrintStreamExtensions {

    /**
     * Вывод в консоль информации об ошибке
     *
     * @param printStream PrintStream, в который необходимо вывести данные
     * @param throwable   Исключение, описывающее ошибку
     * @param causeIndent Отступ для вложенных исключений
     */
    public static void print(PrintStream printStream,
                             Throwable throwable,
                             String causeIndent) {

        if (printStream == null)
            throw new NullPointerException("printStream");
        if (throwable == null)
            throw new NullPointerException("throwable");

        if (throwable instanceof InvocationTargetException && throwable.getCause() != null)
            print(printStream, throwable.getCause(), causeIndent);
        else {
            String indent = "";
            for (Throwable e = throwable; e != null; e = e.getCause()) {
                printStream.print(indent);
                String message = e.getMessage();
                printStream.println(message != null ? message : e.getClass().getName());
                indent += causeIndent;
            }
        }
    }

    /**
     * Вывод в консоль информации об ошибке.
     * В качестве строки отступа для вложенных исключений используется " ".
     *
     * @param printStream PrintStream, в который необходимо вывести данные
     * @param throwable   Исключение, описывающее ошибку
     */
    public static void print(PrintStream printStream,
                             Throwable throwable) {

        print(printStream, throwable, " ");
    }
}
