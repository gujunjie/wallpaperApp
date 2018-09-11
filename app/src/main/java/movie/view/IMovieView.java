package movie.view;

import java.util.List;

import beauty.model.BaiduImage;

public interface IMovieView {
    void init(List<BaiduImage.ImgsBean> list);
}
