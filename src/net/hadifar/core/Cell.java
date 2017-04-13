package net.hadifar.core;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CkyParser
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

    private String nonTerminalSymbol = null;
    private String terminalSymbol = null;
    private BigDecimal probability = null;

    // back pointers
    private Cell left = null;
    private Cell right = null;

    public Cell() {
        entries = new ArrayList<>();
    }

    /**
     * each Cell may contains several rule , So define entries to handle this
     * @param cell newly added
     * @param left
     * @param right
     */
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
            ret.append(entry.nonTerminalSymbol + ",");
        }
        return ret.toString();
    }


    /**
     * Sort solution by probability and return it as StringBuffer
     * @param sb
     */
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

    /**
     * Print solution in PenTreeBank format
     * @param sb
     */
    public void getTrace(StringBuffer sb) {
        sb.append("(" + nonTerminalSymbol);
        if (left != null) {
            left.getTrace(sb);
            right.getTrace(sb);
        } else {
            sb.append(" " + terminalSymbol);
        }
        sb.append(")");
    }

    public void setEntries(List<Cell> entries) {
        this.entries = entries;
    }

    public String getNonTerminalSymbol() {
        return nonTerminalSymbol;
    }

    public void setNonTerminalSymbol(String nonTerminalSymbol) {
        this.nonTerminalSymbol = nonTerminalSymbol;
    }

    public String getTerminalSymbol() {
        return terminalSymbol;
    }

    public void setTerminalSymbol(String terminalSymbol) {
        this.terminalSymbol = terminalSymbol;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }

    public Cell getLeft() {
        return left;
    }

    public void setLeft(Cell left) {
        this.left = left;
    }

    public Cell getRight() {
        return right;
    }

    public void setRight(Cell right) {
        this.right = right;
    }
}

