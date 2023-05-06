package managers;

import models.LabWork;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
/**
 * оперирует коллекцией
 */
public class CollectionManager {
    private int currentId = 1;
    private Map<Integer, LabWork> labWorks = new HashMap<>();

    private HashSet<LabWork> collection = new HashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    /**
     * @return коллекция
     */
    public HashSet <LabWork> getCollection(){
        return collection;
    }

    /**
     * @return последнее время инициализации
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return последнее время сохранения
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * сохраняет коллекцию в файл
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * добавляет лабораторную работу
     * @param labWork - экземпляр лабораторной работы
     * @return успешность выполнения команды
     */
    public boolean add(LabWork labWork){
        if (labWork == null) return false;
        labWorks.put(labWork.getId(), labWork);
        collection.add(labWork);
        return true;
    }

    public LabWork byId(int id) {
        return labWorks.get(id);
    }


    public int getFreeId() {
        while (byId(currentId) != null)
            if (++currentId < 0)
                currentId = 1;
        return currentId;
    }

    /**
     * загружает коллекцию из файла
     * @return true в случае успеха
     */
    public boolean loadCollection() {
        labWorks.clear();
        collection = (HashSet<LabWork>) dumpManager.readCollection();
        lastInitTime = LocalDateTime.now();
        for (var e : collection)
            if (byId(e.getId()) != null) {
                collection.clear();
                return false;
            } else {
                if (e.getId()>currentId) currentId = e.getId();
                labWorks.put(e.getId(), e);
            }
        return true;
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (LabWork labWork : collection) {
            info.append(labWork + "\n\n");
        }
        return info.toString().trim();
    }
}




