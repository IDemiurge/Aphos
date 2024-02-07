package game.dto;

import com.badlogic.gdx.scenes.scene2d.Actor;
import framework.actor.Aggregate;

/**
 * Created by Alexander on 11/5/2023
 * what would determine the aggregate components?
 *
 * We will need Factory of course
 *
 */
public class DtoComp <T extends GdxDto> extends Actor {
    protected T dto;
    Aggregate aggregate;

}
