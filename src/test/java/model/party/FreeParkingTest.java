package model.party;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FreeParkingTest {
    FreeParking freeParking;

    @BeforeEach
    void setUp() {
        freeParking = new FreeParking(40);
    }

    @Test
    void getCash() {
        assertEquals(40, freeParking.getCash());
    }

    @Test
    void setCash() {
        freeParking.setCash(100);
        assertEquals(100, freeParking.getCash());
    }
}