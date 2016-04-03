package net.hadifar;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

import net.hadifar.helper.FileUtils;

import java.util.ArrayList;

public class CKYMain {

    public static void main(String args[]) {


        ArrayList<String> inputSentence = FileUtils.readFile("input.txt");

        ArrayList<String> terminalRules = FileUtils.readFile("T-Rules.txt");
        ArrayList<String> nonTerminalRules = FileUtils.readFile("NT-Rules.txt");

        CKYParser parser = new CKYParser();
        parser.buildGrammar(terminalRules, nonTerminalRules);


        for (String sentence : inputSentence) {

            parser.setSentence(sentence);

            parser.initChart();
            parser.fillChart();
            parser.printChart();

            StringBuffer sb = new StringBuffer();
            parser.getSolution(sb);
            System.out.println(sb.toString());
//            FileUtils.WriteFile("output.txt", sb.toString());
        }
    }
}
