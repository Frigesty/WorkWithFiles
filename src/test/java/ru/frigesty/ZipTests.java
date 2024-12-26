package ru.frigesty;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import static com.codeborne.pdftest.assertj.Assertions.assertThat;

@DisplayName("ZIP тесты")
public class ZipTests {

    private final ClassLoader cl = ZipTests.class.getClassLoader();

    @DisplayName("Проверяем PDF файл из архива")
    @Test
    void checkingAPDFFileFromTheArchiveTest() throws Exception {
        try (ZipFile zipFile = new ZipFile(new File("src/test/resources/files.zip"));
             InputStream is = cl.getResourceAsStream("files.zip");
             ZipInputStream zipInputStream = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().endsWith(".pdf")) {
                    PDF pdf = new PDF(zipFile.getInputStream(entry));
                    assertThat(pdf.numberOfPages).isEqualTo(2);
                    assertThat(pdf.producer).isEqualTo("Adobe PDF Library 15.0");
                    assertThat(pdf).containsExactText("Все упражнения разбиты на четыре группы.");
                }
            }
        }
    }

    @DisplayName("Проверяем XLS файл из архива")
    @Test
    void checkingAXLSXFileFromTheArchiveTest() throws Exception {
        try (ZipFile zipFile = new ZipFile(new File("src/test/resources/files.zip"));
             InputStream is = cl.getResourceAsStream("files.zip");
             ZipInputStream zipInputStream = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().endsWith(".xlsx")) {
                    XLS xls = new XLS(zipFile.getInputStream(entry));
                    assertThat(xls.excel.getSheet("Eligible").getRow(13).getCell(2).toString())
                            .isEqualTo("Hossack K100RS Prototype M/C");
                    assertThat(xls.excel.getSheet("Eligible").getRow(21).getCell(2).toString())
                            .isEqualTo("GT R SpeedLegend");
                }
            }
        }
    }

    @DisplayName("Проверяем CSV файл из архива")
    @Test
    void checkingACSVFileFromTheArchiveTest() throws Exception {
        try (ZipFile zipFile = new ZipFile(new File("src/test/resources/files.zip"));
             InputStream is = cl.getResourceAsStream("files.zip");
             ZipInputStream zipInputStream = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().endsWith(".csv")) {
                    try (InputStream inputStreamCsv = zipFile.getInputStream(entry);
                         InputStreamReader isr = new InputStreamReader(inputStreamCsv, StandardCharsets.UTF_8);
                         CSVReader csvReader = new CSVReader(isr)) {
                        List<String[]> csvContent = csvReader.readAll().subList(0, 6);
                        assertThat(csvContent).contains(
                                new String[]{"Login email", "Identifier", "One-time password", "Recovery code",
                                        "First name", "Last name", "Department", "Location"},
                                new String[]{"rachel@example.com", "9012", "12se74", "rb9012", "Rachel", "Booker",
                                        "Sales", "Manchester"},
                                new String[]{"laura@example.com", "2070", "04ap67", "lg2070", "Laura", "Grey",
                                        "Depot", "London"},
                                new String[]{"craig@example.com", "4081", "30no86", "cj4081", "Craig", "Johnson",
                                        "Depot", "London"},
                                new String[]{"mary@example.com", "9346", "14ju73", "mj9346", "Mary", "Jenkins",
                                        "Engineering", "Manchester"},
                                new String[]{"jamie@example.com", "5079", "09ja61", "js5079", "Jamie", "Smith",
                                        "Engineering", "Manchester"}
                        );
                    }
                }
            }
        }
    }
}