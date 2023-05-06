package managers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import models.LabWork;
import utility.Console;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * использует файл для сохранения и загрузки коллекции
 */
public class DumpManager {
    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    /**
     * преобразует коллекцию в csv - строку
     * @param collection - коллекция объектов
     * @return csv - строка
     */
    private String collection2CSV(Collection<LabWork> collection) {
        try {
            StringWriter sw = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(sw, ';');
            for (var e : collection) {
                csvWriter.writeNext(LabWork.toArray(e));
            }
            String csv = sw.toString();
            return csv;
        } catch (Exception e) {
            console.printError("Ошибка сериализации");
            return null;
        }
    }
    public void writeCollection(Collection<LabWork> collection) {
        try {
            var csv = collection2CSV(collection);
            if (csv == null) return;
            var bos = new BufferedOutputStream(new FileOutputStream(fileName));
            try  {
                bos.write(csv.getBytes());
                bos.flush();
                bos.close();
                console.println("Коллекция успешно сохранена в файл!");
            } catch (IOException e) {
                console.printError("Неожиданная ошибка сохранения");
            }
        } catch (FileNotFoundException | NullPointerException e) {
            console.printError("Файл не найден");
        }
    }

    /**
     * преобразует CSV-строку в коллекцию
     * @param s - CSV-строка
     * @return коллекция
     */
    private HashSet <LabWork> CSV2collection(String s) {
        try {
            StringReader sr = new StringReader(s);
            CSVReader csvReader = new CSVReader(sr, ';');
            HashSet <LabWork> lws = new HashSet<>();
            String[] record = null;
            while ((record = csvReader.readNext()) != null) {
                LabWork lw = LabWork.fromArray(record);
                if (lw == null) { console.printError("Запись "+Arrays.toString(record)+" не действительна"); continue; }
                if (lw.validate())
                    lws.add(lw);
                else
                    console.printError("Файл с колекцией содержит недействительные данные");
            }
            csvReader.close();
            return lws;
        } catch (Exception e) {
            console.printError("Ошибка десериализации " + e);
            return null;
        }
    }

    /**
     * считывает коллекцию из файла
     * @return считанная коллекция
     */
    public Collection<LabWork> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var bis = new BufferedInputStream(new FileInputStream(fileName))) {
                var s  = new StringBuilder("");
                int i;
                while ((i = bis.read())!= -1) {
                    s.append( (char) i);
                    //s.append("\n");
                }
                HashSet <LabWork> collection = CSV2collection(s.toString());
                if (collection != null) {
                    console.println("Коллекция успешно загружена!");
                    return collection;
                } else
                    console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IOException e) {
                console.printError("Ошибка при чтении данных из файла");
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new HashSet<>();
    }
}
