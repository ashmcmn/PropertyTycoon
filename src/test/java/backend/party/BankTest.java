package backend.party;

import backend.party.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class BankTest {
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank(50000);
    }

    @Test
    void getCash() {
        assertEquals(50000, bank.getCash());
    }

    @Test
    void setCash() {
        bank.setCash(60000);
        assertEquals(60000, bank.getCash());
    }
}