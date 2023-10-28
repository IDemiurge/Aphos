package elements.exec.build.condition;

import elements.exec.EntityRef;

import java.util.function.Consumer;

/**
 * Created by Alexander on 8/26/2023
 */
public class AdvancedContext extends ConditionContext {
    private Consumer<EntityRef> advancedFunc;

    public AdvancedContext(Consumer<EntityRef> advancedFunc) {
        super("");
        this.advancedFunc = advancedFunc;
    }

    public void init(EntityRef ref) {
        advancedFunc.accept(ref);
    }
}
