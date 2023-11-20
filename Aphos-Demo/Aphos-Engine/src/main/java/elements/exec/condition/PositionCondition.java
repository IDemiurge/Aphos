package elements.exec.condition;

import framework.combat.field.enums.Cell;
import framework.combat.field.enums.Direction;
import system.utils.EnumFinder;
import elements.exec.EntityRef;
import framework.combat.entity.Entity;
import framework.combat.entity.field.FieldEntity;
import framework.combat.field.FieldPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 8/26/2023
 */
public class PositionCondition extends ConditionImpl {
    @Override
    public String[] getArgNames() {
        return super.getArgNames();
    }

    @Override
    protected boolean checkThis(EntityRef ref) {
        FieldPos requiredPos = getRequiredPos(ref);
        if (requiredPos==null)
            return true;
        if (ref.getMatch() instanceof FieldEntity) {
            FieldPos pos = ((FieldEntity) ref.getMatch()).getPos();
            return requiredPos.equals(pos);
        }
        return false;
    }

    protected FieldPos getRequiredPos(EntityRef ref) {
        if (data == null) {
            return null;
        }
        if (data.has("cell")) {
            Cell cell = EnumFinder.get(Cell.class, data.has("cell"));
            return combat().getField().getPos(cell);
        }
        if (data.has("type")) {
            //TODO
            //multi pos with all matching positions...
        }
        //TODO
        //default - source?
        if (data.has("key")) {
            Object key = data.get("key");
            Entity entity = ref.get(key.toString());
            if (entity instanceof FieldEntity) {
                FieldPos pos = ((FieldEntity) entity).getPos();

                if (data.has("adj")){
                    Object dir = data.get("adj");
                    List<String> list = null;
                    if (dir.toString().contains(",")){
                        list = new ArrayList<>(Arrays.asList(dir.toString().split(",")));
                        //any of these positions will satisfy

                        FieldPos[] positions = list.stream().map
                                (s -> pos.getAdjacent(Direction.get(s)))
                                .collect(Collectors.toList()).toArray(new FieldPos[0]);

                       return  new FieldPos(positions);
                    } else {
                        return
                                pos.getAdjacent(Direction.get(dir.toString()));
                    }
                }
            }
        }
        return null;
    }


}
