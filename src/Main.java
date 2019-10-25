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
        for (TestCase t: testCases) {
            boolean[] predictedVals = getSemanticVals(t.getText());
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
        System.out.println("Hate accuracy: " + hateSum/testCases.size());
        System.out.println("Mock accuracy: " + mockSum/testCases.size());
        System.out.println("Threat accuracy: " + threatSum/testCases.size());
        System.out.println("Discrim accuracy: " + discrimSum/testCases.size());
        System.out.println("Overall accuracy: " + overallSum/testCases.size());

    }

    public static boolean[] getSemanticVals(String text) {
        boolean isNegative = false;
        boolean[] out = {
                false, false, false, false, false
        };

        for (String w :
                hate.getWords()) {
            if (text.contains(w)) {
                isNegative = true;
                out[0] = true;
            }
        }

        for (String w :
                mock.getWords()) {
            if (text.contains(w)) {
                isNegative = true;
                out[1] = true;
            }
        }

        for (String w :
                threat.getWords()) {
            if (text.contains(w)) {
                isNegative = true;
                out[2] = true;
            }
        }

        for (String w :
                discrim.getWords()) {
            if (text.contains(w)) {
                isNegative = true;
                out[3] = true;
            }
        }

        out[4] = isNegative;
        return out;
    }

}
