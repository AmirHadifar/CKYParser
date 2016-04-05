package net.hadifar.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class TerminalRules {

    public List<TRule> mTRules = null;

    public TerminalRules() {
        mTRules = new ArrayList<>();
    }

    public void addRule(String[] elements) {
        mTRules.add(TRule.makeRule(elements));
    }

    public static class TRule {

        String leftHandSide = null;
        String rightHandSide = null;
        double probability = 0;

        TRule() {
        }

        public static TRule makeRule(String[] elements) {
            TRule rule = new TRule();
            rule.probability = Double.parseDouble(elements[0]);
            rule.leftHandSide = elements[1];
            rule.rightHandSide = elements[2];
            return rule;
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
