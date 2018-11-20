package duanzi.model;

import java.util.List;

public class DuanZiBean {

    /**
     * code : 200
     * message : 成功!
     * result : [{"sid":"28655485","text":"大意失荆州\n\n从前，有一个农夫，家里老是丢鸡，他便设下陷阱，抓住了一只狐狸，邻居对他说，\u2018这狐狸太可怜了，你放了他吧！\u2019，农夫看了看，也觉得狐狸可怜，便大发善心，把那只狐狸给放了，从哪天开始，农夫每天早上起来，便会发现灶头上放着一碗热气腾腾的白粥，\u2026\u2026\u201d\u201c 农夫每天起床，便喝了白粥下地干活，日子过得有滋有味，直到有一天，一位和尚路过，告诉农夫，你家里妖气太重了，恐怕是出了妖孽，农夫起初不以为意，将和尚喝退，不过心中却暗暗留了心思，有一天，天还没亮，他便悄悄地起来，想要看看那白粥究竟是怎么来的，不看还好，这一看，你知道他看到什么么？\u201c一只狐狸，正坐在他家灶头上，对着那碗口\u2026\u2026","type":"text","thumbnail":null,"video":null,"images":null,"up":"921","down":"38","forward":"3","comment":"45","uid":"22091388","name":"你说我是谁","header":"http://wimg.spriteapp.cn/profile","top_comments_content":"这个段子得有20年历史了吧！","top_comments_voiceuri":"","top_comments_uid":"21122318","top_comments_name":"甄丽苏","top_comments_header":"http://wx.qlogo.cn/mmhead/Q3auHgzwzM57tibbalOOYvuAOQJdia5x8jxl7CNjGTib02LHRZGsjiaazQ/0","passtime":"2018-09-29 18:44:01"}]
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
         * sid : 28655485
         * text : 大意失荆州

         从前，有一个农夫，家里老是丢鸡，他便设下陷阱，抓住了一只狐狸，邻居对他说，‘这狐狸太可怜了，你放了他吧！’，农夫看了看，也觉得狐狸可怜，便大发善心，把那只狐狸给放了，从哪天开始，农夫每天早上起来，便会发现灶头上放着一碗热气腾腾的白粥，……”“ 农夫每天起床，便喝了白粥下地干活，日子过得有滋有味，直到有一天，一位和尚路过，告诉农夫，你家里妖气太重了，恐怕是出了妖孽，农夫起初不以为意，将和尚喝退，不过心中却暗暗留了心思，有一天，天还没亮，他便悄悄地起来，想要看看那白粥究竟是怎么来的，不看还好，这一看，你知道他看到什么么？“一只狐狸，正坐在他家灶头上，对着那碗口……
         * type : text
         * thumbnail : null
         * video : null
         * images : null
         * up : 921
         * down : 38
         * forward : 3
         * comment : 45
         * uid : 22091388
         * name : 你说我是谁
         * header : http://wimg.spriteapp.cn/profile
         * top_comments_content : 这个段子得有20年历史了吧！
         * top_comments_voiceuri :
         * top_comments_uid : 21122318
         * top_comments_name : 甄丽苏
         * top_comments_header : http://wx.qlogo.cn/mmhead/Q3auHgzwzM57tibbalOOYvuAOQJdia5x8jxl7CNjGTib02LHRZGsjiaazQ/0
         * passtime : 2018-09-29 18:44:01
         */

        private String sid;
        private String text;
        private String type;
        private Object thumbnail;
        private Object video;
        private Object images;
        private String up;
        private String down;
        private String forward;
        private String comment;
        private String uid;
        private String name;
        private String header;
        private String top_comments_content;
        private String top_comments_voiceuri;
        private String top_comments_uid;
        private String top_comments_name;
        private String top_comments_header;
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

        public Object getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Object thumbnail) {
            this.thumbnail = thumbnail;
        }

        public Object getVideo() {
            return video;
        }

        public void setVideo(Object video) {
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

        public String getTop_comments_content() {
            return top_comments_content;
        }

        public void setTop_comments_content(String top_comments_content) {
            this.top_comments_content = top_comments_content;
        }

        public String getTop_comments_voiceuri() {
            return top_comments_voiceuri;
        }

        public void setTop_comments_voiceuri(String top_comments_voiceuri) {
            this.top_comments_voiceuri = top_comments_voiceuri;
        }

        public String getTop_comments_uid() {
            return top_comments_uid;
        }

        public void setTop_comments_uid(String top_comments_uid) {
            this.top_comments_uid = top_comments_uid;
        }

        public String getTop_comments_name() {
            return top_comments_name;
        }

        public void setTop_comments_name(String top_comments_name) {
            this.top_comments_name = top_comments_name;
        }

        public String getTop_comments_header() {
            return top_comments_header;
        }

        public void setTop_comments_header(String top_comments_header) {
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
