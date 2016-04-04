package net.hadifar.core;

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
        mWords = sentence.split("\\s");
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

        CFG2CNF(mTerminalRules, mNonTerminalRules);

    }

    /**
     * Convert Context-free-grammar to Chomsky-normal-form
     * Just remove Union rules
     */
    private void CFG2CNF(TerminalRules mTerminalRules, NonTerminalRules mNonTerminalRules) {

        //new rules which added to terminals
        ArrayList<TerminalRules.TRule> newAddedRule = new ArrayList<>();
        ArrayList<NonTerminalRules.NTRule> newDeleteRule = new ArrayList<>();

        //iterate through non-terminals to find union-Rules
        for (NonTerminalRules.NTRule nRule : mNonTerminalRules.mNTRules) {
            //find union
            if (nRule.rightHandSide.size() == 1) {
                //replace all variant of rule with Terminals and added to newAddedRule
                for (TerminalRules.TRule tRule : mTerminalRules.mTRules) {
                    if (nRule.rightHandSide.get(0).equals(tRule.leftHandSide))
                        newAddedRule.add(TerminalRules.TRule.makeRule(new String[]{"" + nRule.probability * tRule.probability, nRule.leftHandSide, tRule.rightHandSide}));
                }
                //new delete rule added to deleted list
                newDeleteRule.add(nRule);
            }
        }

        //Union deleted && new Rules added
        mTerminalRules.mTRules.addAll(newAddedRule);
        mNonTerminalRules.mNTRules.removeAll(newDeleteRule);
    }


    /**
     * initializing table/chart of CKYParser with following structure
     * <p>
     * sentence : $$$$$$
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

            ArrayList<Cell> lexList = mTerminalRules.createLexical(word);
            for (Cell lex : lexList) {
                mChart[i][i].addEntry(lex, null, null);
            }
        }


    }

    public void fillChart() {

        for (int length = 1; length < mWordTokens; length++) {

            for (int i = 0; i < mWordTokens - length; i++) {

                for (int k = i; k < i + length; k++) {
                    combineCells(i, k, i + length);
                }
            }
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
                Cell newCell = mNonTerminalRules.createBinaryLexical(c1, c2);
                // if X -> Y Z in Rules
                if (newCell != null) {
                    System.out.println("find: " + c1.pname + " + " + c2.pname);

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

    public void getSolution(StringBuffer sb) {
        Cell fin = mChart[0][mWordTokens - 1];
        fin.getSolution(sb);
    }
}

