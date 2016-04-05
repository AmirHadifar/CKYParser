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

public class NonTerminalRules {

    public List<NTRule> mNTRules = null;

    public NonTerminalRules() {
        mNTRules = new ArrayList<NTRule>();
    }

    public void addRule(String[] elements) {
        mNTRules.add(NTRule.makeRule(elements));
    }

    public static class NTRule {

        String leftHandSide = null;
        ArrayList<String> rightHandSide = null;
        BigDecimal probability;

        private NTRule() {
            rightHandSide = new ArrayList<String>();
        }

        public static NTRule makeRule(String[] elements) {
            NTRule rule = new NTRule();
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
     * @param c1
     * @param c2
     * @return
     */
    public Cell createBinaryLexical(Cell c1, Cell c2) {
        for (NTRule rule : mNTRules) {
            if (rule.rightHandSide.get(0).equals(c1.pname) && rule.rightHandSide.get(1).equals(c2.pname)) {
                // matched rule
                Cell cell = new Cell();
                cell.pname = rule.leftHandSide;
                cell.probability = c1.probability.multiply(c2.probability);
                return cell;
            }
        }
        return null;
    }

}

