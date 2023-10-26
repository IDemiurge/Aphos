package tests.basic_init.basic;

import org.yaml.snakeyaml.Yaml;
import system.utils.old.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Alexander on 8/25/2023
 */
public class YamlDataTest {
    @org.junit.jupiter.api.Test
    public void yamlTest(){
        FileInputStream input = null;
        try {
            input = new FileInputStream(
                    "C:\\code\\eidolons\\resources\\common\\img\\abil.yml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String s = FileManager.readFile(new File("C:\\code\\eidolons\\resources\\common\\img\\abil.yml"));
        s.trim();
        Yaml yaml = new Yaml();
        // yaml.addImplicitResolver();
        // yaml.addTypeDescription();
        // Parse the YAML file into a List of maps
        Object abilityList = yaml.load(input);
        abilityList.toString();
        // Process each ability
        // for (Map<String, Object> abilityMap : abilityList) {
        //     System.out.println("Ability Name: " + MapMaster.getNetStringForMap(abilityMap));
        //     System.out.println();
        // }
    }
}
