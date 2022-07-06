package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        if (fromUser != null && toUser != null && fromUser.getAmount() >= amount) {
            int fromUserAmount = fromUser.getAmount();
            int toUserAmount = toUser.getAmount();
            fromUser.setAmount(fromUserAmount - amount);
            toUser.setAmount(toUserAmount + amount);
            result = true;
        }
        return result;
    }
}
