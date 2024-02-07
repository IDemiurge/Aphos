package system.path;

/**
 * Created by Alexander on 11/5/2023
 */
public class PathResolver {

    // public String resolveToHandle(ResourceType type, ResPath respath){
    // }
    public String resolve(ResPath respath){
        StringBuilder builder = new StringBuilder();
        builder.append(respath.getPrefix().orElse(""));
        builder.append(respath.getName());
        builder.append(respath.getFormat());

        return builder.toString();
    }
}
