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
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                while (!(line.substring(line.length()-1).equals("0") || line.substring(line.length()-1).equals("1"))) {
                    line = line + scanner.nextLine().trim();
                }

                int firstComma = getIndexOfNthComma(line, 1);
                String text = line.substring(0, firstComma);
                int hate, mock, threat, discrim, overall;

                hate = processLine(line, 1);
                mock = processLine(line, 2);
                threat = processLine(line, 3);
                discrim = processLine(line, 4);
                overall = processLine(line, 5);


                TestCase t = new TestCase(text, new int[] {hate, mock, threat, discrim, overall});
                out.add(t);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }

        return out;
    }

    private static int getIndexOfNthComma(String line, int n) {
        int counter = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.substring(i, i+1).equals(",")) {
                counter++;
                if (counter == n) {
                    return i;
                }
            }
            if (line.substring(i, i+1).equals("\"")) {
                i = line.indexOf("\"", i+1);
            }
        }
        return -1;
    }

    private static int processLine(String line, int loc) {
        int startIndex = getIndexOfNthComma(line, 1) - 1;

        for (int i = 0; i < loc; i++) {
            startIndex = line.indexOf(",", startIndex + 1);
        }

        int endIndex = line.indexOf(",", startIndex + 1);
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

    public static ArrayList<String> splitIntoWords(String text) {
        ArrayList<String> sentences = splitIntoSentences(text);
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            String[] words = sentences.get(i).split(" ");
            for (String w :
                    words) {
                out.add(w);
            }
        }

        return out;
    }

    public static int totalUppercase(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i, i + 1);
            if (letter.toUpperCase().equals(letter) && isLetter(letter)) count++;
        }

        return count;
    }

    public static int totalLowercase(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i, i + 1);
            if (letter.toLowerCase().equals(letter) && isLetter(letter)) count++;
        }

        return count;
    }

    private static boolean isLetter(String letter) {
        return "qwertyuiopasdfghjklzxcvbnm".contains(letter.toLowerCase());
    }
}
