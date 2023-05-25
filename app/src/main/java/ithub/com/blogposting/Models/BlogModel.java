package ithub.com.blogposting.Models;

public class BlogModel {

    String code_head;
    String code;
    String author_name;

    public BlogModel() {
    }

    public BlogModel(String code_head ,String code, String author_name) {
        this.code_head = code_head;
        this.code = code;
        this.author_name = author_name;
    }

    public String getCode_head() {
        return code_head;
    }

    public void setCode_head(String code_head) {
        this.code_head = code_head;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }
}
