package framework.combat.entity.field;

import framework.combat.field.FieldPos;

import java.util.Map;

/**
 * Created by Alexander on 8/21/2023
 */
public class HeroUnit extends Unit {
    public HeroUnit(Map<String, Object> valueMap, int faction, FieldPos pos) {
        super(valueMap, faction, pos);
    }

    // FeatSet feats;
    // Seal summonSeal; //put marks ... by more than 1 condition perhaps

}
