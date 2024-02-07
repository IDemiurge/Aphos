package system.path;

import java.util.Optional;

/**
 * Created by Alexander on 11/5/2023
 */
public class ResPath {
    Optional<String> prefix;
    String name;
    String format;

    public ResPath(String prefix, String name, String format) {
        this.prefix =  Optional.ofNullable(prefix);
        this.name = name;
        this.format = format;
    }

    public Optional<String> getPrefix() {
        return prefix;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }
}
