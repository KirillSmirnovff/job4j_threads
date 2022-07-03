package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        users.putIfAbsent(user.getId(), user);
        return !isPresent(user);
    }

    public synchronized boolean update(User user) {
        boolean result = isPresent(user);
        if (result) {
            users.put(user.getId(), user);
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        boolean result = isPresent(user);
        if (result) {
            users.remove(user.getId());
        }
        return result;
    }

    private boolean isPresent(User user) {
        return users.containsKey(user.getId());
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        boolean firstCheck = users.containsKey(fromId) && users.containsKey(toId);
        if (firstCheck) {
            User fromUser = users.get(fromId);
            User toUser = users.get(toId);
            int fromUserAmount = fromUser.getAmount();
            int toUserAmount = toUser.getAmount();
            result = fromUserAmount >= amount;
            if (result) {
                fromUser.setAmount(fromUserAmount - amount);
                toUser.setAmount(toUserAmount + amount);
            }
        }
        return result;
    }
}
