package framework.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class LoginCsvReader {
    private LoginCsvReader() {}

    public record LoginData(String username, String password, String expectedMessage) {}

    public static List<LoginData> read() {
        List<LoginData> rows = new ArrayList<>();
        try (var input = LoginCsvReader.class.getClassLoader()
                .getResourceAsStream("testdata/invalid-login.csv")) {
            if (input == null) throw new IllegalStateException("CSV test data not found");
            try (var reader = new BufferedReader(new InputStreamReader(input))) {
                reader.readLine();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] p = line.split(",", -1);
                    rows.add(new LoginData(p[0], p[1], p[2]));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to read login CSV", e);
        }
        return rows;
    }
}
