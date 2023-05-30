package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.ArrayList;
import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet wn;
    private NGramMap ngm;
    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        List<String> returnList = new ArrayList<>();

        if (words.size() == 1 && k == 0) {
            returnList = wn.getHyponyms(words.get(0));

        } else if (words.size() > 1 && k == 0) {
            returnList = wn.getHyponyms(words);

        } else if (words.size() == 1 && k != 0) {
            returnList = wn.getHyponyms(words.get(0), k, startYear, endYear);

        } else {
            returnList = wn.getHyponyms(words, k, startYear, endYear);
        }
        return returnList.stream().sorted().toList().toString();
    }
}
