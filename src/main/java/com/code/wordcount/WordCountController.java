package com.code.wordcount;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller class to provide functionality of add word and count of words
 */
@RestController
public class WordCountController {

    private ConcurrentHashMap<String, Integer> wordCountMap;

    Translator translator;

    public WordCountController(Translator translator) {
        this.translator = translator;
        wordCountMap = new ConcurrentHashMap<>();
    }

    /**
     * Endpoint that allows you to add words
     * First Checking if word is not null and alphabetic then translation
     * @param word
     */
    @RequestMapping(value = "/add-word/{word}", method = RequestMethod.POST)
    public void addWord(@PathVariable final String word) {
        if (StringUtils.isNotEmpty(word) && StringUtils.isAlpha(word)) {
            String englishWord = translator.translateWord(word);
            wordCountMap.compute(englishWord, (key, value) -> (value == null ? 1 : value + 1));
        }
    }

    /**
     * Endpoint to count how many times a given word was added
     * @param word
     * @return
     */
    @RequestMapping(value = "/word-count/{word}", method = RequestMethod.GET)
    public int getWordCount(@PathVariable final String word) {
        return wordCountMap.getOrDefault(word, 0);
    }
}
