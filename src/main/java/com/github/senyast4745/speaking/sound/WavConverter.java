package com.github.senyast4745.speaking.sound;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WavConverter {

    private static Logger log = Logger.getLogger(WavConverter.class.getSimpleName());

    private static final String path = ParseTxtClass.getCurrentDirectory();
    private static final String oldPath = path + "/mp3";
    private static final String newPath = path + "/wav";

    public static void mainConverter() throws IOException, UnsupportedAudioFileException, JavaLayerException {
        Path path = Paths.get(newPath);
        if(!Files.exists(path)){
            log.info("Create directory " + newPath);
            Files.createDirectories(path);
        }
        File folder = new File(oldPath);
        processFilesFromFolder(folder);
    }

    private static void processFilesFromFolder(File folder) throws IOException, UnsupportedAudioFileException, JavaLayerException {
        File[] folderEntries = folder.listFiles();
        if(folderEntries == null){
            log.warning("Folder " + folder.getAbsolutePath() + " is empty");
            throw new RuntimeException("Problem with reading folder \"mp3\"");
        }
        for (File entry : folderEntries)
        {
            if (entry.isDirectory())
            {
                log.info("it is a directory: " + entry.getAbsolutePath());

            } else {
                mp3ToWav(entry);
            }

        }
    }

    private static void mp3ToWav(File inputFile) throws JavaLayerException {
        // open stream
        String [] fileSplit = (inputFile.getName()).split("\\.");

        File wavFile = new File(newPath + "/" + fileSplit[0] + ".wav");
        log.info("Creating file " + wavFile.getName());
        Converter converter = new Converter();
        converter.convert(inputFile.getAbsolutePath(), wavFile.getAbsolutePath());
        log.fine("Write is ok");

    }
}
