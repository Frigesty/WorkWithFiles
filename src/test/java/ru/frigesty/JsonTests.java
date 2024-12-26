package ru.frigesty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.frigesty.modal.Phone;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Json тесты")
public class JsonTests {

    private final ClassLoader cl = JsonTests.class.getClassLoader();

    @DisplayName("Проверяем файл Phone.json")
    @Test
    void jsonTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("Phone.json");
             InputStreamReader isr = new InputStreamReader(is)) {

            Phone phone = new ObjectMapper().readValue(isr, Phone.class);

            assertThat(phone.getBrand()).isEqualTo("Samsung");
            assertThat(phone.getModel()).isEqualTo("Galaxy S23 Ultra");
            List<String> expectedNavigationSystem = Arrays.asList("GPS", "GLONASS", "Beidou", "Galileo", "QZSS");
            assertThat(phone.getNavigationSystem()).containsExactlyElementsOf(expectedNavigationSystem);
            assertThat(phone.getOperatingSystem()).isEqualTo("Android");
            assertThat(phone.getMemoryGB()).isEqualTo(256);
            assertThat(phone.getCpu().getCpuFrequencyGHz()).isEqualTo(3.36);
            assertThat(phone.getCpu().getCpuType()).isEqualTo("8 core");
            assertThat(phone.getCamera().getResolutionMP()).isEqualTo(200);
            assertThat(phone.getCamera().getAutofocus()).isTrue();
            assertThat(phone.getCamera().getZoom()).isEqualTo("100x");
        }
    }
}