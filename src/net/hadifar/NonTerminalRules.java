package net.hadifar;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */


import java.util.ArrayList;

public class NonTerminalRules {

    public ArrayList<NTRule> ntrules = null;

    public NonTerminalRules() {
        ntrules = new ArrayList<NTRule>();
    }

    /**
     * grammar rule 추가
     *
     * @param elements
     */
    public void addRule(String[] elements) {
        ntrules.add(NTRule.makeRule(elements));
    }

    static class NTRule {

        public String lhs = null;
        public ArrayList<String> rhs = null;

        private NTRule() {
            rhs = new ArrayList<String>();
        }

        public static NTRule makeRule(String[] elements) {
            NTRule rule = new NTRule();
            rule.lhs = elements[0];

            for (int i = 2; i < elements.length; i++) {
                rule.rhs.add(elements[i]);
            }
            return rule;
        }

        /**
         * non terminal rule을 standard form 으로 변환시 사용
         *
         * @param lhs
         * @param rhs1
         * @param rhs2
         * @return
         */
        public static NTRule makeRule(String lhs, String rhs1, String rhs2) {
            NTRule rule = new NTRule();
            rule.lhs = lhs;
            rule.rhs.add(rhs1);
            rule.rhs.add(rhs2);
            return rule;
        }

        @Override
        public String toString() {
            StringBuffer ret = new StringBuffer();
            ret.append(lhs);
            ret.append(" >> ");
            for (String s : rhs) {
                ret.append(s + " ");
            }
            return ret.toString();
        }
    }

    public Cell checkRule(Cell c1, Cell c2) {
        for (NTRule rule : ntrules) {
            if (rule.rhs.get(0).equals(c1.pname)
                    && rule.rhs.get(1).equals(c2.pname)) {
                // matched rule
                Cell cell = new Cell();
                cell.pname = rule.lhs;
                return cell;
            }
        }
        return null;
    }

    public void printRules() {
        System.out.println("-- Non Terminal Rules --");
        for (NTRule rule : ntrules) {
            System.out.println(rule.toString());
        }
    }
}

