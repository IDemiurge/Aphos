package module.battle.foundation.entity.sub;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander on 8/25/2023
 * <p>
 * So what are the rules for 2+ omens on a unit? > Override Omen will prevail if present > Else just use rolls from one,
 * then the other
 */
public class OmenStack {
    List<Omen> omens = new LinkedList<>();

    public void roundEnds() {
        //usually, remove
    }

    public void unitRolls() {
        //make it usable in precalc?!
        getActiveOmen().roll();
        //return rollModData?!
    }

    private Omen getActiveOmen() {
        Collections.sort(omens, getSorter());
        return omens.get(0);
    }

    private Comparator<Omen> getSorter() {
        return (o1, o2) -> 0;
    }

    public void add(Omen omen) {
        //add chaos... init effect...
        omen.applied();
        omens.add(omen);
        //event/log...
    }
}
