package alfredfliu.app.mynews.base;

import lombok.Getter;
import lombok.Setter;

/*
*
*
*
 */
public interface Gate {
    /**
     * when c
     * @param    param  <p>itemlist 0:url,String;1:content from url,String,2: a object casted from content</p>
     *
     */
    void run(Item param);

    public class Item{
        public Item(String url, String content, Object object) {
            Url = url;
            Content = content;
            this.object = object;
        }

        @Getter
        @Setter
        String Url;
        @Getter
        @Setter
        String Content;
        @Getter
        @Setter
        Object object;
    }
}
