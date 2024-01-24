package at.fhtw.sampleapp.controller.weather;

import at.fhtw.sampleapp.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
//Die setUp-Methode wird vor jedem Test ausgeführt und initialisiert die Card-Instanz.
//Die testId-Methode testet die setId- und getId-Methoden.
//Die testName-Methode testet die setName- und getName-Methoden.
//Die testDamage-Methode testet die setDamage- und getDamage-Methoden.
//Die testType-Methode testet die setType- und getType-Methoden.
    private Card card;

    @BeforeEach
    public void setUp() {
        card = new Card();
    }

    @Test
    public void testId() {
        String id = "testId";
        card.setId(id);
        assertEquals(id, card.getId());
    }

    @Test
    public void testName() {
        String name = "testName";
        card.setName(name);
        assertEquals(name, card.getName());
    }

    @Test
    public void testDamage() {
        Integer damage = 10;
        card.setDamage(damage);
        assertEquals(damage, card.getDamage());
    }

    @Test
    public void testType() {
        int type = 1;
        card.setType(type);
        assertEquals(type, card.getType());
    }
}