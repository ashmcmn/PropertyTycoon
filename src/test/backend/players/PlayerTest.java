package test.backend.players;

import main.backend.players.Player;
import main.backend.players.Token;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player(Token.BOOT, 1500);
    }

    @Test
    void getToken() {
        assertEquals(Token.BOOT, player.getToken());
    }

    @Test
    void setToken() {
        player.setToken(Token.CAT);
        assertEquals(Token.CAT, player.getToken());
    }

    @Test
    void getCash() {
        assertEquals(1500, player.getCash());
    }

    @Test
    void setCash() {
        player.setCash(2000);
        assertEquals(2000, player.getCash());
    }
}