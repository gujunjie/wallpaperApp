package collection.presenter;

import android.content.Context;

import base.BasePresenter;
import collection.model.CollectionModel;
import collection.model.IModel;
import collection.view.IView;
import io.realm.Realm;
import io.realm.RealmList;
import register.model.Collection;

public class Presenter extends BasePresenter<IView> implements IModel.onGetCollectionListListener{

    private IView iView;
    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new CollectionModel();
    }

    public void getCollectionList(Context context)
    {
        iModel.getCollectionList(context,this);
    }

    @Override
    public void onGetCollectionListSuccess(RealmList<Collection> list, Realm realm) {
        iView.initAdapter(list,realm);
    }

    @Override
    public void onGetCollectionListFailure(Realm realm) {
          iView.tipsNeedToCollect(realm);
    }
}
