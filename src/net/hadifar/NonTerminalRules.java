package net.hadifar;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */


import java.util.ArrayList;

public class NonTerminalRules {

    public ArrayList<NTRule> mNTRules = null;

    public NonTerminalRules() {
        mNTRules = new ArrayList<NTRule>();
    }

    public void addRule(String[] elements) {
        mNTRules.add(NTRule.makeRule(elements));
    }

    static class NTRule {

        String leftHandSide = null;
        ArrayList<String> rightHandSide = null;
        double probability = 0;

        private NTRule() {
            rightHandSide = new ArrayList<String>();
        }

        public static NTRule makeRule(String[] elements) {
            NTRule rule = new NTRule();
            rule.probability = Double.parseDouble(elements[0]);
            rule.leftHandSide = elements[1];

            for (int i = 2; i < elements.length; i++) {
                rule.rightHandSide.add(elements[i]);
            }
            return rule;
        }


//        public static NTRule makeRule(String lhs, String rhs1, String rhs2) {
//            NTRule rule = new NTRule();
//            rule.leftHandSide = lhs;
//            rule.rightHandSide.add(rhs1);
//            rule.rightHandSide.add(rhs2);
//            return rule;
//        }

        @Override
        public String toString() {
            StringBuffer ret = new StringBuffer();
            ret.append(leftHandSide);
            ret.append(" >> ");
            for (String s : rightHandSide) {
                ret.append(s + " ");
            }
            return ret.toString();
        }
    }

    public Cell createLexical(Cell c1, Cell c2) {
        for (NTRule rule : mNTRules) {
            if (rule.rightHandSide.get(0).equals(c1.pname) && rule.rightHandSide.size() == 1) {
                Cell cell = new Cell();
                cell.pname = rule.leftHandSide;
                c1.addEntry(cell, null, null);
                return null;
            } else if (rule.rightHandSide.get(0).equals(c1.pname) && rule.rightHandSide.get(1).equals(c2.pname)) {
                // matched rule
                Cell cell = new Cell();
                cell.pname = rule.leftHandSide;
                return cell;
            }
        }
        return null;
    }

//    public void printRules() {
//        System.out.println("-- Non Terminal Rules --");
//        for (NTRule rule : mNTRules) {
//            System.out.println(rule.toString());
//        }
//    }
}

