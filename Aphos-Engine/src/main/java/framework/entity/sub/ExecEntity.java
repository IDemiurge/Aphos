package framework.entity.sub;

import elements.exec.Executable;
import elements.exec.build.ExecBuilder;
import elements.exec.condition.Condition;
import framework.entity.field.Unit;

import java.util.Map;

/**
 * Created by Alexander on 10/31/2023
 */
public class ExecEntity extends UnitSubEntity {

    private final Map<String, Object> varMap;
    protected Condition activationCondition;

    public ExecEntity(Map<String, Object> valueMap, Map<String, Object> varMap, Unit unit) {
        super(valueMap, unit);
        this.varMap = varMap;
    }

    public Map<String, Object> getVarMap() {
        return varMap;
    }


    public Executable getExecutable() {
        return getExecutable(false);
    }
    public Executable getExecutable(boolean boost) {
        Executable executable = ExecBuilder.initExecutable(this, boost);
        //are they stateful? Or are we just creating anew to support Var Map changes?
        return executable;
    }

}
