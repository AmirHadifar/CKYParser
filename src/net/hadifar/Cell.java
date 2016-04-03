package net.hadifar;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */


import java.util.ArrayList;

public class Cell {

    private ArrayList<Cell> entries = null;

    public String pname = null;
    public String name = null;

    // back pointers
    public Cell left = null;
    public Cell right = null;

    public Cell() {
        entries = new ArrayList<Cell>();
    }

    public void addEntry(Cell cell, Cell left, Cell right) {
        cell.left = left;
        cell.right = right;
        entries.add(cell);
    }
//
//    private void addUnique(Cell cell) {
//        for (Cell c : entries) {
//            if (c.pname.equals(cell.pname)) {
//                return;
//            }
//        }
//        entries.add(cell);
//    }

    public ArrayList<Cell> getEntries() {
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

    public void printSolution() {
        for (int i = 0; i < entries.size(); i++) {
            Cell entry = entries.get(i);
            entry.printTrace();
            System.out.println();
        }
    }

    public void getSolution(StringBuffer sb) {
        for (int i = 0; i < entries.size(); i++) {
            Cell entry = entries.get(i);
            entry.getTrace(sb);
            sb.append("\n");
        }
    }

    public void printTrace() {
        System.out.print("(" + pname);
        if (left != null) {
            left.printTrace();
            right.printTrace();
        } else {
            System.out.print(" " + name);
        }
        System.out.print(")");
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

