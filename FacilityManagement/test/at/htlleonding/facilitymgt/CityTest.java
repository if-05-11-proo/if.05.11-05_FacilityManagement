package at.htlleonding.facilitymgt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {
    @Test
    void testConstructor() {
        City linz = new City(4020, "Linz");
        assertEquals(4020, linz.getZipCode());
        assertEquals("Linz", linz.getName());
    }

    @Test
    void testEquality() {
        City linz = new City(4030, "Linz");
        City pichling = new City(4030, "Linz/Pichling");
        City grieskirchen = new City(4710, "Grieskirchen");

        assertEquals(true, linz.equals(pichling));
        assertEquals(true, pichling.equals(linz));
        assertEquals(false, linz.equals(grieskirchen));
        assertEquals(false, grieskirchen.equals(pichling));
    }

    @Test
    void testCompareTo() {
        City linz = new City(4020, "Linz");
        City pichling = new City(4030, "Linz/Pichling");
        City grieskirchen = new City(4710, "Grieskirchen");

        assertEquals(0, linz.compareTo(linz));
        assertEquals(0, pichling.compareTo(pichling));
        assertEquals(0, grieskirchen.compareTo(grieskirchen));
        assertEquals(-1, linz.compareTo(pichling));
        assertEquals(1, pichling.compareTo(linz));
        assertEquals(1, grieskirchen.compareTo(pichling));
        assertEquals(-1, pichling.compareTo(grieskirchen));
        assertEquals(1, grieskirchen.compareTo(linz));
        assertEquals(-1, linz.compareTo(grieskirchen));
    }

    @Test
    void testToString() {
        City linz = new City(4020, "Linz");
        City grieskirchen = new City(4710, "Grieskirchen");

        assertEquals("4020 Linz", linz.toString());
        assertEquals("4710 Grieskirchen", grieskirchen.toString());
    }
}