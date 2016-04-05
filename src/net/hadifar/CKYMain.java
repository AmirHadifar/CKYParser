package net.hadifar;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

import net.hadifar.core.CKYParser;
import net.hadifar.helper.FileUtils;

import java.util.ArrayList;

public class CKYMain {

    public static void main(String args[]) {


        //read input sentence
        ArrayList<String> inputSentence = FileUtils.readFile("input.txt");

        //read terminal & non-terminal rules
        ArrayList<String> terminalRules = FileUtils.readFile("T-Rules.txt");
        ArrayList<String> nonTerminalRules = FileUtils.readFile("NT-Rules.txt");

        //initialize parser & build grammar from our rules
        CKYParser parser = new CKYParser();
        parser.buildGrammar(terminalRules, nonTerminalRules);

        //parse each sentence
        for (String sentence : inputSentence) {

            parser.ckyAlgorithm(sentence);

            StringBuffer sb = new StringBuffer();
            parser.getSolution(sb);
            String[] result = sb.toString().split("\n");
            System.out.println(sb.toString());
            FileUtils.WriteFile("penTreeBank.txt", sb.toString());
        }
    }
}
