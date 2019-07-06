package com.github.senyast4745.speaking.sound;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static org.apache.http.HttpHeaders.USER_AGENT;

public class RequestClass {


    private static Logger log = Logger.getLogger(RequestClass.class.getSimpleName());

    private static final String baseUrl = "https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=ru&q=";


    public static void sendGet(String filename, String text) throws Exception {
        try {
            InputStream is = sendRequest(createUrl(text));

            String strPath = ParseTxtClass.getCurrentDirectory();
            strPath += "/mp3/";
            Path path = Paths.get(strPath);
            if(!Files.exists(path)){
                log.info("Creating folder " + strPath);
                Files.createDirectories(path);
            }

            File outFile = new File(strPath + filename + ".mp3");
            log.fine(outFile.getAbsolutePath());
            OutputStream outstream = new FileOutputStream(outFile);
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) > 0) {
                outstream.write(buffer, 0, len);
            }
            outstream.close();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    private static String createUrl(String text){

        String [] strings = text.split(" ");
        StringBuilder sb = new StringBuilder(baseUrl);
        for (int i = 0; i < strings.length - 1; i++) {
            sb.append(strings[i]);
            sb.append("+");
        }
        sb.append(strings[strings.length-1]);
        log.fine("URL to send "  + sb.toString());
        return sb.toString();
    }

    private static InputStream sendRequest(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        log.info("\nSending 'GET' request to URL : " + url);
        log.info("Response Code : " +
                response.getStatusLine().getStatusCode());

         return response.getEntity().getContent();
    }

}
