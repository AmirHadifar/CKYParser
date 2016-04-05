package net.hadifar.core;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Cell {

    private List<Cell> entries = null;

    public String pname = null;
    public String name = null;
    public BigDecimal probability = null;

    // back pointers
    public Cell left = null;
    public Cell right = null;

    public Cell() {
        entries = new ArrayList<>();
    }

    public void addEntry(Cell cell, Cell left, Cell right) {
        cell.left = left;
        cell.right = right;
        entries.add(cell);
    }

    public List<Cell> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        StringBuffer ret = new StringBuffer();
        for (Cell entry : entries) {
            ret.append(entry.pname + ",");
        }
        return ret.toString();
    }


    public void getSolution(StringBuffer sb) {
        //sort by their probability
        Collections.sort(entries, new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                if (o1.probability.doubleValue() < o2.probability.doubleValue())
                    return 0;
                else return -1;
            }
        });

        for (int i = 0; i < entries.size(); i++) {
            Cell entry = entries.get(i);
            entry.getTrace(sb);
            sb.append("\t" + entry.probability  + "\n");
        }
    }

    public void getTrace(StringBuffer sb) {
        sb.append("(" + pname);
        if (left != null) {
            left.getTrace(sb);
            right.getTrace(sb);
        } else {
            sb.append(" " + name);
        }
        sb.append(")");
    }
}

