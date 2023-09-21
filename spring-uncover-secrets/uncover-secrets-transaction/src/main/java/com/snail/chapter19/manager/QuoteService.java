package com.snail.chapter19.manager;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class QuoteService implements IQuoteService {
    private JdbcTemplate jdbcTemplate;

    @Override
    public Quote getQuate() {
        return (Quote) getJdbcTemplate().queryForObject("", (RowMapper<Object>) (resultSet, i) -> {
            Quote quote = new Quote();
            return quote;
        });
    }

    @Override
    public Quote getQuateByDateTime(Date date) {
        throw new RuntimeException();
    }

    @Override
    public void saveQuate(Quote quote) {
        throw new RuntimeException();
    }

    @Override
    public void updateQuate(Quote quote) {
        throw new RuntimeException();
    }

    @Override
    public void deleteQuate(Quote quote) {
        throw new RuntimeException();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
