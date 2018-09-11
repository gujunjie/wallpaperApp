package collection.view;

import io.realm.Realm;
import io.realm.RealmList;
import register.model.Collection;

public interface IView {

    void initAdapter(RealmList<Collection> list, Realm realm);

    void tipsNeedToCollect(Realm realm);
}
