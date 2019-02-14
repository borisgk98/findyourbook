package space.borisgk.findyourbook.controler.tool;

import java.util.Arrays;

public class PathToParamsParser {
    public static String[] parse(String path) {
        if (path == null) {
            return new String[0];
        }
        return Arrays.stream(path.split("/"))
                .filter(x -> !x.equals(""))
                .toArray(String[]::new);
    }
}
