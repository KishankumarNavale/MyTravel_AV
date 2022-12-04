package com.av.util;


import java.io.PrintWriter;
import java.util.Date;

public class LoggerCreator {    
   public PrintWriter jlogger;
   public LoggerFormatter formatter;
   String name = "";

    public LoggerCreator(PrintWriter writer) {
        
    }

    public LoggerCreator(PrintWriter writer, LoggerFormatter formatter) {
        this.jlogger = writer;
        this.formatter= formatter;
    }
   
   PrintWriter getBaseLogger(){
       return jlogger;
   }
   
    public void error(Object msg){
        log("ERROR", msg);
    }

    public void error(Object msg, Throwable t){
        log("ERROR", msg, t);
    }   

    public void info(Object msg){
        log("INFO", msg);
    }
    
    
    
    public void log(Object level, Object msg){
        log(level, msg, null);
    }

    String getExceptionString(Throwable t){
        StringBuilder sb = new StringBuilder();
        
        if(t!=null){
            
            sb.append("\n").append(t.getClass().getName()).append(": ").append(t.getLocalizedMessage());        
            
            for(StackTraceElement ste : t.getStackTrace()){
                 sb.append("\n").append(ste);
            }
        }
        
        if(t.getCause()!=null){
            sb.append("\n").append("cause: ");
            sb.append(getExceptionString(t.getCause()));
        }
        return sb.toString();
    }
    
    public void log(Object level, Object msg, Throwable t){
        StringBuilder sb = new StringBuilder();
        
        sb.append(msg);
        if(t!=null){
            sb.append("\n").append(getExceptionString(t));        
        }
        
        String line = formatter.format(new Date(), String.valueOf(level), name, sb.toString());
        
        jlogger.println(line);
    }
    
}
