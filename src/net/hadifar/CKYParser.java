package net.hadifar;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

import java.util.ArrayList;

public class CKYParser {

    private String[] mWords = null;
    private Cell[][] mChart = null;

    private NonTerminalRules nonTerminalRules = null;
    private TerminalRules terminalRules = null;

    private int tokens = 0;


    /**
     * default constructor for CKYParser
     * pass input sentence for splitting it to arrays of String
     * @param sentence
     */
    public CKYParser(String sentence) {
        this.mWords = sentence.split("\\s");
        tokens = this.mWords.length;
    }


    public void setGrammar(ArrayList<String> rules) {
        nonTerminalRules = new NonTerminalRules();
        terminalRules = new TerminalRules();

        for (String rule : rules) {
            String[] elms = rule.split("\\s");
            if (elms.length < 3) {
                continue;
            } else if (elms.length == 3) {
                terminalRules.addRule(elms);
            } else {
                nonTerminalRules.addRule(elms);
            }
        }
    }


    public void initChart() {
        // init chart
        mChart = new Cell[tokens][];
        for (int i = 0; i < tokens; i++) {
            mChart[i] = new Cell[tokens];
            for (int j = i; j < tokens; j++) {
                mChart[i][j] = new Cell();
            }
        }

        for (int i = 0; i < tokens; i++) {
            initCell(i);
        }
    }

    private void initCell(int i) {
        String word = mWords[i];
        ArrayList<Cell> lexs = terminalRules.lexicalize(word);
        // 품사추가
        for (Cell lex : lexs) {
            addToCell(mChart[i][i], lex, null, null);
        }
    }

    private void addToCell(Cell parent, Cell cell, Cell left, Cell right) {
        parent.addEntry(cell, left, right);
    }

    public void fillChart() {
        for (int j = 1; j < tokens; j++) {
            for (int i = 0; i < tokens - j; i++) {
                fillCell(i, i + j);
            }
        }
    }

    private void fillCell(int i, int j) {
        for (int k = i; k < j; k++) {
            combineCells(i, k, j);
        }
    }

    private void combineCells(int i, int k, int j) {
        Cell cell1 = mChart[i][k];
        ArrayList<Cell> entries1 = cell1.getEntries();

        // find Y in cell[i][k]
        for (Cell c1 : entries1) {

            Cell cell2 = mChart[k + 1][j];
            ArrayList<Cell> entries2 = cell2.getEntries();

            // find Z in cell[k+1][j]
            for (Cell c2 : entries2) {

                // find X in Nonterminal rules
                System.out.println("find: " + c1.pname + " + " + c2.pname);
                Cell newCell = nonTerminalRules.checkRule(c1, c2);
                // if X -> Y Z in Rules
                if (newCell != null) {
                    // match
                    // addToCell(cell[i][j], X, Y, Z)
                    mChart[i][j].addEntry(newCell, c1, c2);
                }
            }
        }
    }

    public void printChart() {

        System.out.println("\n-- Recognition Chart --");
        for (int i = 0; i < tokens; i++) {
            for (int j = 0; j < tokens; j++) {
                if (j < i) {
                    System.out.print("\t");
                } else {
                    System.out.print(mChart[i][j].toString() + "\t");
                }
            }
            System.out.println();
        }

    }

    public void printSolution() {
        Cell fin = mChart[0][tokens - 1];
        fin.printSolution();
    }

    public void getSolution(StringBuffer sb) {
        Cell fin = mChart[0][tokens - 1];
        fin.getSolution(sb);
    }
}

