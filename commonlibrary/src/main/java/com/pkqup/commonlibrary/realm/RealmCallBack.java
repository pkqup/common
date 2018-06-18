package com.pkqup.commonlibrary.realm;

import io.realm.Realm;

/**
 * @CreatedbBy: liucun on 2018/6/17.
 * @Describe:
 */
public interface RealmCallBack {

    void onExecute(Realm realm);

    void onExecute();

    void onSuccess();

    void onError();
}
