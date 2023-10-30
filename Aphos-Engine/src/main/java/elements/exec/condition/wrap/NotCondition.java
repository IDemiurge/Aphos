package elements.exec.condition.wrap;

import elements.exec.EntityRef;
import elements.exec.build.condition.ConditionContext;
import elements.exec.condition.Condition;
import framework.data.TypeData;

/**
 * Created by Alexander on 8/25/2023
 */
public class NotCondition implements Condition {
    private final Condition condition;

    public NotCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public String[] getArgNames() {
        return condition.getArgNames();
    }

    @Override
    public TypeData getData() {
        return condition.getData();
    }

    @Override
    public Condition setContext(ConditionContext context) {
        return condition.setContext(context);
    }

    @Override
    public boolean check(EntityRef ref) {
        return !condition.check(ref);
    }
}
