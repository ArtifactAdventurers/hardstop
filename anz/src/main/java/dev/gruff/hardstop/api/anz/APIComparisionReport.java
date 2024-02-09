package dev.gruff.hardstop.api.anz;

import dev.gruff.hardstop.api.HSClass;

import java.util.LinkedList;
import java.util.List;

public class APIComparisionReport {

    private List<APIComparision.Result> results=new LinkedList<>();
    public APIComparisionReport(HSClass a, HSClass b) {
    }

    public void add(APIComparision.Result r) {
        results.add(r);
    }

}
