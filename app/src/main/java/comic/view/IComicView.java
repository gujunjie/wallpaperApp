package comic.view;

import java.util.List;

import beauty.model.BaiduImage;

public interface IComicView {
    void init(List<BaiduImage.ImgsBean> list);
}
