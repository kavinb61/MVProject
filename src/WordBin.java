import java.util.ArrayList;

public class WordBin {
    private String filepath;
    private ArrayList<String> words;

    public WordBin(String filepath) {
        this.filepath = filepath;
        words = TextLib.splitIntoLines(filepath);
    }

    public int size() {
        return words.size();
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
        words = TextLib.splitIntoLines(filepath);
    }
}
