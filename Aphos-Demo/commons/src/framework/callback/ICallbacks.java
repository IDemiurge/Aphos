package framework.callback;

/**
 * Created by Alexander on 2/7/2024
 */
public interface ICallbacks<T> {
    void invoke (T key, Object data);
}
