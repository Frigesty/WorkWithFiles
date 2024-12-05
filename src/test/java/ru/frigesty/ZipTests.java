package ru.frigesty;

import com.codeborne.pdftest.PDF;

import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZipTests {

    private ClassLoader cl = ZipTests.class.getClassLoader();

    @Test
    void checkingAPDFFileFromTheArchiveTest() throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/files.zip"));
        try (InputStream is = cl.getResourceAsStream("files.zip")) {
            assert is != null;
            try (ZipInputStream zipInputStream = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".pdf")) {
                        PDF pdf = new PDF(zipFile.getInputStream(entry));
                        assertEquals(2, pdf.numberOfPages);
                        assertEquals("Adobe PDF Library 15.0", pdf.producer);
                        assertThat(pdf).containsExactText("Все упражнения разбиты на четыре группы.");
                    }
                }
            }
        }
    }

    @Test
    void checkingAXLSXFileFromTheArchiveTest() throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/files.zip"));
        try (InputStream is = cl.getResourceAsStream("files.zip")) {
            assert is != null;
            try (ZipInputStream zipInputStream = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".xlsx")) {
                        XLS xls = new XLS(zipFile.getInputStream(entry));
                        Assertions.assertEquals("Hossack K100RS Prototype M/C",
                                xls.excel.getSheet("Eligible").getRow(13).getCell(2).toString());
                        Assertions.assertEquals("GT R SpeedLegend",
                                xls.excel.getSheet("Eligible").getRow(21).getCell(2).toString());
                    }
                }
            }
        }
    }

    @Test
    void checkingACSVFileFromTheArchiveTest() throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/files.zip"));
        try (InputStream is = cl.getResourceAsStream("files.zip")) {
            assert is != null;
            try (ZipInputStream zipInputStream = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".csv")) {
                        try (InputStream inputStreamCsv = zipFile.getInputStream(entry)) {
                            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStreamCsv, StandardCharsets.UTF_8));
                            List<String[]> csvContent = csvReader.readAll().subList(0, 6);
                            assertThat(csvContent).contains(
                                    new String[]{"Login email", "Identifier", "One-time password", "Recovery code", "First name", "Last name", "Department", "Location"},
                                    new String[]{"rachel@example.com", "9012", "12se74", "rb9012", "Rachel", "Booker", "Sales", "Manchester"},
                                    new String[]{"laura@example.com", "2070", "04ap67", "lg2070", "Laura", "Grey", "Depot", "London"},
                                    new String[]{"craig@example.com", "4081", "30no86", "cj4081", "Craig", "Johnson", "Depot", "London"},
                                    new String[]{"mary@example.com", "9346", "14ju73", "mj9346", "Mary", "Jenkins", "Engineering", "Manchester"},
                                    new String[]{"jamie@example.com", "5079", "09ja61", "js5079", "Jamie", "Smith", "Engineering", "Manchester"}
                            );
                        }

                    }
                }
            }
        }
    }
}