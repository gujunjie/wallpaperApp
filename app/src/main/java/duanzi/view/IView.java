package duanzi.view;

import java.util.List;

import duanzi.model.DuanZiBean;

public interface IView {

    void showDuanZiData(List<DuanZiBean.ResultBean> list);

    void showError(String message);
}
