package net.hadifar.core;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */


import java.util.ArrayList;
import java.util.List;

public class Cell {

    private List<Cell> entries = null;

    public String pname = null;
    public String name = null;
    public double probability = 0;

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
        for (int i = 0; i < entries.size(); i++) {
            Cell entry = entries.get(i);
            entry.getTrace(sb);
            sb.append("\n");
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

