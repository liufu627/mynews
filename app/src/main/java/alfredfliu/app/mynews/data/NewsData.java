package alfredfliu.app.mynews.data;

import java.util.List;

public class NewsData {


    /**
     * data : {"countcommenturl":"/client/content/countComment/","more":"/static/api/news/10007/list_2.json","news":[],"title":"北京","topic":[],"topnews":[{"comment":true,"commentlist":"/static/api/news/10007/53/147253/comment_1.json","commenturl":"/client/user/newComment/147253","id":147253,"pubdate":"2015-10-19 07:18","title":"市教委：中高考英语试卷结构不变","topimage":"/static/images/2015/10/19/36/1053274969EORV.jpg","type":"news","url":"/static/html/2015/10/19/714C6E504A6F1B7869277C42.html"},{"comment":true,"commentlist":"/static/api/news/10007/54/147254/comment_1.json","commenturl":"/client/user/newComment/147254","id":147254,"pubdate":"2015-10-19 07:22","title":"警方披露\u201c二环十三郎\u201d审讯内容","topimage":"/static/images/2015/10/19/14/105235144895YS.jpg","type":"news","url":"/static/html/2015/10/19/724F6B554E6B1D7E68267C45.html"},{"id":147315,"title":"百年彭家寨 \u201c国保\u201d吊脚楼","topimage":"/static/images/2015/10/20/80/467283565SGJ.jpg","url":"/static/html/2015/10/20/754868564D681E7C68227840.html","pubdate":"2015-10-20 09:57","comment":true,"commenturl":"/client/user/newComment/147315","type":"news","commentlist":"/static/api/news/10012/15/147315/comment_1.json"},{"id":147274,"title":"年度最佳旅行地 让你砰然心动","topimage":"/static/images/2015/10/19/70/467283565C7N.jpg","url":"/static/html/2015/10/19/704D6C524D681D7E6D217D44.html","pubdate":"2015-10-19 11:42","comment":true,"commenturl":"/client/user/newComment/147274","type":"news","commentlist":"/static/api/news/10012/74/147274/comment_1.json"}]}
     * retcode : 200
     */

    private DataBean data;
    private int retcode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public static class DataBean {
        /**
         * countcommenturl : /client/content/countComment/
         * more : /static/api/news/10007/list_2.json
         * news : []
         * title : 北京
         * topic : []
         * topnews : [{"comment":true,"commentlist":"/static/api/news/10007/53/147253/comment_1.json","commenturl":"/client/user/newComment/147253","id":147253,"pubdate":"2015-10-19 07:18","title":"市教委：中高考英语试卷结构不变","topimage":"/static/images/2015/10/19/36/1053274969EORV.jpg","type":"news","url":"/static/html/2015/10/19/714C6E504A6F1B7869277C42.html"},{"comment":true,"commentlist":"/static/api/news/10007/54/147254/comment_1.json","commenturl":"/client/user/newComment/147254","id":147254,"pubdate":"2015-10-19 07:22","title":"警方披露\u201c二环十三郎\u201d审讯内容","topimage":"/static/images/2015/10/19/14/105235144895YS.jpg","type":"news","url":"/static/html/2015/10/19/724F6B554E6B1D7E68267C45.html"},{"id":147315,"title":"百年彭家寨 \u201c国保\u201d吊脚楼","topimage":"/static/images/2015/10/20/80/467283565SGJ.jpg","url":"/static/html/2015/10/20/754868564D681E7C68227840.html","pubdate":"2015-10-20 09:57","comment":true,"commenturl":"/client/user/newComment/147315","type":"news","commentlist":"/static/api/news/10012/15/147315/comment_1.json"},{"id":147274,"title":"年度最佳旅行地 让你砰然心动","topimage":"/static/images/2015/10/19/70/467283565C7N.jpg","url":"/static/html/2015/10/19/704D6C524D681D7E6D217D44.html","pubdate":"2015-10-19 11:42","comment":true,"commenturl":"/client/user/newComment/147274","type":"news","commentlist":"/static/api/news/10012/74/147274/comment_1.json"}]
         */

        private String countcommenturl;
        private String more;
        private String title;
        private List<?> news;
        private List<?> topic;
        private List<TopnewsBean> topnews;

        public String getCountcommenturl() {
            return countcommenturl;
        }

        public void setCountcommenturl(String countcommenturl) {
            this.countcommenturl = countcommenturl;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<?> getNews() {
            return news;
        }

        public void setNews(List<?> news) {
            this.news = news;
        }

        public List<?> getTopic() {
            return topic;
        }

        public void setTopic(List<?> topic) {
            this.topic = topic;
        }

        public List<TopnewsBean> getTopnews() {
            return topnews;
        }

        public void setTopnews(List<TopnewsBean> topnews) {
            this.topnews = topnews;
        }

        public static class TopnewsBean {
            /**
             * comment : true
             * commentlist : /static/api/news/10007/53/147253/comment_1.json
             * commenturl : /client/user/newComment/147253
             * id : 147253
             * pubdate : 2015-10-19 07:18
             * title : 市教委：中高考英语试卷结构不变
             * topimage : /static/images/2015/10/19/36/1053274969EORV.jpg
             * type : news
             * url : /static/html/2015/10/19/714C6E504A6F1B7869277C42.html
             */

            private boolean comment;
            private String commentlist;
            private String commenturl;
            private int id;
            private String pubdate;
            private String title;
            private String topimage;
            private String type;
            private String url;

            public boolean isComment() {
                return comment;
            }

            public void setComment(boolean comment) {
                this.comment = comment;
            }

            public String getCommentlist() {
                return commentlist;
            }

            public void setCommentlist(String commentlist) {
                this.commentlist = commentlist;
            }

            public String getCommenturl() {
                return commenturl;
            }

            public void setCommenturl(String commenturl) {
                this.commenturl = commenturl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTopimage() {
                return topimage;
            }

            public void setTopimage(String topimage) {
                this.topimage = topimage;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
