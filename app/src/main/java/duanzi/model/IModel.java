package duanzi.model;

import android.content.Context;

import java.util.List;

public interface IModel {


    void getDuanZiData(Context context,onGetDuanZiDataListener listener);

    interface onGetDuanZiDataListener
    {
        void getDuanziSuccess(List<DuanZiBean.ResultBean> list);
        void getDuanziFailure(String message);
    }

}
