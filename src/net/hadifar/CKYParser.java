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

    private NonTerminalRules mNonTerminalRules = null;
    private TerminalRules mTerminalRules = null;

    private int mWordTokens = 0;


    /**
     * default constructor for CKYParser
     * initialized terminal & non-terminals Objects
     */
    public CKYParser() {
        mNonTerminalRules = new NonTerminalRules();
        mTerminalRules = new TerminalRules();
    }

    /**
     * Pass input sentence for splitting it to arrays of String
     * number of words also set to mWordTokens
     *
     * @param sentence
     */
    public void setSentence(String sentence) {
        this.mWords = sentence.split("\\s");
        mWordTokens = this.mWords.length;
    }


    /**
     * build grammar with two ArrayList of terminal and non-terminal
     * set result to mTerminalRules & mNonTerminalRules objects
     *
     * @param terminals
     * @param nonTerminals
     */
    public void buildGrammar(ArrayList<String> terminals, ArrayList<String> nonTerminals) {

        for (String terminalRule : terminals) {
            String[] splitWords = terminalRule.split("\\s");
            mTerminalRules.addRule(splitWords);
        }

        for (String nonTerminalRule : nonTerminals) {
            String[] splitWords = nonTerminalRule.split("\\s");
            mNonTerminalRules.addRule(splitWords);

        }
    }


    /**
     * initializing table/chart of CKYParser with following structure
     * <p>
     * $$$$$$
     * $$$$$
     * $$$$
     * $$$
     * $$
     * $
     */
    public void initChart() {

        mChart = new Cell[mWordTokens][];

        for (int i = 0; i < mWordTokens; i++) {

            mChart[i] = new Cell[mWordTokens];

            for (int j = i; j < mWordTokens; j++) {
                mChart[i][j] = new Cell();
            }
        }

        initCell();
    }

    /**
     * initialize entries of table/chart with words of sentence
     */
    private void initCell() {

        for (int i = 0; i < mWordTokens; i++) {

            String word = mWords[i];

            ArrayList<Cell> lexList = mTerminalRules.lexicalize(word);
            for (Cell lex : lexList) {
                addToCell(mChart[i][i], lex, null, null);
            }
        }

    }

    /**
     * add cell ( terminal-rule ) to  table
     *
     * @param parent
     * @param cell
     * @param left
     * @param right
     */
    private void addToCell(Cell parent, Cell cell, Cell left, Cell right) {
        parent.addEntry(cell, left, right);
    }

    /**
     * A->BC
     * <p>
     * If there is an A somewhere in the input, then there must be a B followed by a C in the input
     * If the A spans from i to j in the input,then there must be a k such that i <k <j
     */
    public void fillChart() {

        for (int i = 1; i < mWordTokens; i++) {
            for (int j = 0; j < mWordTokens - i; j++) {
                for (int k = j; k < i; k++) {
                    combineCells(i, k, j);
                }
            }
        }

    }

    private void combineCells(int j, int k, int i) {

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
                Cell newCell = mNonTerminalRules.checkRule(c1, c2);
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
        for (int i = 0; i < mWordTokens; i++) {
            for (int j = 0; j < mWordTokens; j++) {
                if (j < i) {
                    System.out.print("\t");
                } else {
                    System.out.print(mChart[i][j].toString() + "\t");
                }
            }
            System.out.println();
        }

    }
//
//    public void printSolution() {
//        Cell fin = mChart[0][mWordTokens - 1];
//        fin.printSolution();
//    }

    public void getSolution(StringBuffer sb) {
        Cell fin = mChart[0][mWordTokens - 1];
        fin.getSolution(sb);
    }
}

