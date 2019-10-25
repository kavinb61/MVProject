import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class TextLib {
    public static ArrayList<TestCase> readTestCases(String filename) {
        Scanner scanner;
        ArrayList<TestCase> out = new ArrayList<>();

        try {
            scanner = new Scanner(new FileInputStream(filename), "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String text = line.substring(0, line.indexOf(","));
                boolean hate, mock, threat, discrim, overall;

                hate = toBool(processLine(line, 1));
                mock = toBool(processLine(line, 2));
                threat = toBool(processLine(line, 3));
                discrim = toBool(processLine(line, 4));
                overall = toBool(processLine(line, 5));


                TestCase t = new TestCase(text, new boolean[] {hate, mock, threat, discrim, overall});
                out.add(t);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }

        return out;
    }

    private static boolean toBool(int val) {
        return val == 1;
    }

    private static int processLine(String line, int loc) {
        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < loc; i++) {
            startIndex = line.indexOf(",", startIndex + 1);
        }

        endIndex = line.indexOf(",", startIndex + 1);
        if (endIndex == -1) endIndex = line.length();

        String num = line.substring(startIndex + 1, endIndex).trim();

        return Integer.parseInt(num);
    }

    public static String readFileAsString(String filename) {
        Scanner scanner;
        StringBuilder output = new StringBuilder();

        try {
            scanner = new Scanner(new FileInputStream(filename), "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                output.append(line.trim() + "\n");
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }

        return output.toString();
    }

    public static ArrayList<String> splitIntoLines(String filename) {
        Scanner scanner;
        ArrayList<String> lines = new ArrayList<>();

        try {
            scanner = new Scanner(new FileInputStream(filename), "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                lines.add(line);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }

        return lines;
    }


    public static ArrayList<String> splitIntoSentences(String text) {
        ArrayList<String> output = new ArrayList<>();

        Locale locale = Locale.US;
        BreakIterator breakIterator = BreakIterator.getSentenceInstance(locale);
        breakIterator.setText(text);

        int prevIndex = 0;
        int boundaryIndex = breakIterator.first();
        while (boundaryIndex != BreakIterator.DONE) {
            String sentence = text.substring(prevIndex, boundaryIndex).trim();
            if (sentence.length() > 0)
                output.add(sentence);
            prevIndex = boundaryIndex;
            boundaryIndex = breakIterator.next();
        }

        String sentence = text.substring(prevIndex).trim();
        if (sentence.length() > 0)
            output.add(sentence);

        return output;
    }
}
