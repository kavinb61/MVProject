import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    static WordBin hate = new WordBin("wordbins/hate.txt");
    static WordBin threatVerbs = new WordBin("wordbins/threatVerbs.txt");
    static WordBin races = new WordBin("wordbins/race.txt");

    public static void main(String[] args) {

        ArrayList<TestCase> testCases = TextLib.readTestCases("data/Test Suite - Sheet1.csv");

        double hateSum = 0;
        double mockSum = 0;
        double threatSum = 0;
        double discrimSum = 0;
        double overallSum = 0;
        for (TestCase t : testCases) {
            System.out.println(t.getText());
            int[] predictedVals = getSemanticVals(t.getText());
            if (t.getSemanticVal("hate") == predictedVals[0]) {
                hateSum++;
            }
            if (t.getSemanticVal("mock") == predictedVals[1]) {
                mockSum++;
            }
            if (t.getSemanticVal("threat") == predictedVals[2]) {
                threatSum++;
            }
            if (t.getSemanticVal("discrim") == predictedVals[3]) {
                discrimSum++;
            }
            if (t.getSemanticVal("overall") == predictedVals[4]) {
                overallSum++;
            }
        }
        System.out.println("Hate accuracy: " + hateSum / testCases.size());
        System.out.println("Mock accuracy: " + mockSum / testCases.size());
        System.out.println("Threat accuracy: " + threatSum / testCases.size());
        System.out.println("Discrim accuracy: " + discrimSum / testCases.size());
        System.out.println("Overall accuracy: " + overallSum / testCases.size());

    }

    public static int[] getSemanticVals(String text) {

        int[] out = {
                0, 0, 0, 0, 0
        };

        out[0] = getHate(text);
        out[1] = getMock(text);
        out[2] = getThreat(text);
        out[3] = getDiscrim(text);
//        out[4] = (out[0] + out[1] + out[2] + out[3]) / 4;

        if ((out[0] + out[1] + out[2] + out[3]) > 0) out[4] = 1;
        return out;
    }

    private static int getDiscrim(String text) {
        text = text.toLowerCase();
        for (String w :
                hate.getWords()) {
            w = " " + w + " ";
            if (text.contains(w)) {
                System.out.println(w);
                return 1;
            }
        }

        return 0;
    }

    private static int getThreat(String text) {
        ArrayList<String> sentences = TextLib.splitIntoSentences(text.toLowerCase());
        for (String sentence : sentences) {
            if (isActiveThreat(sentence)) System.out.println(sentence);return 1;
        }

        return 0;
    }

    private static boolean isActiveThreat(String sentence) {
        for (String verb : threatVerbs.getWords()) {
            verb = " " + verb + " ";
            verb = verb.toLowerCase();
            if (sentence.contains(verb)) {
                for (String slur : races.getWords()) {
                    slur = " " + slur + " ";
                    slur = slur.toLowerCase();
                    if (!slur.equals("slur") && sentence.contains(slur)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static int getMock(String text) {

        for (String w :
                TextLib.splitIntoWords(text)) {
            if (!allLetters(w)) return 0;

            if (TextLib.totalUppercase(w) > 1 && TextLib.totalLowercase(w) != 0) {
                System.out.println(w);
                return 1;
            }
        }

        return 0;
    }

    private static boolean allLetters(String w) {
        for (int i = 0; i < w.length(); i++) {
            if (!isLetter(w)) return false;
        }

        return true;
    }

    private static boolean isLetter(String w) {
        return  "qwertyuiopasdfghjklzxcvbnm".contains(w.toLowerCase());
    }

    private static int getHate(String text) {
        text = text.toLowerCase();
        for (String w :
                hate.getWords()) {
            w = " " + w + " ";
            if (text.contains(w)) {
                System.out.println(w);
                return 1;
            }
        }

        return 0;
    }
}

