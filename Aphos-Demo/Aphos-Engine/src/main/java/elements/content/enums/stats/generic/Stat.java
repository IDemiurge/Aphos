package elements.content.enums.stats.generic;

public interface Stat {
    default String getName(){
        return toString();
    }
}
