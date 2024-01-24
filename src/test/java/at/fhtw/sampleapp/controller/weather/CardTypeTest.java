package at.fhtw.sampleapp.controller.weather;

import at.fhtw.sampleapp.model.CardType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTypeTest {

    @Test
    void testCardTypeValues() {
        // Assert
        assertEquals(4, CardType.values().length);
        assertEquals(CardType.FIRE, CardType.values()[0]);
        assertEquals(CardType.WATER, CardType.values()[1]);
        assertEquals(CardType.REGULAR, CardType.values()[2]);
        assertEquals(CardType.NEUTRAL, CardType.values()[3]);
    }
}