package ru.job4j.concurrent;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class CacheCAS {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        boolean result;
        if (memory.putIfAbsent(model.id(), model) != null) {
            result = true;
        } else {
            throw new OptimisticException("Model is exist");
        }
        return result;
    }

    public boolean update(Base model) throws OptimisticException {
        boolean result;
        if (memory.computeIfPresent(model.id(), (id, existingModel)
                -> model) != null) {
            result = true;
        } else {
            throw new OptimisticException("Model not exist");
        }
        return result;
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Optional.ofNullable(memory.get(id));
    }
}