package collection.model;

import android.content.Context;
import android.content.SharedPreferences;

import io.realm.Realm;
import io.realm.RealmList;
import register.model.Collection;
import register.model.User;

public class CollectionModel implements IModel {

    Realm realm;


    @Override
    public void getCollectionList(Context context, onGetCollectionListListener listener) {
        SharedPreferences sp=context.getSharedPreferences("loginData",Context.MODE_PRIVATE);
        String name=sp.getString("loginAccount","");
        realm=Realm.getDefaultInstance();
        User user=realm.where(User.class).equalTo("userName",name).findFirst();


        if(user==null)
        {
            listener.onGetCollectionListFailure(realm);

        }else
        {

            if(user.getList()==null||user.getList().size()==0)
          {
            listener.onGetCollectionListFailure(realm);
          }else
            {
                listener.onGetCollectionListSuccess(user.getList(),realm);
            }

        }
    }
}
