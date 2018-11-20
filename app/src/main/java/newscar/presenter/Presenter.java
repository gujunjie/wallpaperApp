package newscar.presenter;

import android.content.Context;

import base.BasePresenter;
import newscar.model.NewsCarModel;
import newscar.model.IModel;
import newscar.view.IView;
import tuijian.model.NewsBean;


public class Presenter extends BasePresenter<IView> implements IModel.onGetNewsBeanListener{

    private IView iView;

    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new NewsCarModel();
    }

    public void getNewsBean(Context context)
    {
        iModel.getNewsBean(context,this);
    }


    @Override
    public void getNewsBeanSuccess(NewsBean.DataBean dataBean) {
        iView.showNewsData(dataBean);
    }

    @Override
    public void getNewsBeanFailure(String message) {
        iView.showError(message);
    }
}
