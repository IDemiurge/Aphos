package elements.content.generic.stats;

public interface Stat {
    default String getName(){
        return toString();
    }
}