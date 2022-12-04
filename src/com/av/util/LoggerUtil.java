package com.av.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;




public class LoggerUtil{
           
    public static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());
    
    public static String logDirSuccess="E:\\APP\\LOGS\\SUCCESS"; 
    public static String logDirError="E:\\APP\\LOGS\\ERROR"; 
    
    public static PrintWriter logWriterSuccess=null;
    public static PrintWriter logWriterError=null;
    
    public static LoggerCreator fileLoggerSuccess=null;
    public static LoggerCreator fileLoggerError=null;
    
    public LoggerCreator loggerSuccessUtil() throws FileNotFoundException, IOException, InterruptedException{
        Date date = new Date();
        if((logWriterSuccess==null)){
            logWriterSuccess = loggerSuccessUtil(new File(buildFilePath(logDirSuccess, date, "SUCCESS")));
            
            if(fileLoggerSuccess==null){
                LoggerFormatter formatter= new LoggerFormatter();
                fileLoggerSuccess = new LoggerCreator( logWriterSuccess, formatter);
            }    
                
            }
        return fileLoggerSuccess;
        }
     
    
    
    public LoggerCreator loggerErrorUtil() throws FileNotFoundException, IOException, InterruptedException{
        Date date = new Date();
        if((logWriterError==null)){
            logWriterError = loggerErrorUtil(new File(buildFilePath(logDirError, date, "ERROR")));
            if(fileLoggerError==null){
                LoggerFormatter formatter= new LoggerFormatter();
                fileLoggerError = new LoggerCreator( logWriterError, formatter);  
            } 
        }
        return fileLoggerError;
    }
    
    
    public PrintWriter loggerSuccessUtil(File file) throws FileNotFoundException, IOException{
        file.getParentFile().mkdirs();
        PrintWriter writer = new PrintWriter(new FileWriter(file), true);
        return writer;
    }
    
    
    public PrintWriter loggerErrorUtil(File file) throws FileNotFoundException, IOException{
        System.out.println("writer: " + file.getAbsolutePath());
        file.getParentFile().mkdirs();
        PrintWriter writer = new PrintWriter(new FileWriter(file), true);
        return writer;
    }
    
    public static String buildFilePath(String dir, Date date, String suffix){
        String fileName = buildFileName(date, suffix);
        String path = "";
        
        if(dir!=null && !dir.isEmpty())
            path = dir + File.separator + fileName;
        else
            path = fileName;
        return path;
    }
    
    
    public static String buildFileName(Date date, String prefix){
        SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String strDate = myDate.format(date);
        String fileName = prefix + "-" + strDate  +".log";
        return fileName;
    }
    
    
    /*public static void main(String[] args) {  
    try {  
        
        System.out.println("INSIDE MAIN");
         LoggerUtil log = new LoggerUtil();
         log.LoggerUtil();
         log.fileLoggerSuccess.log("LOG CREATED", "LOG CREATED SUCCESFFULLY");
         log.fileLoggerError.log("LOG NOT CREATED", "LOG NOT CREATED SUCCESFFULLY");

    } catch (Exception e) {  
        e.printStackTrace();  
    } 


    }*/
}
             

             