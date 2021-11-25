package at.htlleonding.facilitymgt;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FacilityTest {
    @Test
    void testAbstractAndInheritance() {
        assertEquals(true, Modifier.isAbstract(Facility.class.getModifiers()));

        City linz = new City(4020, "Linz");
        City leonding = new City(4060, "Leonding");
        Museum lentos = new Museum("Lentos Kunstmuseum Linz", linz, "Ernst-Koref-Promenade", 1);
        School htlLeonding = new School("Höhere Technische Bundeslehranstalt Leonding", leonding, "Limesstraße", 14, "Technische Lehranstalt");

        assertEquals(true, lentos instanceof Facility);
        assertEquals(true, htlLeonding instanceof Facility);
    }

    @Test
    void testSchoolConstructor() {
        City leonding = new City(4060, "Leonding");
        School htlLeonding = new School("Höhere Technische Bundeslehranstalt Leonding", leonding, "Limesstraße", 14, "Technische Lehranstalt");

        assertEquals("Höhere Technische Bundeslehranstalt Leonding", htlLeonding.getName());
        assertEquals(leonding, htlLeonding.getCity());
        assertEquals("Limesstraße", htlLeonding.getStreet());
        assertEquals(14, htlLeonding.getHouseNumber());
        assertEquals(SchoolType.HTL, htlLeonding.getSchoolType());
    }

    @Test
    void testSchoolTypes() {
        City leonding = new City(4060, "Leonding");

        School school = new School("Schule für angewandtes Unit-Testing", leonding, "Teststraße", 7, "AHS-Langform");
        assertEquals(SchoolType.AHS, school.getSchoolType());

        school = new School("Schule für angewandtes Unit-Testing", leonding, "Teststraße", 7, "Kaufmännische Schule");
        assertEquals(SchoolType.HAK, school.getSchoolType());

        school = new School("Höhere Technische Bundeslehranstalt Leonding", leonding, "Limesstraße", 14, "Technische Lehranstalt");
        assertEquals(SchoolType.HTL, school.getSchoolType());

        school = new School("Kolleg für EDV/Organisation", leonding, "Limesstraße", 14, "Kolleg");
        assertEquals(SchoolType.KOLLEG, school.getSchoolType());

        school = new School("Schule für angewandtes Unit-Testing", leonding, "Teststraße", 7, "Mittelschule");
        assertEquals(SchoolType.MS, school.getSchoolType());

        school = new School("Schule für angewandtes Unit-Testing", leonding, "Teststraße", 7, "Neue Mittelschule");
        assertEquals(SchoolType.MS, school.getSchoolType());

        school = new School("Schule für angewandtes Unit-Testing", leonding, "Teststraße", 7, "Volksschule");
        assertEquals(SchoolType.VS, school.getSchoolType());

        school = new School("Schule für angewandtes Unit-Testing", leonding, "Teststraße", 7, "Lebensschule");
        assertEquals(SchoolType.SONSTIGE, school.getSchoolType());

        school = new School("Höhere Technische Bundeslehranstalt Leonding", leonding, "Limesstraße", 14, "Schule für Berufstätige");
        assertEquals(SchoolType.SONSTIGE, school.getSchoolType());
    }

    @Test
    void testSchoolToString() {
        City leonding = new City(4060, "Leonding");
        School htlLeonding = new School("Höhere Technische Bundeslehranstalt Leonding", leonding, "Limesstraße", 14, "Technische Lehranstalt");

        assertEquals("Höhere Technische Bundeslehranstalt Leonding (Limesstraße 14, 4060 Leonding) - HTL", htlLeonding.toString());
    }

    @Test
    void testMuseumContructor() {
        City linz = new City(4040, "Linz");
        Museum biologiezentrum = new Museum("Biologiezentrum Linz", linz, "J.-W.-Klein-Straße", 73);

        assertEquals("Biologiezentrum Linz", biologiezentrum.getName());
        assertEquals(linz, biologiezentrum.getCity());
        assertEquals("J.-W.-Klein-Straße", biologiezentrum.getStreet());
        assertEquals(73, biologiezentrum.getHouseNumber());
    }

    @Test
    void testMuseumCollectionFoci() {
        City linz = new City(4040, "Linz");
        Museum biologiezentrum = new Museum("Biologiezentrum Linz", linz, "J.-W.-Klein-Straße", 73);

        assertEquals(true, biologiezentrum.addCollectionFocus("Naturwissenschaft"));
        assertEquals(false, biologiezentrum.hasCollectionFocus("Natur"));
        assertEquals(true, biologiezentrum.hasCollectionFocus("Naturwissenschaft"));
        assertEquals(false, biologiezentrum.hasCollectionFocus("Technik"));
        assertEquals("Biologiezentrum Linz (J.-W.-Klein-Straße 73, 4040 Linz) - Sammlungsschwerpunkte: Naturwissenschaft", biologiezentrum.toString());

        assertEquals(false, biologiezentrum.addCollectionFocus("Naturwissenschaft"));
        assertEquals(false, biologiezentrum.hasCollectionFocus("Natur"));
        assertEquals(true, biologiezentrum.hasCollectionFocus("Naturwissenschaft"));
        assertEquals(false, biologiezentrum.hasCollectionFocus("Technik"));
        assertEquals("Biologiezentrum Linz (J.-W.-Klein-Straße 73, 4040 Linz) - Sammlungsschwerpunkte: Naturwissenschaft", biologiezentrum.toString());

        assertEquals(true, biologiezentrum.addCollectionFocus("Natur"));
        assertEquals(true, biologiezentrum.hasCollectionFocus("Natur"));
        assertEquals(true, biologiezentrum.hasCollectionFocus("Naturwissenschaft"));
        assertEquals(false, biologiezentrum.hasCollectionFocus("Technik"));
        assertEquals("Biologiezentrum Linz (J.-W.-Klein-Straße 73, 4040 Linz) - Sammlungsschwerpunkte: Naturwissenschaft, Natur", biologiezentrum.toString());
    }

    @Test
    void testEquality() {
        City linz = new City(4020, "Linz");
        City leonding = new City(4060, "Leonding");
        Museum lentos = new Museum("Lentos Kunstmuseum Linz", linz, "Ernst-Koref-Promenade", 1);
        Museum lentosCafe = new Museum("Lentos Kaffee", linz, "Ernst-Koref-Promenade", 1);
        Museum museumLeonding = new Museum("Computermuseum", leonding, "Limesstraße", 14);
        School htlLeonding = new School("Höhere Technische Bundeslehranstalt Leonding", leonding, "Limesstraße", 14, "Technische Lehranstalt");
        School kollegLeonding = new School("Kolleg für EDV/Organisation", leonding, "Limesstraße", 14, "Kolleg");
        School adultSchoolLeonding = new School("Höhere Technische Bundeslehranstalt Leonding", leonding, "Limesstraße", 14, "Schule für Berufstätige");
        School testSchoolLeonding = new School("Unit-Testing-Schule Leonding", leonding, "Limesstraße", 14, "Technische Lehranstalt");

        assertEquals(lentos, lentosCafe, "Same address - equality!");

        assertNotEquals(lentos, museumLeonding, "Different address - no equality.");
        assertNotEquals(htlLeonding, lentos, "Different address and class- no equality.");

        assertNotEquals(htlLeonding, museumLeonding, "Same address but different class - no equality!");

        assertEquals(htlLeonding, testSchoolLeonding, "Same address and same school type - equality!");

        assertNotEquals(htlLeonding, kollegLeonding, "Same address but different school type - no equality!");
        assertNotEquals(htlLeonding, adultSchoolLeonding, "Same address but different school type - no equality!");
        assertNotEquals(adultSchoolLeonding, kollegLeonding, "Same address but different school type - no equality!");
    }
}
