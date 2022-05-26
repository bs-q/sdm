package q.sdm.ui.base.activity;

public interface BaseDbCallback <T>{
    void doSuccess(T response);
    void doError(Throwable throwable);
}
