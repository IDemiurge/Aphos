package elements.content.enums.stats.action;

import elements.content.enums.stats.generic.Stat;

/**
 * Created by Alexander on 8/2/2023
 */
public enum ActionProp implements Stat {
    Vars,
    Exec_data_boost{
        @Override
        public String getName() {
            return "exec data boost";
        }
    },
    Exec_data {
        @Override
        public String getName() {
            return "exec data";
        }
    }
}
