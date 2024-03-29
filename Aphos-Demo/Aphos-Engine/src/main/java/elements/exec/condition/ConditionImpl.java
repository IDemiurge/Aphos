package elements.exec.condition;

import elements.exec.EntityRef;
import elements.exec.build.VarHolder;
import elements.exec.build.condition.ConditionContext;
import framework.data.TypeData;

import java.util.function.Supplier;

/**
 * Created by Alexander on 8/22/2023
 */
public abstract class ConditionImpl implements Condition, VarHolder {
    protected ConditionContext context;
    protected TypeData data;

    protected abstract boolean checkThis(EntityRef ref);
    // public abstract String[] getArgs( );

    @Override
    public boolean check(EntityRef ref) {
        if (context != null)
        {
            ref = ref.copy();
            context.init(ref);
        }
        return checkThis(ref);
    }

    @Override
    public String[] getArgNames() {
        return Condition.super.getArgNames();
    }

    @Override
    public void set(String key, Object o) {
        getData().set(key, o);
    }

    @Override
    public TypeData getData() {
        if (data == null){
            data = new TypeData();
        }
        return data;
    }

    public ConditionImpl setData(TypeData data) {
        this.data = data;
        return this;
    }


    public ConditionImpl setContext(ConditionContext context) {
        this.context = context;
        return this;
    }

    @Override
    public Condition setContext(Supplier<String> keySupplier) {
        return setContext(new ConditionContext(ref -> ref.get(keySupplier.get())));
    }

    public ConditionContext getContext() {
        return context;
    }
    // abstract boolean checkEntity(T entity);
}
