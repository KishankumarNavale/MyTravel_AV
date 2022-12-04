package com.av.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author 10151489
 */
public class LoggerFormatter extends Formatter {

    
    String format = "[date] [level] [name] [message]";
    String dateFormat = "yyyy-MM-dd HH:mm:ss";
    String prefix;

    public LoggerFormatter() {
    }
    
    public LoggerFormatter(String format) {
        this.format = format;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        
        if(format!=null){
            // to-do: replace placeholders ("date, level, ...") in format string
            String line = getFormat();
            SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
            String date = sdf.format(new Date(record.getMillis())); 
            line = line.replace("[date]", date);
            line = line.replace("[level]", record.getLevel().getName());
            line = line.replace("[name]", record.getLoggerName());
            line = line.replace("[message]", record.getMessage());
            sb.append(line);
        }else{
            String line = record.getMessage();
            sb.append(line);
        }
        return sb.toString();
    }
    
    public String format(Date date, String level, String loggerName, String message) {
        StringBuilder sb = new StringBuilder();
        
        // to-do: replace placeholders ("date, level, ...") in format string
        String line = getFormat();
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
        String dateStr = sdf.format(date); 
        line = line.replace("[date]", dateStr);
        line = line.replace("[level]", level);
        line = line.replace("[name]", loggerName);
        line = line.replace("[message]", message);
        sb.append(line);
        
        return sb.toString();
    }
}