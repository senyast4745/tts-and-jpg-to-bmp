package com.github.senyast4745.speaking;

import com.github.senyast4745.speaking.image.ImageClass;
import com.github.senyast4745.speaking.sound.ParseTxtClass;
import com.github.senyast4745.speaking.sound.WavConverter;
import com.github.senyast4745.speaking.util.NewLoadClass;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MainFrame extends JPanel
        implements ActionListener {

    private static Logger log = Logger.getLogger(MainFrame.class.getSimpleName());

    public static String selectedPath = new File("").getAbsolutePath();

    private static final String TITLE ="Выбранный путь: ";

    private static JLabel centerLabel;

    private MainFrame(Container container) {

        Dimension labelSize = new Dimension(80, 80);

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        Font font = new Font("Century Gothic", Font.BOLD, 14);

        centerLabel = new JLabel(TITLE + selectedPath);
        centerLabel.setVerticalAlignment(JLabel.CENTER);
        centerLabel.setHorizontalAlignment(JLabel.CENTER);
        centerLabel.setPreferredSize(labelSize);
        centerLabel.setFont(font);

        centerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(centerLabel);

        JButton chooseButton = new JButton("Выбрать директорию");
        chooseButton.addActionListener(this);
        chooseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(chooseButton);
        JButton startButton = new JButton("Сгенерировать файлы");
        startButton.addActionListener(e -> {
            log.fine("Button \"Start\" clicked");
            try {
                ParseTxtClass.readTxt();
            } catch (Exception ex){
                showWarning(ex.getMessage());
                log.log(Level.SEVERE, "Exception ", ex);
            }

            try {
                WavConverter.mainConverter();
            } catch (Exception ex) {
                showWarning(ex.getMessage());
                log.log(Level.SEVERE, "Exception ", ex);
            }

            try {
                ImageClass.mainImage();

            } catch (Exception ex) {
                showWarning(ex.getMessage());
                log.log(Level.SEVERE, "Exception ", ex);
           }

            JOptionPane.showMessageDialog(MainFrame.this,
                    new String[]{"Файлы сгенерированны и наодятся в директории", selectedPath},
                    "Готово",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(startButton);
        JButton helpButton = new JButton("Помощь");
        helpButton.addActionListener(e ->showInfoWindow());
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(helpButton);
    }

    private void showWarning(String text){
        JOptionPane.showMessageDialog(MainFrame.this,
                new String[]{text},
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        log.info("Start folder is " + new java.io.File(".").getAbsoluteFile());
        String chooserTitle = "Choose File";
        chooser.setDialogTitle(chooserTitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedPath = chooser.getSelectedFile().getAbsolutePath();
            centerLabel.setText(TITLE +  selectedPath);
            log.info("Selected path " + selectedPath);
        }
        else {
            log.info("No selection");
        }

    }

    private void showInfoWindow(){
        NewLoadClass loadClass = new NewLoadClass();
//        String [] message = loadClass.loadProperty("info").split("~");
        String [] message = loadClass.getMessage().split("~");
        JOptionPane.showMessageDialog(MainFrame.this,
                message,
                "Помощь",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] s) {

        createLogDirectory();
        try {
            LogManager.getLogManager().readConfiguration(
                    MainFrame.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        log.info("Start application");

        JFrame frame = new JFrame("");
        MainFrame panel = new MainFrame(frame.getContentPane());
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.getContentPane().add(panel);
        frame.setSize(new Dimension(600, 485));
        frame.setVisible(true);
    }

    private static void createLogDirectory(){
        File logDirFile = new java.io.File(".");
        String logDir = logDirFile.getAbsolutePath();
        logDir += "/log/";
        Path path = Paths.get(logDir);
        try {
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.err.println("Could not setup log folder: " + e.toString());
        }

    }
}


