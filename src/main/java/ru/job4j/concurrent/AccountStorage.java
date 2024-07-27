package ru.job4j.concurrent;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Account firstAcc = accounts.get(fromId);
        Account secondAcc = accounts.get(toId);
        if (firstAcc != null && secondAcc != null && firstAcc.amount() >= amount) {
            /* firstAcc.amount() - amount; */
            rsl = true;
        }
        return rsl;
    }
}