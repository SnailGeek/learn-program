package com.geek.design.prototype;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: Demo
 * @description:
 * @author: wangf-q
 * @date: 2023-01-09 09:46
 **/
public class Demo {
    private ConcurrentHashMap<String, SearchWord> currentKeyWords = new ConcurrentHashMap<>();
    private long lastUpdateTime = -1;

    public void refresh() {
        // 这种情况下，Map中会存在两种版本的数据
        List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
        long maxNewUpdatedTime = lastUpdateTime;
        for (SearchWord searchWord : toBeUpdatedSearchWords) {
            if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
                maxNewUpdatedTime = searchWord.getLastUpdateTime();
            }
            if (currentKeyWords.containsKey(searchWord.getKeyword())) {
                currentKeyWords.replace(searchWord.getKeyword(), searchWord);
            } else {
                currentKeyWords.put(searchWord.getKeyword(), searchWord);
            }
        }
        lastUpdateTime = maxNewUpdatedTime;
    }

    private List<SearchWord> getSearchWords(long lastUpdateTime) {
        return null;
    }
}
