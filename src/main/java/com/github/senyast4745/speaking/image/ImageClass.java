package com.github.senyast4745.speaking.image;

import com.github.senyast4745.speaking.sound.ParseTxtClass;
import com.github.senyast4745.speaking.sound.WavConverter;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ImageClass {

    private static Logger log = Logger.getLogger(ImageClass.class.getSimpleName());

    private static final String path = ParseTxtClass.getCurrentDirectory();
    private static final String oldPath = path + "/jpg";
    private static final String newPath = path + "/bmp";


    private static BufferedImage loadImage(File inputFile) throws IOException {
        return ImageIO.read(inputFile);
    }

    private static BufferedImage scaleImage(BufferedImage startImage){
        return Scalr.resize(startImage, 160);
    }

    public static void mainImage() throws IOException {
        try {
            Path path = Paths.get(newPath);
            if(!Files.exists(path)){
                log.fine("Creating folder " + newPath);
                Files.createDirectories(path);
            }
            File folder = new File(oldPath);
            processFilesFromFolder(folder);
        } catch (Exception e){
            throw new RuntimeException("Can not find directory with name \"jpg\"");
        }

    }

    private static void scaleAndSave(File inputFile) throws IOException {

        BufferedImage image = scaleImage(loadImage(inputFile));
        String [] fileSplit = (inputFile.getName()).split("\\.");
        File tmp = new File(newPath + "/" + fileSplit[0] + ".bmp");
        log.fine("Creating file " + tmp);
        ImageIO.write(image, "bmp", tmp);
        log.fine("Write is ok");
    }

    private static void processFilesFromFolder(File folder) throws IOException {
        File[] folderEntries = folder.listFiles();
        if(folderEntries == null){
            throw new RuntimeException("Problem with reading folder \"jpg\"");
        }
        for (File entry : folderEntries)
        {
            if (entry.isDirectory())
            {
                log.info("it is a directory: " + entry.getAbsolutePath());

            } else {
                scaleAndSave(entry);
            }

        }
    }
}
