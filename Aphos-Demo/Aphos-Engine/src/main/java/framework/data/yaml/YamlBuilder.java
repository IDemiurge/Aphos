package framework.data.yaml;

import elements.exec.Executable;
import elements.exec.build.ExecBuilder;
import elements.content.enums.stats.action.ActionProp;
import framework.data.DataManager;
import org.yaml.snakeyaml.Yaml;
import system.launch.Launch;
import system.launch.LaunchException;
import system.log.SysLog;
import system.ExceptionMaster;
import utils.old.FileManager;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexander on 8/25/2023 Special rules: [param] triplet / / / #tag => .setProcessComments(true)?
 * <p>
 * Entity - easy? We sure can have a mapper for that ; dataMap is good enough BTW - consider that there is no backward
 * compatibility for creating YAML from java object... Do we need it? // yaml.parse(null).forEach(event -> event.);
 * IDEA: process comments first? //for selective loading, gonna have to use parse() as GPT suggested
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class YamlBuilder {
    private static String ROOT_PATH;

    public void buildYamlFiles() throws LaunchException {
        buildYamlFile("units", "Unit", "Faction", false);
        buildYamlFile("actions", "Action", "Faction", true);
        buildYamlFile("passives", "Passive", "Faction", true);
        buildYamlFile("party", "Party", "Faction", true);
        //PERKS can really just be an ENUM !
    }

    public void buildYamlFile(String filename, String typeKey, String listNameProp, boolean parseVars) throws LaunchException {
        // Yaml yaml = new Yaml(new CustomMapConstructor());
        //set map constructor to STR!
        // Map<String, List<Map>> load = new LinkedStringMap();

        Yaml yaml = new Yaml();
        File temp = new File(getClass().getClassLoader().getResource(filename + ".yml").getFile());
        String content = FileManager.readFile(temp);
        Map loaded = yaml.load(content);

        for (Object key : loaded.keySet()) {
            processTypesMap(typeKey, key.toString(), (List<Map>) loaded.get(key), listNameProp, parseVars);
        }
    }

    private void processTypesMap(String typeKey, String docName, List<Map> typeData, String listNameProp, boolean parseVars) {
        for (Map types : typeData) {
            for (Object typeNode : types.keySet()) {
                Map typeMap = (Map) types.get(typeNode);
                String name = typeNode.toString();
                try {
                    initAndAddTypeMap(name, typeMap, typeKey, docName, listNameProp, parseVars);
                } catch (Exception e) {
                    Launch.handler().failYamlType(name, typeKey, typeNode, e);
                }
            }

        }
    }

    private void initAndAddTypeMap(String name, Map typeMap, String typeKey, String docName, String listNameProp, boolean parseVars) {
        typeMap.put(listNameProp, docName);
        typeMap.put("Type", typeKey);
        typeMap.put("Name", name);
        Set valueKeySet = new HashSet(typeMap.keySet());

        //save only data, parse on demand! (we'll have a test that parses all in advance)
        if (parseVars) {
            boolean execType = docName.toLowerCase().contains("exec");
            for (Object value : valueKeySet) { //why not just get()?
                if (value.toString().equals(ActionProp.Exec_data.getName())) {
                    try {
                        //are we still parsing it all on init()? just save data!
                        String execKey = parseExec(name, typeMap.get(value), execType);
                        typeMap.put(value, execKey); //what is the point?
                    } catch (Exception e) {
                        SysLog.printLine(SysLog.LogChannel.Error, "Exec build failed: ", typeKey, docName, name,
                                "; DATA ---> \n", typeMap.get(value));
                        ExceptionMaster.printStackTrace(e);

                    }
                }
            }
            if (!execType) {
                //ACTIONS AND PASSIVES
                Map varMap = (Map) typeMap.remove("vars");
                if (varMap == null) {
                    varMap = (Map) typeMap.remove("Vars");
                }
                if (varMap != null) {
                    DataManager.addVarData(name, varMap);
                }
                DataManager.addTypeData(typeKey, name, typeMap);
            }
        } else {
            DataManager.addTypeData(typeKey, name, typeMap);
        }
    }

    private String parseExec(String typeName, Object o, boolean execType) {
        // if (!execType) {
        ExecBuilder.setExecData(typeName, o);
        return typeName;
        // }
        // Executable exec = ExecBuilder.build(o);
        //
        // StringBuilder execKey = new StringBuilder(typeName);
        // Map args = getMap(o, "args");
        // if (args != null) {
        //     for (Object key : args.keySet()) {
        //         execKey.append("_").append(key).append("=").append(args.get(key));
        //     }
        // }
        // ExecBuilder.addExec(execKey.toString(), exec);
        // return execKey.toString();
    }


    public Executable applyEffectArgs(Executable base, Map<String, Object> args) {
        // Executable clone = clone(preset);
        for (String s : args.keySet()) {
            setVariable(s, args.get(s), base);
        }
        return base;
    }


    private void setVariable(String s, Object o, Executable base) {
        //TODO
        // Pair<Targeting, Effect> pair = base.getTargetedEffects().get(i);
        // pair.getRight().getData().set(name, o);
        // pair.getLeft().getData().set(name, o);
    }


    public static Map getMap(Object node, String type) {
        return (Map) ((Map) node).get(type);
    }

    // private int getInt(Object node, String name) {
    //     return (int) ((Map) node).get(name);
    // }
    //
    // private String getS(Object node, String name) {
    //     return ((Map) node).get(name).toString();
    // }
    //
    private String getMapName(Object node) {
        return ((Map) node).keySet().toArray()[0].toString();
    }

    /*
    doc is map of name + type=> list of types
    type is a map with inlaid lists: [Pas, Act]
    >> active type is created from nested data

    exec
    inside action/passive,

    using anchors
    what's the advantage over having a managed map of templates?

    > support multi exec's as list

     */
}
