package com.snail.chapter19.manager;

import java.util.Date;

public interface IQuoteService {
    Quote getQuate();

    Quote getQuateByDateTime(Date date);

    void saveQuate(Quote quote);

    void updateQuate(Quote quote);

    void deleteQuate(Quote quote);
}
