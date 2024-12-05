package ru.frigesty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.frigesty.modal.Phone;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Json тесты")
public class JsonTests {

    private final ClassLoader cl = JsonTests.class.getClassLoader();

    @DisplayName("Проверяем файл Phone.json")
    @Test
    void jsonTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("Phone.json")) {
            assert is != null;
            try (InputStreamReader isr = new InputStreamReader(is)) {
                Phone phone = new ObjectMapper().readValue(isr, Phone.class);
                assertEquals("Samsung", phone.getBrand());
                assertEquals("Galaxy S23 Ultra", phone.getModel());
                List<String> expectedNavigationSystem = Arrays.asList("GPS", "GLONASS", "Beidou", "Galileo", "QZSS");
                assertEquals(expectedNavigationSystem, phone.getNavigationSystem());
                assertEquals("Android", phone.getOperatingSystem());
                assertEquals(256, phone.getMemoryGB());
                assertEquals(3.36, phone.getCPU().getCPUFrequencyGHz());
                assertEquals("8 core", phone.getCPU().getCPUType());
                assertEquals(200, phone.getCamera().getResolutionMP());
                assertTrue(phone.getCamera().getAutofocus());
                assertEquals("100x", phone.getCamera().getZoom());
            }
        }
    }
}