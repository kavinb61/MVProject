import org.w3c.dom.Text;

import java.util.ArrayList;

public class Main {
    static WordBin hate = new WordBin("wordbins/hate.txt");
    static WordBin mock = new WordBin("wordbins/hate.txt");
    static WordBin threat = new WordBin("wordbins/hate.txt");
    static WordBin discrim = new WordBin("wordbins/hate.txt");

    public static void main(String[] args) {

        ArrayList<TestCase> testCases = TextLib.readTestCases("data/Data Set - Sheet1.csv");

        double hateSum = 0;
        double mockSum = 0;
        double threatSum = 0;
        double discrimSum = 0;
        double overallSum = 0;
        for (TestCase t : testCases) {
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
        for (String w :
                discrim.getWords()) {
            w = " " + w + " ";
            if (text.contains(w)) {
                return 1;
            }
        }

        return 0;
    }

    private static int getThreat(String text) {
        for (String w :
                threat.getWords()) {
            w = " " + w + " ";
            if (text.contains(w)) {
                return 1;
            }
        }

        return 0;
    }

    private static int getMock(String text) {

        for (String w :
                TextLib.splitIntoWords(text)) {
            if (TextLib.totalUppercase(w) > 1 && TextLib.totalLowercase(w) != 0) {
                System.out.println(w);
                return 1;
            }
        }

        return 0;
    }

    private static int getHate(String text) {
        for (String w :
                hate.getWords()) {
            w = " " + w + " ";
            if (text.contains(w)) {
                return 1;
            }
        }

        return 0;
    }
}

