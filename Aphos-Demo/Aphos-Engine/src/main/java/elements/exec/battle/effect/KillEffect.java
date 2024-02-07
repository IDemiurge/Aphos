package elements.exec.battle.effect;

import elements.exec.EntityRef;
import elements.exec.generic.effect.Effect;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 10/19/2023
 */
public class KillEffect  extends Effect {
    private boolean body = true;

    @Override
    protected void applyThis(EntityRef ref) {
        combat().getEntities().kill(ref, body);
    }

    public void setBody(boolean body) {
        this.body = body;
    }
}
