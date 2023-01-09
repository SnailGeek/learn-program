package com.geek.design.prototype;

/**
 * @program: SearchWord
 * @description:
 * @author: wangf-q
 * @date: 2023-01-09 09:47
 **/
public class SearchWord {
    private long lastUpdateTime;

    private String keyword;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
