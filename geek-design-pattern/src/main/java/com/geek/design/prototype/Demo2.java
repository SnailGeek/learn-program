package com.geek.design.prototype;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: Demo2
 * @description:
 * @author: wangf-q
 * @date: 2023-01-09 10:01
 **/
public class Demo2 {
    private HashMap<String, SearchWord> currentKeywords = new HashMap<>();

    public void refresh() {
        //newKeywords 构建成本较高
        HashMap<String, SearchWord> newKeywords = new LinkedHashMap<>();
        List<SearchWord> toBeUpdatedSearchWords = getSearchKeywords();
        for (SearchWord searchWord : toBeUpdatedSearchWords) {
            newKeywords.put(searchWord.getKeyword(), searchWord);
        }
        currentKeywords = newKeywords;
    }

    private List<SearchWord> getSearchKeywords() {
        return null;
    }
}
