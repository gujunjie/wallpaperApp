package video.model;

import java.util.List;

public class VideoBean  {


    /**
     * code : 200
     * message : 成功!
     * result : [{"sid":"28654154","text":"你没见过的吉利丁蛋糕制作过程！","type":"video","thumbnail":"http://wimg.spriteapp.cn/picture/2018/0927/5a0a0d10c23611e8a874842b2b4c75ab_wpd.jpg","video":"http://wvideo.spriteapp.cn/video/2018/0927/5a0a0d10c23611e8a874842b2b4c75ab_wpd.mp4","images":null,"up":"75","down":"6","forward":"8","comment":"13","uid":"22905126","name":"大厨来了","header":"http://wimg.spriteapp.cn/profile/20180810140726513883.jpg","top_comments_content":null,"top_comments_voiceuri":null,"top_comments_uid":null,"top_comments_name":null,"top_comments_header":null,"passtime":"2018-09-30 02:15:01"},{"sid":"28640493","text":"女生喂狗和男生喂狗的区别","type":"video","thumbnail":"http://wimg.spriteapp.cn/picture/2018/0923/5ba7699d19335__b.jpg","video":"http://wvideo.spriteapp.cn/video/2018/0923/5ba7699d3030d_wpd.mp4","images":null,"up":"136","down":"14","forward":"5","comment":"25","uid":"22934438","name":"情感语录","header":"http://wimg.spriteapp.cn/profile/large/2018/09/28/5bade2e07c460_mini.jpg","top_comments_content":"比你爹吃的都好","top_comments_voiceuri":"","top_comments_uid":"19830129","top_comments_name":"yi本道长","top_comments_header":"http://wimg.spriteapp.cn/profile/large/2017/07/17/596c64e34079e_mini.jpg","passtime":"2018-09-30 01:34:02"}]
     */

    private int code;
    private String message;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * sid : 28654154
         * text : 你没见过的吉利丁蛋糕制作过程！
         * type : video
         * thumbnail : http://wimg.spriteapp.cn/picture/2018/0927/5a0a0d10c23611e8a874842b2b4c75ab_wpd.jpg
         * video : http://wvideo.spriteapp.cn/video/2018/0927/5a0a0d10c23611e8a874842b2b4c75ab_wpd.mp4
         * images : null
         * up : 75
         * down : 6
         * forward : 8
         * comment : 13
         * uid : 22905126
         * name : 大厨来了
         * header : http://wimg.spriteapp.cn/profile/20180810140726513883.jpg
         * top_comments_content : null
         * top_comments_voiceuri : null
         * top_comments_uid : null
         * top_comments_name : null
         * top_comments_header : null
         * passtime : 2018-09-30 02:15:01
         */

        private String sid;
        private String text;
        private String type;
        private String thumbnail;
        private String video;
        private Object images;
        private String up;
        private String down;
        private String forward;
        private String comment;
        private String uid;
        private String name;
        private String header;
        private Object top_comments_content;
        private Object top_comments_voiceuri;
        private Object top_comments_uid;
        private Object top_comments_name;
        private Object top_comments_header;
        private String passtime;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public String getUp() {
            return up;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public String getDown() {
            return down;
        }

        public void setDown(String down) {
            this.down = down;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public Object getTop_comments_content() {
            return top_comments_content;
        }

        public void setTop_comments_content(Object top_comments_content) {
            this.top_comments_content = top_comments_content;
        }

        public Object getTop_comments_voiceuri() {
            return top_comments_voiceuri;
        }

        public void setTop_comments_voiceuri(Object top_comments_voiceuri) {
            this.top_comments_voiceuri = top_comments_voiceuri;
        }

        public Object getTop_comments_uid() {
            return top_comments_uid;
        }

        public void setTop_comments_uid(Object top_comments_uid) {
            this.top_comments_uid = top_comments_uid;
        }

        public Object getTop_comments_name() {
            return top_comments_name;
        }

        public void setTop_comments_name(Object top_comments_name) {
            this.top_comments_name = top_comments_name;
        }

        public Object getTop_comments_header() {
            return top_comments_header;
        }

        public void setTop_comments_header(Object top_comments_header) {
            this.top_comments_header = top_comments_header;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }
    }
}
