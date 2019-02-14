package space.borisgk.findyourbook.localization;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Localization {
    private static Localization instance = null;

    public static Localization getInstance() {
        if (instance == null) {
            instance = new Localization();
        }
        return instance;
    }

    private Map<String, Map<String, String>> lmap = new HashMap<>();

    @SneakyThrows
    private Localization() {
        // TODO поработать с путями
        File dir = Paths.get("/home/boris/D/my programms/find-your-book/backend/findyourbook/src/main/resources/locales").toFile();
        System.out.println(dir);
        if (dir.listFiles() == null) {
            return;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            String locname = file.getName().split("\\.")[0];
            Map<String, String> locmap = new HashMap<>();
            Pattern pattern = Pattern.compile("^([^=\\s]+)\\s*=\\s*(.*)$");
            Files.readAllLines(file.toPath()).stream().forEach((s) -> {
                Matcher matcher = pattern.matcher(s);
                if (matcher.matches()) {
                    locmap.put(matcher.group(1), matcher.group(2));
                }
            });
            lmap.put(locname, locmap);
        }
    }

    public Map<String, String> getLocale(String name) {
        return lmap.get(name);
    }
}
