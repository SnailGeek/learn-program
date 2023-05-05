package com.snail.property;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            final Date date = simpleDateFormat.parse(text);
            setValue(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
