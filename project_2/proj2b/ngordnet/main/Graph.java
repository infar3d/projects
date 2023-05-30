package ngordnet.main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public Map<Integer, List<Integer>> adjList; // id --> children ids
    public Map<Integer, List<String>> wordList; // id --> word
    //public Map<Integer, List<String>> hypList; // id --> direct children (words)
    public Map<String, List<Integer>> idList; // word --> id FIX THIS FOR LIST FO WORDSSSS

    public int size;

    public Graph() {
        adjList = new HashMap<>();
        wordList = new HashMap<>();
        idList = new HashMap<>();
    }

    public void addNode(int ID, String word) {
        if (wordList.get(ID) != null) {
            wordList.get(ID).add(word);
        } else {
            List<String> y = new ArrayList<>();
            y.add(word);
            wordList.put(ID, y);

        }
        if (idList.get(word) != null) {
            idList.get(word).add(ID);
            List x = new ArrayList();
            adjList.put(ID, x);
            size++;

        } else {
            List x = new ArrayList();
            x.add(ID);
            idList.put(word,x);
            List z = new ArrayList();
            adjList.put(ID, z);
            size++;
        }

    }
    public void addEdge(int ID1, int ID2) {
        if (adjList.get(ID1) != null) {
            adjList.get(ID1).add(ID2);
        }

    }
    public int size() {
        return size;
    }

    public List<Integer> neighborList(int ID) {
        return adjList.get(ID);
    }

    /**  NOT NEEDED **/
    public boolean isHyponym(int wordID, int hypID) {
        // check if hyp is a hyponym of word

        for (int x: neighborList(wordID)) {
            if (x == hypID) {
                return true;
            }
            return isHyponym(x, hypID);
        }
        return false;
    }

}
