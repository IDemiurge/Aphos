package elements.exec.generic.effect;

import elements.exec.EntityRef;
import elements.exec.build.VarHolder;
import framework.data.TypeData;
import org.apache.commons.lang3.NotImplementedException;
import system.log.SysLog;
import system.log.result.EffectResult;

import java.util.Map;

/**
 * Created by Alexander on 8/21/2023
 */
public abstract class Effect implements VarHolder {
    protected TypeData data;
    protected EffectResult effectResult;

    public TypeData getData() {
        if (data == null) {
            data = new TypeData();
        }
        return data;
    }

    public final String[] getArgNames() {
         return getArgs().split("\\|");
    }

    public String getArgs() {
        return "";
    }

    public void setData(TypeData effectData) {
        this.data = effectData;
    }

    public void setData(Map<String, Object> map) {
        setData(new TypeData(map));
    }

    public EffectResult apply(EntityRef ref) {
        effectResult = new EffectResult();
        SysLog.printLine(SysLog.LogChannel.Main, "Applying", this, "on", ref.getTarget());
        applyThis(ref);
        SysLog.printLine(SysLog.LogChannel.Main, "Applied", this, "on", ref.getTarget(), effectResult);
        return effectResult;
    }

    protected abstract void applyThis(EntityRef ref);


    @Override
    public void set(String key, Object o) {
        setValue(key, o);
    }
    public Effect setValue(int index, Object value) {
        return setValue(getArgNames()[index], value);
    }

    public Effect setValue(String key, Object value) {
        getData().set(key, value);
        return this;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        if (data == null)
            return getClass().getSimpleName();
        return getClass().getSimpleName() + " with " + data.toSimpleString();
    }

    public EffectResult getResult() {
        return effectResult;
    }

    public void setAdditionalFx(Effect additionalFx) {
        throw new NotImplementedException();
    }
}
