package duanzi.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import duanzi.model.DuanZiBean;
import duanzi.model.DuanZiModel;
import duanzi.model.IModel;
import duanzi.view.IView;

public class Presenter extends BasePresenter<IView> implements IModel.onGetDuanZiDataListener{

    private IView iView;

    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new DuanZiModel();
    }

    public void getDuanZiData(Context context)
    {
        iModel.getDuanZiData(context,this);
    }

    @Override
    public void getDuanziSuccess(List<DuanZiBean.ResultBean> list) {
            iView.showDuanZiData(list);
    }

    @Override
    public void getDuanziFailure(String message) {
           iView.showError(message);
    }
}
