package com.tzly.ctcyh.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tzly.ctcyh.router.base.IBasePresenter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class UserBaseActivity extends AppCompatActivity {

    private Set<IBasePresenter> mAllPresenters = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPresenters();
        initPresenters();
    }

    private void addPresenters() {
        IBasePresenter[] presenters = getPresenters();
        if (presenters != null) {
            Collections.addAll(mAllPresenters, presenters);
        }
    }

    /**
     * 需要子类来实现，获取子类的IPresenter，一个activity有可能有多个IPresenter
     */
    protected abstract IBasePresenter[] getPresenters();

    //初始化presenters，
    protected abstract void initPresenters();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mAllPresenters.clear();
    }
}
