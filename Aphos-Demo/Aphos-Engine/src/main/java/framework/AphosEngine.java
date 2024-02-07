package framework;

import framework.data.yaml.YamlBuilder;
import system.launch.Launch;
import system.launch.LaunchType;
import system.utils.EnumFinder;

/**
 * Created by Alexander on 8/23/2023
 */
public class AphosEngine {

    //GAME/TEST LAUNCH is scoped to this method!
    //Then do for COMBAT
    public static void init() {
        Launch.launch(LaunchType.TEST);
        EnumFinder.initEnumMap();

        try {
            new YamlBuilder().buildYamlFiles();
        } catch (Exception e) {
            Launch.handler().exception(e, "Yaml Build failed");
        }
    }
}
