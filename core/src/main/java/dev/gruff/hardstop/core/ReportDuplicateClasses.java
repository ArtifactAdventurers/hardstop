
package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.HSComponent;

import java.io.PrintWriter;
import java.util.*;

public class ReportDuplicateClasses {

    private final PrintWriter pw;
    public ReportDuplicateClasses(PrintWriter pw) {
        this.pw=pw;
    }
    public ReportDuplicateClasses() {
        this(new PrintWriter(System.out));
    }

    public void report(HSComponent m) {



    }

}
