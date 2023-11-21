package system.launch;

/**
 * Created by Alexander on 11/20/2023
 *
 * make it reusable for each combat,
 */
public class Launch {
    static LaunchHandler launch;
    LaunchPhase phase;
    LaunchType type;

    public Launch(LaunchType type) {
        this.type = type;
    }

    private LaunchHandler start() {
        return createHandler(type);
    }
    private LaunchHandler createHandler(LaunchType type) {
        return switch(type){
            case TEST -> new LaunchHandler(this);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    @Override
    public String toString() {
        //for log?
        return super.toString();
    }

    public void setPhase(LaunchPhase phase) {
        this.phase = phase;
    }


    public static LaunchHandler handler() {
        return launch;
    }

    public static LaunchHandler launch(LaunchType type){
        return launch = new Launch(type).start();
    }

}
