package collection.model;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmList;
import register.model.Collection;

public interface IModel {

    void getCollectionList(Context context,onGetCollectionListListener listener);

    interface onGetCollectionListListener
    {
        void onGetCollectionListSuccess(RealmList<Collection> list,Realm realm);
        void onGetCollectionListFailure(Realm realm);
    }
}
