package net.hadifar.core;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CKYParser {

    private String[] mWords = null;
    private Cell[][] mTable = null;

    private Grammar mGrammar = null;

    private int mWordTokens = 0;


    /**
     * default constructor for CKYParser
     * initialized terminal & non-terminals Objects
     */
    public CKYParser() {
        mGrammar = new Grammar();
    }

    /**
     * Pass input sentence for splitting it to arrays of String
     * number of words also set to mWordTokens
     *
     * @param sentence input string
     */
    private void setSentence(String sentence) {
        mWords = sentence.split("\\s");
        mWordTokens = this.mWords.length;
    }


    /**
     * build grammar with two ArrayList of terminal and non-terminal
     * set result to mTerminalRules & mNonTerminalRules objects
     *
     * @param terminals rules
     * @param nonTerminals rules
     */
    public void buildGrammar(ArrayList<String> terminals, ArrayList<String> nonTerminals) {

        for (String terminalRule : terminals) {
            String[] splitWords = terminalRule.split("\\s");
            mGrammar.addTerminalRule(splitWords);
        }

        for (String nonTerminalRule : nonTerminals) {
            String[] splitWords = nonTerminalRule.split("\\s");
            mGrammar.addNonTerminalRule(splitWords);
        }

        CFG2CNF(mGrammar);

    }

    /**
     * Convert Context-free-grammar to Chomsky-normal-form
     * Just remove Union rules
     *
     */
    private void CFG2CNF(Grammar grammar) {

        //TODO: remove epsilon rules & rules with more that two characters in rightHandSide
        //two iterator for iterate terminals & non-terminals
        ListIterator<Grammar.Rule> ntRuleListIterator = grammar.mNonTerminalRule.listIterator();
        ListIterator<Grammar.Rule> tRuleListIterator;

        while (ntRuleListIterator.hasNext()) {
            Grammar.Rule nRule = ntRuleListIterator.next();
            if (nRule.rightHandSide.size() == 1) {
                tRuleListIterator = grammar.mTerminalRule.listIterator();

                while (tRuleListIterator.hasNext()) {
                    Grammar.Rule tRule = tRuleListIterator.next();
                    if (nRule.rightHandSide.get(0).equals(tRule.leftHandSide)) {
                        String s = nRule.probability.multiply(tRule.probability).toString();
                        tRuleListIterator.add(Grammar.Rule.makeRule(new String[]{s, nRule.leftHandSide, tRule.rightHandSide.get(0)}));
                    }
                }
                ntRuleListIterator.remove();
            }
        }
    }


    /**
     * CKY algorithm , Take input sentence and run other part of PARSER
     *
     * @param sentence come from .txt
     */
    public void ckyAlgorithm(String sentence) {
        setSentence(sentence);
        initTable();
        fillTable();
    }

    /**
     * Initializing table of CKYParser with following structure
     */
    private void initTable() {

        mTable = new Cell[mWordTokens][];

        for (int i = 0; i < mWordTokens; i++) {

            mTable[i] = new Cell[mWordTokens];

            for (int j = i; j < mWordTokens; j++) {
                mTable[i][j] = new Cell();
            }
        }

        initCell();
    }

    /**
     * Initialize entries of table with words of sentence
     */
    private void initCell() {

        for (int i = 0; i < mWordTokens; i++) {

            String word = mWords[i];

            ArrayList<Cell> lexList = mGrammar.createUnaryRule(word);
            for (Cell lex : lexList) {
                mTable[i][i].addEntry(lex, null, null);
            }
        }
    }

    /**
     * CKY Algorithm
     */
    private void fillTable() {

        for (int length = 1; length < mWordTokens; length++) { // length of span

            for (int i = 0; i < mWordTokens - length; i++) { //start of span

                for (int k = i; k < i + length; k++) {
                    combineCells(i, k, i + length);
                }
            }
        }

    }


    /**
     * Combine two Cell into one
     *
     * @param i start of span
     * @param k indicator of span
     * @param j end of span
     */
    private void combineCells(int i, int k, int j) {

        Cell cell1 = mTable[i][k];
        List<Cell> entries1 = cell1.getEntries();

        // find Y in cell[i][k]
        for (Cell c1 : entries1) {

            Cell cell2 = mTable[k + 1][j];
            List<Cell> entries2 = cell2.getEntries();

            // find Z in cell[k+1][j]
            for (Cell c2 : entries2) {

                // find X in Nonterminal rules
                Cell newCell = mGrammar.createBinaryRule(c1, c2);
                // if X -> Y Z in Rules
                if (newCell != null) {
                    // match
                    // addToCell(cell[i][j], X, Y, Z)
                    mTable[i][j].addEntry(newCell, c1, c2);
                }
            }
        }

    }

    /**
     * This is function print whole Table visually
     */
    public void printTable() {


        for (int i = 0; i < mWordTokens; i++) {
            for (int j = 0; j < mWordTokens; j++) {
                if (j < i) {
                    System.out.print("\t");
                } else {
                    System.out.print(mTable[i][j].toString() + "\t");
                }
            }
            System.out.println();
        }

    }

    /**
     * Put solution as String into stringBuffer
     *
     * @param stringBuffer load result into into it
     */
    public void getSolution(StringBuffer stringBuffer) {
        Cell endCell = mTable[0][mWordTokens - 1];
        endCell.getSolution(stringBuffer);
    }
}

