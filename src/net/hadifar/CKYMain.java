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


        ArrayList<String> inputSentence = FileUtils.readFile("input2.txt");
        ArrayList<String> grammar = FileUtils.readFile("grammar_test.txt");

        // String sentence = Main.readStringFromFile("input.txt");
        // ArrayList<String> grammar = Main.readFile("grammar.txt");


        for (String sentence : inputSentence) {

            CKYParser parser = new CKYParser(sentence);
            parser.setGrammar(grammar);

            parser.initChart();
            parser.fillChart();
            parser.printChart();

            StringBuffer sb = new StringBuffer();
            parser.getSolution(sb);
            System.out.println(sb.toString());
            FileUtils.WriteFile("output.txt", sb.toString());
        }
    }
}
