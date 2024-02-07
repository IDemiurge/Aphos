package framework.math.wrap.mx;

import org.mariuszgromada.math.mxparser.Argument;
import framework.math.wrap.IArgument;

/**
 * Created by Alexander on 11/21/2023
 */
public class MxArg implements IArgument {

    private final Argument argument;

    public MxArg(Argument argument) {
        this.argument = argument;
    }

    @Override
    public Object unwrap() {
        return argument;
    }
}
