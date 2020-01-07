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
     * @param    arg  <p>itemlist 0:url,String;1:content from url,String,2: a object casted from content</p>
     *
     */
    void run(Item arg);

    public class Item{
        public Item(Boolean Success,String url, String content, Object object) {
            this.Success =Success;
            Url = url;
            Content = content;
            this.object = object;
        }
        @Getter
        @Setter
        Boolean Success;
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
