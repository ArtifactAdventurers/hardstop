package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.HSContainer;
import dev.gruff.hardstop.api.HSClass;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class RavenStats {


    public enum StatsElement {

        classes, interfaces,enums,literals,finalClass,superClass
    }

    private int[] totals;
    private Map<HSContainer,int[]> containerStats=new HashMap<>();
    private Map<String,int[]> packageStats=new HashMap<>();
    public RavenStats() {
        StatsElement[] labels=StatsElement.values();
        totals=new int[labels.length];
    }
    public void update(HSClass clazz) {

        StatsElement[] labels = StatsElement.values();
        int[] record = new int[labels.length];

        if (clazz.isClass()) record[StatsElement.classes.ordinal()]++;
        if (clazz.isEnum()) record[StatsElement.enums.ordinal()]++;
        if (clazz.isInterface()) record[StatsElement.interfaces.ordinal()]++;
        if (clazz.isFinal()) record[StatsElement.finalClass.ordinal()]++;
       record[StatsElement.literals.ordinal()]=clazz.literals().size();

        // update container
        HSContainer container=clazz.container();
        int[] cstats=containerStats.computeIfAbsent(container, f ->  new int[labels.length]);
        addStats(cstats,record);

        // update package
        int[] pstats=packageStats.computeIfAbsent(clazz.packageName(), f ->  new int[labels.length]);
        addStats(pstats,record);

        // totals
        addStats(totals,record);

    }


    private void addStats(int[] parent, int[] record) {
        for(int i=0;i<parent.length;i++) {
            parent[i]=parent[i]+record[i];
        }
    }

    public void print() {
        print(System.out);
    }

    public void print(PrintStream out) {
        out.println("Total Stats");
        printLabels(out,totals);
    }

    private void printLabels(PrintStream out, int[] totals) {
        for(StatsElement s:StatsElement.values()) {
            out.println(s.name()+" = "+totals[s.ordinal()]);
        }
    }

    public int classCount() {
        return totals[StatsElement.classes.ordinal()];
    }
}
