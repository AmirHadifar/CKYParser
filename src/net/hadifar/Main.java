package net.hadifar;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CkyParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

import net.hadifar.core.CkyParser;
import net.hadifar.helper.FileUtils;

import java.util.ArrayList;

public class Main {

    /**
     * Take 3 .txt file as Input and then
     * Create Parser and pass nonTerminal & Terminal rule as arg to buildGrammar function
     * for each sentence in your input.txt it calls parser.ckyAlgorithm
     * At the end write result in penTreeBank.txt file
     */
    public static void main(String args[]) {

        //read input sentence
        ArrayList<String> inputSentence = FileUtils.readFile("input.txt");

        //read terminal & non-terminal rules
        ArrayList<String> terminalRules = FileUtils.readFile("T-Rules.txt");
        ArrayList<String> nonTerminalRules = FileUtils.readFile("NT-Rules.txt");

        //initialize parser & build grammar from our rules
        CkyParser parser = new CkyParser();
        parser.buildGrammar(terminalRules, nonTerminalRules);

        //parse each sentence
        for (String sentence : inputSentence) {

            parser.ckyAlgorithm(sentence);

            StringBuffer sb = new StringBuffer();
            parser.getSolution(sb);
            System.out.println(sentence + "\n" + sb.toString());
            FileUtils.WriteFile("output.txt", sb.toString());
        }
    }
}
