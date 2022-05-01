package iob.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/*
    In our opinion it will be better for us to keep this class:
    Instead of separately managing each field in an attribute map, it's easier to encapsulate everything as one object,
    especially since we are getting the 99% of the JSON object from the external news API.
    In the instance object of type "article" attribute map's it will be a value denoted by the key "theArticle".
 */
@Data
@AllArgsConstructor
@Builder
public class Article {

    @Data
    @AllArgsConstructor
    public static class ArticleSource {
        private String id;
        private String name;
    }

    private ArticleSource source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private Date publishedAt;
    private String content;
    private Category category;

}
