package newsports.model;

import android.content.Context;

import tuijian.model.NewsBean;

public interface IModel {

    void getNewsBean(Context context, onGetNewsBeanListener listener);

    interface onGetNewsBeanListener
    {
        void getNewsBeanSuccess(NewsBean.DataBean dataBean);
        void getNewsBeanFailure(String message);
    }

}
