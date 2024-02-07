package elements.exec.battle.targeting;

import framework.data.consts.StringConsts;
import module.battle.foundation.entity.field.FieldEntity;
import module.battle.foundation.entity.field.Unit;
import utils.old.NumberUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 8/22/2023
 */
public class TargetGroup {
    private final List<Integer> ids;
    private final List<FieldEntity> targets;

    public TargetGroup(List<FieldEntity> targets) {
        this.targets = targets;
        ids = targets.stream().map(t -> t.getId()).collect(Collectors.toList());
    }

    public TargetGroup(String ids) {
        this. ids = Arrays.asList(ids.split(StringConsts.CONTAINER_SEPARATOR))
                .stream().map(s -> NumberUtils.getInt(s)).collect(Collectors.toList());
        this.targets = this.ids.stream().map(id -> combat().getById(id, Unit.class)).collect(Collectors.toList());
    }
    public List<FieldEntity> getTargets() {
        return targets;
    }
    public List<Integer> getTargetsIds() {
        return ids;
    }
}
