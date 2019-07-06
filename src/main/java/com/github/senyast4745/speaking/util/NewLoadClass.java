package com.github.senyast4745.speaking.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Set;

public class NewLoadClass {

    public String getMessage(){
        return "Доброе время суток!~Инструкция:~Для начала работы программы создайте на вшем компьютере пустой каталог~" +
                "Далее для генерации \".wav\" файла в этом каталоге создайте файл \"input.txt\"~" +
                "Внесите в этот файл значения по типу <имя записи>:<необходимый текст для озвучивания>~" +
                "Каждая пара в отельной строке~Например: 0:Привет мир!~" +
                "Далее для перевода картинок из формата \"jpg\" в формат \"bmp\"~" +
                "вы можете создать в этом же каталоге новый каталог с названием \"jpg\" и загрузить туда свои изображения~" +
                "для конвертации и уменьшения размера до размера 200*200 пикселей~(при условии что изначальое изображение квадратное)~" +
                "Далее нажмите кнопку \"Выбрать директорю\" и найдите директорию в которой вы создали файл \"input.txt\" и/или папку \"jpg\"~" +
                "После этого в окне выбора директории нажмите кнопку \"Открыть\" и проверьте выбранную директорию~" +
                "Далее при корректной директории нажмите кнопку \"Сгенерировать файлы\"~" +
                "При наличи ошибок прочитайте сообщение и примите решении необходимо ли вам что то менять в конфигурации папки~" +
                "(Для генерации звуковых файлов обязательно подключение к интернету)~" +
                "После успешной генерации зайдите в созданную вами папку, в которой вы создавали файл \"input.txt\" и/или папку \"jpg\"~" +
                "Сгенрированные файлы \".wav\" находятся в папке \"wav\", а сгенерированные файлы \".bmp\" в папке \"bmp\"~" +
                "Спасибо за использование моего приложения!";
    }

    public String loadProperty(String key){
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                throw new RuntimeException("Sorry, unable to find " + "config.properties");

            }

            prop.load(input);

            // Java 8 , print key and values


            return prop.getProperty(key);

            /*Enumeration e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                System.out.println("Key : " + key + ", Value : " + value);
            }*/

        } catch (IOException ex) {
            throw new RuntimeException("Can not load properties");
        }
    }

}
