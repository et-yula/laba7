package managers;

import models.LabWork;
import utility.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * класс, оперирующий коллекцией
 */
public class CollectionManager {
    private int currentId = 1;
    private ConcurrentHashMap<LabWork,Long> userIdMap = new ConcurrentHashMap<>();

    private ConcurrentSkipListSet<LabWork> collection = new ConcurrentSkipListSet<>(Comparator.comparingInt(LabWork::getId));
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;
    private final UserManager userManager;

    public CollectionManager(DumpManager dumpManager, UserManager userManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
        this.userManager=userManager;
    }

    /**
     * @return коллекция
     */
    public HashSet <LabWork> getCollection(){
        return new HashSet<>(Arrays.asList(collection.toArray(new LabWork[]{})));
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
     * удаляет лабораторную работу по id
     * @param id - id лабораторной работы
     * @param user - удаляющий пользователь
     * @return успешность выполнения
     */
    public boolean remove(int id, User user) {
        if (user == null) return false;
        var labWork = byId(id);
        if (labWork == null) return false;
        if (!(userIdMap.get(labWork)==user.getID())) return false;
        if (!dumpManager.removeLabWork(id)) return false;
        collection.remove(labWork);
        return true;
    }

    /**
     * обновляет лабораторную работу по id
     * @param labWork - лабораторная работа
     * @param user - пользователь
     * @return успешность выполнения
     */
    public boolean update(LabWork labWork, User user) {
        if (labWork == null || user == null) return false;
        var oldLabWork = byId(labWork.getId());
        if (oldLabWork == null) return false;
        if (!(userIdMap.get(oldLabWork)==user.getID())) return false;
        if (!dumpManager.updateLabWork(labWork)) return false;
        collection.remove(oldLabWork);
        collection.add(labWork);
        userIdMap.put(labWork, user.getID());
        return true;
    }

    /**
     * загружает коллекцию из базы данных
     */
    public void load() {
        collection.clear();
        userIdMap.clear();
        for (var labWorkAndUser: dumpManager.selectLabWork()) {
            collection.add(labWorkAndUser.labwork);
            userIdMap.put(labWorkAndUser.labwork, labWorkAndUser.user);
        }
    }

    /**
     * добавляет лабораторную работу
     * @param labWork - экземпляр лабораторной работы
     * @return успешность выполнения команды
     */
    public boolean add(LabWork labWork, User user){
        if (labWork == null || user == null) {
            return false;
        }
        if (!dumpManager.insertLabWork(labWork, user.getID())) {
            return false;
        }
        collection.add(labWork);
        userIdMap.put(labWork, user.getID());
        return true;
    }

    /**
     * получаем лабораторную работу по id
     * @param id - id лабораторной работы
     * @return лабораторная работа
     */
    public LabWork byId(int id) {
        for (var labWork: collection.toArray()) if (((LabWork)labWork).getId()==id) return (LabWork) labWork; return null;
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