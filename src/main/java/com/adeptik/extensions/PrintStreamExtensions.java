package com.adeptik.extensions;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Утилиты для {@link PrintStream}
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class PrintStreamExtensions {

    /**
     * Вывод в консоль информации об ошибке
     *
     * @param printStream     PrintStream, в который необходимо вывести данные
     * @param throwable       Исключение, описывающее ошибку
     * @param causeIndent     Отступ для вложенных исключений
     * @param printStackTrace Выводить стек вызовов или нет
     */
    public static void print(PrintStream printStream,
                             Throwable throwable,
                             String causeIndent,
                             boolean printStackTrace) {

        if (printStream == null)
            throw new NullPointerException("printStream");
        if (throwable == null)
            throw new NullPointerException("throwable");

        if (throwable instanceof InvocationTargetException && throwable.getCause() != null)
            print(printStream, throwable.getCause(), causeIndent, printStackTrace);
        else {
            String indent = "";
            for (Throwable e = throwable; e != null; e = e.getCause()) {
                printStream.print(indent);
                String message = e.getMessage();
                printStream.println(message != null ? message : e.getClass().getName());
                if (printStackTrace)
                    printStackTraceWithIndent(printStream, e.getStackTrace(), indent);
                indent += causeIndent;
            }
        }
    }

    /**
     * Вывод в консоль информации об ошибке.
     * В качестве строки отступа для вложенных исключений используется " ".
     *
     * @param printStream     PrintStream, в который необходимо вывести данные
     * @param throwable       Исключение, описывающее ошибку
     * @param printStackTrace Выводить стек вызовов или нет
     */
    public static void print(PrintStream printStream,
                             Throwable throwable,
                             boolean printStackTrace) {

        print(printStream, throwable, " ", printStackTrace);
    }

    /**
     * Вывод стека вызовов с указанным отступом
     *
     * @param printStream        PrintStream, в который необходимо вывести данные
     * @param stackTraceElements Элементы стека вызовов для вывода
     * @param indent             Отступ для каждого элемента стека вызовов
     */
    public static void printStackTraceWithIndent(PrintStream printStream,
                                                 StackTraceElement[] stackTraceElements,
                                                 String indent) {

        if (stackTraceElements != null)
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                printStream.print(indent);
                printStream.println(stackTraceElement.toString());
            }
    }
}
