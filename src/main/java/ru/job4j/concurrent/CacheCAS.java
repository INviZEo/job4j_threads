package ru.job4j.concurrent;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CacheCAS {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        return memory.computeIfPresent(model.id(), (key, value) -> {
            if (value.version() != model.version()) {
                throw new OptimisticException("Model is exist");
            }
            return new Base(model.id(), model.name(), model.version() + 1);
        }) != null;
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Optional.ofNullable(memory.get(id));
    }
}