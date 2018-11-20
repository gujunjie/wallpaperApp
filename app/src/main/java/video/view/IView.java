package video.view;

import java.util.List;

import video.model.VideoBean;

public interface IView {

    void showVideoData(List<VideoBean.ResultBean> list);

    void showError(String message);
}
