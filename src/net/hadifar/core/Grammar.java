package net.hadifar.core;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CkyParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class Grammar {

    private List<Rule> nonTerminalRule = null;
    private List<Rule> terminalRule = null;


    Grammar() {
        nonTerminalRule = new ArrayList<>();
        terminalRule = new ArrayList<>();
    }

    /**
     * add raw input data to our non-terminal's List
     *
     * @param elements array of string contains probability , leftHandSide rule , rightHandSide rule(s)
     */
    public void addNonTerminalRule(String[] elements) {
        nonTerminalRule.add(Rule.makeRule(elements));
    }

    /**
     * add raw input data to our terminal's List
     *
     * @param elements array of string contains probability , leftHandSide rule , rightHandSide rule
     */
    public void addTerminalRule(String[] elements) {
        terminalRule.add(Rule.makeRule(elements));
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
     * this function create new Cell and return it
     *
     * @param c1 first rule
     * @param c2 second rule
     * @return if find any matched non-terminal rules , create new Cell and return it
     */
    public Cell createBinaryRule(Cell c1, Cell c2) {
        for (Rule rule : nonTerminalRule) {
            if (rule.rightHandSide.get(0).equals(c1.getNonTerminalSymbol()) && rule.rightHandSide.get(1).equals(c2.getNonTerminalSymbol())) {
                Cell cell = new Cell();
                cell.setNonTerminalSymbol(rule.leftHandSide);
                cell.setProbability(c1.getProbability().multiply(c2.getProbability()));
                return cell;
            }
        }
        return null;
    }


    /**
     * Create unary rule if find it in terminal rules
     *
     * @param word of our input sentence
     * @return if find any matched terminal rules , create new Cell and return it
     */
    public ArrayList<Cell> createUnaryRule(String word) {

        ArrayList<Cell> equavalentUnaryRule = new ArrayList<>();

        for (Rule rule : terminalRule) {
            if (rule.rightHandSide.get(0).equals(word)) {
                Cell lex = new Cell();
                lex.setTerminalSymbol(rule.rightHandSide.get(0));
                lex.setNonTerminalSymbol(rule.leftHandSide);
                lex.setProbability(rule.probability);

                equavalentUnaryRule.add(lex);
            }
        }
        return equavalentUnaryRule;
    }

    public List<Rule> getNonTerminalRule() {
        return nonTerminalRule;
    }

    public void setNonTerminalRule(List<Rule> nonTerminalRule) {
        this.nonTerminalRule = nonTerminalRule;
    }

    public List<Rule> getTerminalRule() {
        return terminalRule;
    }

    public void setTerminalRule(List<Rule> terminalRule) {
        this.terminalRule = terminalRule;
    }
}

