package com.github.senyast4745.speaking.sound;

import com.github.senyast4745.speaking.MainFrame;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ParseTxtClass {

    private static final String fileName = "input.txt";

    private static Logger log = Logger.getLogger(ParseTxtClass.class.getSimpleName());

    public static String getCurrentDirectory() {
        return MainFrame.selectedPath;
    }

    public static void readTxt() {
        String path = getCurrentDirectory();
        log.info("Current path: " + path);
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(path + "/" + fileName);
            log.info("Reading file " + path + "/" + fileName);
        } catch (IOException ex) {
            throw new RuntimeException("Can not find file \"input.txt\"");
        }


        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 200);
        BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                log.fine("Line " + line);
                String[] parsed = line.split(":");

                RequestClass.sendGet(parsed[0].trim(), parsed[1].trim());

            }
        } catch (IOException ex) {
            throw new RuntimeException("Problem with reading \"input.txt\"");
        } catch (Exception ex) {
            throw new RuntimeException("Problem with creating sound. You should have an Internet connection");
        }

    }

}
