package com.github.andrezimmermann.gluappsample.client.mvp;

/**
 * TODO: add javadoc
 */
public interface Presenter<V extends View> {

    V getView();

    void onCreate();


    void onResume();

    void onPause();
}
