package tuijian.view;

import tuijian.model.NewsBean;

public interface IView {

    void showNewsData(NewsBean.DataBean dataBean);
    void showError(String message);
}
