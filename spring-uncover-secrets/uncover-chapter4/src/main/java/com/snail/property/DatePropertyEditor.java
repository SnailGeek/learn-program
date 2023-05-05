package com.snail.property;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePropertyEditor extends PropertyEditorSupport {
    private String datePattern;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getDatePattern());
            final Date date = simpleDateFormat.parse(text);
            setValue(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }
}
