package com.huc.config;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 声明自定义日期转换器
 */
public class PmsCustomDateEditor extends PropertiesEditor {
    private final boolean allowEmpty;
    private final int exactDateLength;

    public PmsCustomDateEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
        this.exactDateLength = -1;
    }
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            this.setValue((Object)null);
        } else {
            if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
                throw new IllegalArgumentException("Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
            }

            try {
                this.setValue(this.parse(text));
            } catch (ParseException var3) {
                throw new IllegalArgumentException("Could not parse date: " + var3.getMessage(), var3);
            }
        }

    }

    public String getAsText() {
        Date value = (Date)this.getValue();
        return value != null ? this.format(value) : "";
    }

    /**
     * 解析日期数据
     * @param text
     * @return
     * @throws ParseException
     */
    public Date parse(String text) throws ParseException {
        return DateUtils.parseDateStrictly(text, Locale.CHINA, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy年MM月dd日 HH:mm", "yyyy-MM-dd HH", "yyyy年MM月dd日 HH", "yyyy-MM-dd", "yyyy年MM月dd日"});
    }

    /**
     * 将日期转为字符串数据
     * @param date
     * @return
     */
    public String format(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
