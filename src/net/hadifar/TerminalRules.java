package net.hadifar;

import java.util.ArrayList;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class TerminalRules {

    public ArrayList<TRule> mTRules = null;

    public TerminalRules() {
        mTRules = new ArrayList<TRule>();
    }

    public void addRule(String[] elements) {
        mTRules.add(TRule.makeRule(elements));
    }

    static class TRule {

        String leftHandSide = null;
        String rightHandSide = null;
        double probability = 0;

        private TRule() {}

        public static TRule makeRule(String[] elements) {
            TRule rule = new TRule();
            rule.probability = Double.parseDouble(elements[0]);
            rule.leftHandSide = elements[1];
            rule.rightHandSide = elements[2];
            return rule;
        }

        @Override
        public String toString() {
            StringBuffer ret = new StringBuffer();
            ret.append(leftHandSide);
            ret.append(" >> ");
            ret.append(rightHandSide);
            return ret.toString();
        }
    }


    public ArrayList<Cell> createLexical(String word) {

        ArrayList<Cell> lexs = new ArrayList<Cell>();

        for (TRule rule : mTRules) {

            if (rule.rightHandSide.equals(word)) {
                Cell lex = new Cell();
                lex.name = rule.rightHandSide;
                lex.pname = rule.leftHandSide;
                lexs.add(lex);
            }
        }
        return lexs;
    }

}
