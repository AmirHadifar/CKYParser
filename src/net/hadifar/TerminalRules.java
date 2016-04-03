package net.hadifar;

import java.util.ArrayList;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class TerminalRules {

    public ArrayList<TRule> trules = null;

    public TerminalRules() {
        trules = new ArrayList<TRule>();
    }

    public void addRule(String[] elements) {
        trules.add(TRule.makeRule(elements));
    }

    static class TRule {

        public String lhs = null;
        public String rhs = null;

        private TRule() {
        }

        public static TRule makeRule(String[] elements) {
            TRule rule = new TRule();
            rule.lhs = elements[0];
            rule.rhs = elements[2];
            return rule;
        }

        @Override
        public String toString() {
            StringBuffer ret = new StringBuffer();
            ret.append(lhs);
            ret.append(" >> ");
            ret.append(rhs);
            return ret.toString();
        }
    }


    public ArrayList<Cell> lexicalize(String word) {

        ArrayList<Cell> lexs = new ArrayList<Cell>();
        for (TRule trule : trules) {
            if (trule.rhs.equals(word)) {
                Cell lex = new Cell();
                lex.name = trule.rhs;
                lex.pname = trule.lhs;
                lexs.add(lex);
            }
        }
        return lexs;
    }

//    public void printRules() {
//        System.out.println("-- Terminal Rules --");
//        for (TRule rule : trules) {
//            System.out.println(rule.toString());
//        }
//    }
}
