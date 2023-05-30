package ngordnet.main;
import edu.princeton.cs.algs4.In;
import ngordnet.browser.NgordnetQuery;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class WordNet {
    // wrapper for a graph

    private Graph graph;
    private NGramMap ngm;

    public WordNet(String synsetFile, String hyponymsFile, NGramMap ngm) {
        graph = new Graph();
        In sysnsetsReader = new In(synsetFile);
        In hyponymsReader = new In(hyponymsFile);

        while (sysnsetsReader.hasNextLine()) {
            String[] currLine = sysnsetsReader.readLine().split(",");
            int ID = Integer.parseInt(currLine[0]);
            String[] words = currLine[1].split(" ");
            for (int i = 0; i < words.length; i++) {
                graph.addNode(ID, words[i]);
            }

        }

        while (hyponymsReader.hasNextLine()) {
            String[] currLine = hyponymsReader.readLine().split(",");
            int mainID = Integer.parseInt(currLine[0]);

            for (int i = 1; i < currLine.length; i++) {
                int id = Integer.parseInt(currLine[i]);
                graph.addEdge(mainID, id);

            }
        }
        this.ngm = ngm;
    }
    /** getHyponyms for k=0, input is a single word **/
    public List<String> getHyponyms(String word) {
        List<String> returnList = new ArrayList<>();
        List<Integer> idList = graph.idList.get(word);
        HashSet<String> allHyponyms = new HashSet();
        if (idList == null) {
            return returnList;
        }
        for (int id: idList) {
            dfs(id, allHyponyms);
        }

        for (String hyp: allHyponyms) {
            returnList.add(hyp);
        }
        return returnList;
    }

    private void dfs(int id, HashSet<String> hypSet) {
        for (String word: graph.wordList.get(id)) {
            hypSet.add(word);
        }
        List<Integer> neighbors = graph.adjList.get(id);

        if (neighbors.isEmpty()) {
            return;

        } else {
            for (int neighborID : neighbors) {
                dfs(neighborID, hypSet);
            }
        }

    }
    /** getHyponyms for k=0, input is a list of words **/
    public List<String> getHyponyms(List<String> words) {
        List<Set<String>> allWordHyponyms = new ArrayList<>();

        for (String word: words) {
            List<String> hyps = getHyponyms(word);
            Set<String> hypSet =  new HashSet<>(hyps);
            allWordHyponyms.add(hypSet); // getting hyponyms of all the words passed in
        }

        HashSet<String> commonHyps = new HashSet<>();
        for (String hyponym: allWordHyponyms.get(0)) {
            commonHyps.add(hyponym); // using first list as comparison list
        }

        for (Set<String> set: allWordHyponyms) {
            commonHyps.retainAll(set); // compares commonHyps with hyps of that list
        }
        List<String> returnList = new ArrayList<>();
        for (String word: commonHyps) {
            returnList.add(word);
        }
        return returnList;
    }

    /** getHyponyms for k!=0, input is a single word **/
    public List<String> getHyponyms(String word, int k, int startYear, int endYear) {
        List<String> unfilteredHyps = getHyponyms(word);
        List<String> unfiltCopy = new ArrayList<>(unfilteredHyps);

        for (String unfiltered: unfilteredHyps) {
            if (ngm.countHistory(unfiltered, startYear, endYear).values().isEmpty()) {
                unfiltCopy.remove(unfiltered); // filtering for words that don't appear in those years
            }
        }

        TreeMap<Long, String> countWord = new TreeMap<>();
        for (String x: unfiltCopy) {
            long totalCount = countSum(x, startYear, endYear);
            countWord.put(totalCount, x);
        }
        List<String> returnList = new ArrayList<>();
        while (countWord.size() != 0 && returnList.size() != k) {
            returnList.add(countWord.remove(countWord.lastKey()));
        }
        return returnList;
    }

    /** HELPER **/
    private long countSum(String word, int startYear, int endYear) {
        TimeSeries wordHist = ngm.countHistory(word, startYear, endYear);
        long count = 0;
        for (int year: wordHist.keySet()) {
            count += wordHist.get(year);
        }
        return count;
    }

    public List<String> getHyponyms(List<String> words, int k, int startYear, int endYear) {
        List<String> unfilteredHyps = getHyponyms(words);
        List<String> unfiltCopy = new ArrayList<>(unfilteredHyps);

        for (String unfiltered : unfilteredHyps) {
            if (ngm.countHistory(unfiltered, startYear, endYear).values().isEmpty()) {
                unfiltCopy.remove(unfiltered); // filtering for words that don't appear in those years
            }
        }

        TreeMap<Double, String> countWord = new TreeMap<>();
        for (String x : unfiltCopy) {
            double totalCount = countSum(x, startYear, endYear);
            countWord.put(totalCount, x);
        }
        List<String> returnList = new ArrayList<>();
        while (countWord.size() != 0 && returnList.size() != k) {
            returnList.add(countWord.remove(countWord.lastKey()));
        }
        return returnList;
    }


}
