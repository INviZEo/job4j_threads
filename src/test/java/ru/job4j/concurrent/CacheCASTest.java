package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class CacheCASTest {

    @Test
    void addModelTest() throws OptimisticException {
        CacheCAS cacheCAS = new CacheCAS();
        Base base = new Base(1, "Das", 3);
        cacheCAS.add(base);
        assertThat(cacheCAS.findById(1)).isPresent();
        assertThat(cacheCAS.findById(1).get().id()).isEqualTo(1);
        assertThat(cacheCAS.findById(1).get().name()).isEqualTo("Das");
        assertThat(cacheCAS.findById(1).get().version()).isEqualTo(3);
    }

    @Test
    void updateModelTest() throws OptimisticException {
        CacheCAS cacheCAS = new CacheCAS();
        Base base = new Base(1, "Das", 3);
        Base base1 = new Base(1, "Kvas", 3);
        cacheCAS.add(base);
        assertEquals("Das", cacheCAS.findById(1).get().name());
        assertTrue(cacheCAS.update(base1));
        assertEquals("Kvas", cacheCAS.findById(1).get().name());
    }
}