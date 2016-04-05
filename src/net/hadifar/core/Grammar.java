package net.hadifar.core;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Grammar {

    public List<Rule> mNonTerminalRule = null;
    public List<Rule> mTerminalRule = null;

    public Grammar() {
        mNonTerminalRule = new ArrayList<>();
        mTerminalRule = new ArrayList<>();
    }

    public void addNonTerminalRule(String[] elements) {
        mNonTerminalRule.add(Rule.makeRule(elements));
    }

    public void addTerminalRule(String[] elements) {
        mTerminalRule.add(Rule.makeRule(elements));
    }

    public static class Rule {

        String leftHandSide = null;
        BigDecimal probability = null;
        List<String> rightHandSide = null;

        private Rule() {
            rightHandSide = new ArrayList<>();
        }

        public static Rule makeRule(String[] elements) {
            Rule rule = new Rule();
            rule.probability = new BigDecimal(elements[0]);
            rule.leftHandSide = elements[1];

            for (int i = 2; i < elements.length; i++) {
                rule.rightHandSide.add(elements[i]);
            }
            return rule;
        }
    }


    /**
     * If two Rule ( c1 , c2 ) match to our Non-Terminals list
     * this function create new Cell
     *
     * @param c1 first rule
     * @param c2 second rule
     * @return if find any matched non-terminal rules , create new Cell and return it
     */
    public Cell createBinaryRule(Cell c1, Cell c2) {
        for (Rule rule : mNonTerminalRule) {
            if (rule.rightHandSide.get(0).equals(c1.pname) && rule.rightHandSide.get(1).equals(c2.pname)) {
                Cell cell = new Cell();
                cell.pname = rule.leftHandSide;
                cell.probability = c1.probability.multiply(c2.probability);
                return cell;
            }
        }
        return null;
    }


    /**
     * Create unary rule if find it in terminal rules
     * @param word of our input sentence
     * @return if find any matched terminal rules , create new Cell and return it
     */
    public ArrayList<Cell> createUnaryRule(String word) {

        ArrayList<Cell> equavalentUnaryRule = new ArrayList<>();

        for (Rule rule : mTerminalRule) {
            if (rule.rightHandSide.get(0).equals(word)) {
                Cell lex = new Cell();
                lex.name = rule.rightHandSide.get(0);
                lex.pname = rule.leftHandSide;
                lex.probability = rule.probability;

                equavalentUnaryRule.add(lex);
            }
        }
        return equavalentUnaryRule;
    }

}

