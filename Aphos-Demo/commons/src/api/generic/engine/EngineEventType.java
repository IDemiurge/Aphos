package api.generic.engine;

/**
 * Created by Alexander on 2/7/2024
 * client decides how to display this themselves, eh?
 * we need to report all the notable 'actions' and add some data about how they went
 * should stick to up to 10 types, the rest supplied via data
 */
public enum EngineEventType {
    action_started, //group events between this and finished() ?
    action_finished,

    //triggers?

    damage, //will group?
    unit_effect, //versatile
    zone_effect,
    value_changed, //for hp/soul/armor/sanity
    unit_moves,
    unit_destroyed,
    unit_spawned,
    unit_status_changed,

    //++ for ui updates?
    ;
}
