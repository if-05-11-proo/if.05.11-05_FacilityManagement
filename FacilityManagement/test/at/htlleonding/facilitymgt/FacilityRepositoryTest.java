package at.htlleonding.facilitymgt;

import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacilityRepositoryTest {
    @Test
    void testSingletonImplementation() {
        FacilityRepository facilityRepository = FacilityRepository.getInstance();
        FacilityRepository otherFacilityRepository = FacilityRepository.getInstance();

        assertEquals(true, facilityRepository == otherFacilityRepository);
    }

    @Test
    void testGetNotExistingCityByZipCode() {
        FacilityManagementException e = assertThrows(FacilityManagementException.class, () -> {
            FacilityRepository.getInstance().getFacilitiesByZipCode(1234);
        });

        assertEquals(FacilityManagementException.ZIP_CODE_NOT_FOUND_MESSAGE, e.getMessage());
    }

    @Test
    void testCreateCityIfNotExists() {
        FacilityManagementException e = assertThrows(FacilityManagementException.class, () -> {
            FacilityRepository.getInstance().getFacilitiesByZipCode(8010);
        });

        assertEquals(FacilityManagementException.ZIP_CODE_NOT_FOUND_MESSAGE, e.getMessage());

        City graz = FacilityRepository.getInstance().createCityIfNotExists(8010, "Graz");
        assertEquals(8010, graz.getZipCode());
        assertEquals("Graz", graz.getName());

        City otherGraz = FacilityRepository.getInstance().createCityIfNotExists(8010, "Graz");
        assertEquals(true,graz == otherGraz);

        City yetAnotherGraz = FacilityRepository.getInstance().getCityByZipCode(8010);
        assertEquals(true, graz == yetAnotherGraz);

        City gras = FacilityRepository.getInstance().createCityIfNotExists(8010, "Gras");
        assertEquals(true, graz == gras);
    }

    @Test
    void testAddDuplicateFacility() {
        MuseumFactory museumFactory = new MuseumFactory();
        Facility museum = museumFactory.createFromString("Museum Arbeitswelt;Wehrgrabengasse;7;4400;Steyr;Handwerk,Industrialisierung");

        assertEquals(true, FacilityRepository.getInstance().addFacility(museum));

        assertEquals(false, FacilityRepository.getInstance().addFacility(museum));

        Facility museumCafe = museumFactory.createFromString("Museum Arbeitswelt Kaffee;Wehrgrabengasse;7;4400;Steyr;Kaffeekultur");

        assertEquals(museum, museumCafe);

        assertEquals(false, FacilityRepository.getInstance().addFacility(museumCafe), "Can't add equal duplicates!");
    }

    @Test
    void testFactoryAndRepositoryCitiesAreIdentical() {
        SchoolFactory schoolFactory = new SchoolFactory();

        Facility htlLeonding = schoolFactory.createFromString("410427;Technische Lehranstalt;Höhere Technische Bundeslehranstalt Leonding;Limesstraße;12;4060;Leonding;Dir. Hofrat DI Wolfgang Holzer");
        Facility sonderschuleHart = schoolFactory.createFromString("410063;Sonderschule;Allgem. Sonderschule Hart;Limesstraße;6;4060;Leonding;SD OSR Manuela Mager, BEd");

        assertEquals(true, htlLeonding.getCity() == sonderschuleHart.getCity());

        City leonding = FacilityRepository.getInstance().getCityByZipCode(4060);

        assertEquals(true, htlLeonding.getCity() == leonding);
        assertEquals(true, leonding == sonderschuleHart.getCity());
    }

    @Test
    void testReadFromNotExistingFile() {
        FacilityManagementException e = assertThrows(FacilityManagementException.class, () -> {
            FacilityRepository.getInstance().readFromFile("data/doesntexist.csv", new MuseumFactory());
        });

        assertEquals(FacilityManagementException.IO_EXCEPTION_MESSAGE, e.getMessage());
        assertEquals(NoSuchFileException.class, e.getCause().getClass());
    }

    @Test
    void testReadFromCorruptFile() {
        int errorCount = FacilityRepository.getInstance().readFromFile("data/museums_corrupt.csv", new MuseumFactory());
        assertEquals(3, errorCount);
    }

    @Test
    void testReadFromFilesAndGetSortedFacilitiesByZipCode() {
        int errorCount = FacilityRepository.getInstance().readFromFile("data/museums_L_LL.csv", new MuseumFactory());
        assertEquals(0, errorCount);
        errorCount = FacilityRepository.getInstance().readFromFile("data/schools_LL.csv", new SchoolFactory());
        assertEquals(0, errorCount);

        List<Facility> facilitiesLinz = FacilityRepository.getInstance().getFacilitiesByZipCode(4020);
        assertEquals(8, facilitiesLinz.size());

        assertEquals("OÖ. Literaturmuseum im StifterHaus (Adalbert-Stifter-Platz 1, 4020 Linz) - Sammlungsschwerpunkte: Literatur, Personen", facilitiesLinz.get(0).toString());
        assertEquals("Nordico Stadtmuseum Linz (Dametzstraße 23, 4020 Linz) - Sammlungsschwerpunkte: Archäologie, Geschichte, Zeitgeschichte, Volkskunde, Alltagskultur, Kunst, Kunstgewerbe, Natur", facilitiesLinz.get(1).toString());
        assertEquals("Lentos Kunstmuseum Linz (Ernst-Koref-Promenade 1, 4020 Linz) - Sammlungsschwerpunkte: Kunst", facilitiesLinz.get(2).toString());
        assertEquals("Zahnmuseum Linz (Pfarrgasse 9, 4020 Linz) - Sammlungsschwerpunkte: Gesundheit", facilitiesLinz.get(3).toString());
        assertEquals("Schlossmuseum Linz (Schlossberg 1, 4020 Linz) - Sammlungsschwerpunkte: Geschichte, Verkehr, Transport, Musik, Militaria, Religion, Zeitgeschichte, Handwerk, Handel, Industrie, Industriegeschichte, Volkskunde, Alltagskultur, Kunst, Kunstgewerbe, Natur, Naturwissenschaft, Technik, Technikgeschichte, Personen", facilitiesLinz.get(4).toString());
        assertEquals("Geschichteclub Stahl (Stahlstraße 33, 4020 Linz) - Sammlungsschwerpunkte: Geschichte, Industrie, Industriegeschichte, Technik, Technikgeschichte", facilitiesLinz.get(5).toString());
        assertEquals("Zeitgeschichte MUSEUM voestalpine (voestalpine-Straße 1, 4020 Linz) - Sammlungsschwerpunkte: Geschichte, Zeitgeschichte, Industrie, Industriegeschichte", facilitiesLinz.get(6).toString());
        assertEquals("voestalpine Stahlwelt (voestalpine-Straße 4, 4020 Linz) - Sammlungsschwerpunkte: Industrie, Industriegeschichte, Technik, Technikgeschichte", facilitiesLinz.get(7).toString());

        List<Facility> facilitiesWilhering = FacilityRepository.getInstance().getFacilitiesByZipCode(4073);
        assertEquals(5, facilitiesWilhering.size());

        assertEquals("Volksschule Dörnbach (Dörnbacherstraße 115, 4073 Wilhering) - VS", facilitiesWilhering.get(0).toString());
        assertEquals("Volksschule Wilhering (Höfer Straße 4, 4073 Wilhering) - VS", facilitiesWilhering.get(1).toString());
        assertEquals("Stiftsmuseum Wilhering (Linzer Straße 4, 4073 Wilhering) - Sammlungsschwerpunkte: Geschichte, Musik, Religion, Kunst", facilitiesWilhering.get(2).toString());
        assertEquals("Stiftsgymnasium Wilhering (Linzer Straße 8, 4073 Wilhering) - AHS", facilitiesWilhering.get(3).toString());
        assertEquals("Volksschule Schönering (Schulstraße 4, 4073 Wilhering) - VS", facilitiesWilhering.get(4).toString());
    }
}