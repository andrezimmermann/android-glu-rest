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



    @Override
    public void onCreate() {
        onCreate(getView());
    }


    @Override
    public V getView() {
        return view;
    }
}
