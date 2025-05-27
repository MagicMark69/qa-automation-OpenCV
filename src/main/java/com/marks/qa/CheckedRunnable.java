package com.marks.qa;

@FunctionalInterface
public interface CheckedRunnable {
    void run() throws Exception;
}
