package beauty.view;

import java.util.List;

import beauty.model.BaiduImage;

public interface IBeautyView {
    void init(List<BaiduImage.ImgsBean> list);

    void netWorkError();
}
