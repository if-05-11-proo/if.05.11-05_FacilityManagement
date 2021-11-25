package at.htlleonding.facilitymgt;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class FacilityFactoryTest {
    @Test
    void testInterfaceAndInheritance() {
        assertEquals(true, Modifier.isInterface(FacilityFactory.class.getModifiers()));

        MuseumFactory museumFactory = new MuseumFactory();
        assertEquals(true, museumFactory instanceof FacilityFactory);

        SchoolFactory schoolFactory = new SchoolFactory();
        assertEquals(true, schoolFactory instanceof FacilityFactory);
    }

    @Test
    void testMuseumFactory() {
        MuseumFactory museumFactory = new MuseumFactory();

        Museum museum = (Museum)museumFactory.createFromString("Nordico Stadtmuseum Linz;Dametzstraße;23;4020;Linz;Archäologie, Geschichte, Zeitgeschichte, Volkskunde, Alltagskultur, Kunst, Kunstgewerbe, Natur");
        assertEquals("Nordico Stadtmuseum Linz", museum.getName());
        assertEquals("Linz", museum.getCity().getName());
        assertEquals(4020, museum.getCity().getZipCode());
        assertEquals("Dametzstraße", museum.getStreet());
        assertEquals(23, museum.getHouseNumber());
        assertEquals("Nordico Stadtmuseum Linz (Dametzstraße 23, 4020 Linz) - Sammlungsschwerpunkte: Archäologie, Geschichte, Zeitgeschichte, Volkskunde, Alltagskultur, Kunst, Kunstgewerbe, Natur", museum.toString());

        assertEquals(true, museum.hasCollectionFocus("Kunst"));
        assertEquals(true, museum.hasCollectionFocus("Volkskunde"));
        assertEquals(true, museum.hasCollectionFocus("Natur"));
        assertEquals(true, museum.hasCollectionFocus("Geschichte"));
        assertEquals(true, museum.hasCollectionFocus("Zeitgeschichte"));
        assertEquals(true, museum.hasCollectionFocus("Kunstgewerbe"));
        assertEquals(true, museum.hasCollectionFocus("Archäologie"));
        assertEquals(true, museum.hasCollectionFocus("Alltagskultur"));
        assertEquals(false, museum.hasCollectionFocus("Naturwissenschaft"));
        assertEquals(false, museum.hasCollectionFocus("Technik"));
        assertEquals(false, museum.hasCollectionFocus("Handwerk"));

        museum = (Museum)museumFactory.createFromString("Österreichisches Sattlermuseum;Ipfmühlstraße;15;4492;Hofkirchen im Traunkreis;Handwerk");
        assertEquals("Österreichisches Sattlermuseum", museum.getName());
        assertEquals("Hofkirchen im Traunkreis", museum.getCity().getName());
        assertEquals(4492, museum.getCity().getZipCode());
        assertEquals("Ipfmühlstraße", museum.getStreet());
        assertEquals(15, museum.getHouseNumber());
        assertEquals("Österreichisches Sattlermuseum (Ipfmühlstraße 15, 4492 Hofkirchen im Traunkreis) - Sammlungsschwerpunkte: Handwerk", museum.toString());

        assertEquals(false, museum.hasCollectionFocus("Kunst"));
        assertEquals(false, museum.hasCollectionFocus("Volkskunde"));
        assertEquals(false, museum.hasCollectionFocus("Natur"));
        assertEquals(false, museum.hasCollectionFocus("Geschichte"));
        assertEquals(false, museum.hasCollectionFocus("Zeitgeschichte"));
        assertEquals(false, museum.hasCollectionFocus("Kunstgewerbe"));
        assertEquals(false, museum.hasCollectionFocus("Archäologie"));
        assertEquals(false, museum.hasCollectionFocus("Alltagskultur"));
        assertEquals(false, museum.hasCollectionFocus("Naturwissenschaft"));
        assertEquals(false, museum.hasCollectionFocus("Technik"));
        assertEquals(true, museum.hasCollectionFocus("Handwerk"));
    }

    @Test
    void testSchoolFactory() {
        SchoolFactory schoolFactory = new SchoolFactory();

        School school = (School)schoolFactory.createFromString("410427;Technische Lehranstalt;Höhere Technische Bundeslehranstalt Leonding;Limesstraße;12;4060;Leonding;Dir. Hofrat DI Wolfgang Holzer");
        assertEquals("Höhere Technische Bundeslehranstalt Leonding", school.getName());
        assertEquals("Leonding", school.getCity().getName());
        assertEquals(4060, school.getCity().getZipCode());
        assertEquals("Limesstraße", school.getStreet());
        assertEquals(12, school.getHouseNumber());
        assertEquals(SchoolType.HTL, school.getSchoolType());
        assertEquals("Höhere Technische Bundeslehranstalt Leonding (Limesstraße 12, 4060 Leonding) - HTL", school.toString());

        school = (School)schoolFactory.createFromString("410162;Neue Mittelschule;Anton Bruckner Neue Mittelschule Ansfelden;Haiderstraße;8;4052;Ansfelden;Dir an NMS Dietmar Lackner");
        assertEquals("Anton Bruckner Neue Mittelschule Ansfelden", school.getName());
        assertEquals("Ansfelden", school.getCity().getName());
        assertEquals(4052, school.getCity().getZipCode());
        assertEquals("Haiderstraße", school.getStreet());
        assertEquals(8, school.getHouseNumber());
        assertEquals(SchoolType.MS, school.getSchoolType());
        assertEquals("Anton Bruckner Neue Mittelschule Ansfelden (Haiderstraße 8, 4052 Ansfelden) - MS", school.toString());
    }
}