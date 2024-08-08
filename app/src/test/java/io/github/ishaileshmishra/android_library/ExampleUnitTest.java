package io.github.ishaileshmishra.android_library;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void initialisation_is_correct() {
        Client client = new Client("API_KEY");
        assertNull(client.API_KEY);
    }

    @Test
    public void initialisation_incorrect() {
        Client client = new Client("API_KEY");
        assertNull(client.API_KEY);
        assertNull(client.AUTH_TOKEN);
        assertEquals(4, 2 + 2);
    }
}


