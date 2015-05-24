package com.github.andrezimmermann.gluappsample.client.mvp;

/**
 * TODO: add javadoc
 */
public abstract class AbstractSimplePresenter<V extends View> implements Presenter<V> {

    private V view;

    public AbstractSimplePresenter(V view) {
        this.view = view;
        onCreate(view);
    }

    protected abstract void onCreate(V view);

    protected abstract void onResume(V view);

    protected abstract void onPause(V view);

    @Override
    public void onCreate() {
        onCreate(getView());
    }

    @Override
    public void onResume() {
        onResume(getView());
    }

    @Override
    public void onPause() {
        onPause(getView());
    }

    @Override
    public V getView() {
        return view;
    }
}
