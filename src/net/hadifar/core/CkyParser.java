package net.hadifar.core;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CkyParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CkyParser {

    private String[] words = null;
    private Cell[][] table = null;

    private Grammar grammar = null;

    private int wordTokens = 0;


    /**
     * default constructor for CkyParser
     * initialized terminal & non-terminals Objects
     */
    public CkyParser() {
        grammar = new Grammar();
    }

    /**
     * Pass input sentence for splitting it to arrays of String
     * number of words also set to wordTokens
     *
     * @param sentence input string
     */
    private void setSentence(String sentence) {
        words = sentence.split("\\s");
        wordTokens = this.words.length;
    }


    /**
     * build grammar with two ArrayList of terminal and non-terminal
     * set result to mTerminalRules & mNonTerminalRules objects
     *
     * @param terminals    rules
     * @param nonTerminals rules
     */
    public void buildGrammar(ArrayList<String> terminals, ArrayList<String> nonTerminals) {

        for (String terminalRule : terminals) {
            String[] splitWords = terminalRule.split("\\s");
            grammar.addTerminalRule(splitWords);
        }

        for (String nonTerminalRule : nonTerminals) {
            String[] splitWords = nonTerminalRule.split("\\s");
            grammar.addNonTerminalRule(splitWords);
        }

        CFG2CNF(grammar);

    }

    /**
     * Convert Context-free-grammar to Chomsky-normal-form
     * Just remove Union rules
     */
    private void CFG2CNF(Grammar grammar) {

        //TODO: remove epsilon rules & rules with more that two characters in rightHandSide
        //two iterator for iterate terminals & non-terminals
        ListIterator<Grammar.Rule> ntRuleListIterator = grammar.getNonTerminalRule().listIterator();
        ListIterator<Grammar.Rule> tRuleListIterator;

        while (ntRuleListIterator.hasNext()) {
            Grammar.Rule nRule = ntRuleListIterator.next();
            if (nRule.rightHandSide.size() == 1) {
                tRuleListIterator = grammar.getTerminalRule().listIterator();

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
     * Initializing table of CkyParser with following structure
     */
    private void initTable() {

        table = new Cell[wordTokens][];

        for (int i = 0; i < wordTokens; i++) {

            table[i] = new Cell[wordTokens];

            for (int j = i; j < wordTokens; j++) {
                table[i][j] = new Cell();
            }
        }

        initCell();
    }

    /**
     * Initialize entries of table with words of sentence
     */
    private void initCell() {

        for (int i = 0; i < wordTokens; i++) {

            String word = words[i];

            ArrayList<Cell> lexList = grammar.createUnaryRule(word);
            for (Cell lex : lexList) {
                table[i][i].addEntry(lex, null, null);
            }
        }
    }

    /**
     * CKY Algorithm
     */
    private void fillTable() {

        for (int length = 1; length < wordTokens; length++) { // length of span

            for (int i = 0; i < wordTokens - length; i++) { //start of span

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

        Cell cell1 = table[i][k];
        List<Cell> entries1 = cell1.getEntries();

        // find Y in cell[i][k]
        for (Cell c1 : entries1) {

            Cell cell2 = table[k + 1][j];
            List<Cell> entries2 = cell2.getEntries();

            // find Z in cell[k+1][j]
            for (Cell c2 : entries2) {

                // find X in Nonterminal rules
                Cell newCell = grammar.createBinaryRule(c1, c2);
                // if X -> Y Z in Rules
                if (newCell != null) {
                    // match
                    // addToCell(cell[i][j], X, Y, Z)
                    table[i][j].addEntry(newCell, c1, c2);
                }
            }
        }

    }

    /**
     * This is function print whole Table visually
     */
    public void printTable() {


        for (int i = 0; i < wordTokens; i++) {
            for (int j = 0; j < wordTokens; j++) {
                if (j < i) {
                    System.out.print("\t");
                } else {
                    System.out.print(table[i][j].toString() + "\t");
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
        Cell endCell = table[0][wordTokens - 1];
        endCell.getSolution(stringBuffer);
    }
}

