package net.hadifar.helper;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Amir on 3/28/2016 AD
 * Project : CKYParser
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class FileUtils {

    /**
     * read input text from file (e.g input.txt)
     *
     * @param fileNm name of the input file
     * @return Arrays of String separated by newLine
     */
    public static ArrayList<String> readFile(String fileNm) {
        ArrayList<String> lines = null;

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(new File(fileNm));
            br = new BufferedReader(fr);
            lines = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            if (br != null)
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

        return lines;
    }

    /**
     * write output text to file (e.g penTreeBank.txt)
     *
     * @param fileNm name of output file
     * @param text written text
     */
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (bw != null)
                try {
                    bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
