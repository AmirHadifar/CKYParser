package net.hadifar;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CKYMain {

    public static void main(String args[]) {

        String sentence = "We eat sushi with tuna";
        ArrayList<String> grammar = CKYMain.readFile("grammar_test.txt");

        // String sentence = Main.readStringFromFile("input.txt");
        // ArrayList<String> grammar = Main.readFile("grammar.txt");

        for (String rule : grammar) {
            System.out.println(rule);
        }

        CKYParser parser = new CKYParser(sentence);
        parser.setGrammar(grammar);

        parser.initChart();
        parser.fillChart();
        parser.printChart();

        StringBuffer sb = new StringBuffer();
        parser.getSolution(sb);
        System.out.println(sb.toString());
        CKYMain.WriteFile("output.txt", sb.toString());
    }

    public static ArrayList<String> readFile(String fileNm) {
        ArrayList<String> lines = null;

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(new File(fileNm));
            br = new BufferedReader(fr);
            lines = new ArrayList<String>();

            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (Exception ex) {
                }

            if (br != null)
                try {
                    br.close();
                } catch (Exception ex) {
                }
        }

        return lines;
    }

    public static void WriteFile(String fileNm, String text) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(new File(fileNm));
            bw = new BufferedWriter(fw);
            bw.write(text);
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (fw != null)
                try {
                    fw.close();
                } catch (Exception ex) {
                }
            ;
            if (bw != null)
                try {
                    bw.close();
                } catch (Exception ex) {
                }
            ;
        }
    }

    public static String readStringFromFile(String fileNm) {
        StringBuffer sb = new StringBuffer();
        List<String> lines = readFile(fileNm);
        for (String line : lines) {
            sb.append(line);
        }
        return sb.toString();
    }
}
