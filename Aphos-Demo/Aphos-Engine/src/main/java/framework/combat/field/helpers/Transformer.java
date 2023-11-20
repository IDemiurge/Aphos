package framework.combat.field.helpers;

import framework.combat.entity.field.FieldEntity;
import framework.combat.field.FieldPos;
import framework.combat.field.enums.Cell;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 10/19/2023
 */
public class Transformer {
    public static List<Cell> toCells(List<FieldEntity> list) {
        return list.stream().map(e -> e.getPos().getCell()).collect(Collectors.toList());
    }

    public static Set<FieldPos> toPos(Set<Cell> set) {
        return set.stream().map(cell -> combat().getField().getPos(cell)).collect(Collectors.toSet());
    }
}
