package ithub.com.blogposting.Models;

public class FirebaseBlogModel {

    String id;
    String blog_id;
    String code_title;
    String code;
    String approve;
    String author;
    String email;

    public FirebaseBlogModel() {
    }

    public FirebaseBlogModel(String id, String blog_id, String code_title, String code, String approve, String author, String email) {
        this.id = id;
        this.blog_id = blog_id;
        this.code_title = code_title;
        this.code = code;
        this.approve = approve;
        this.author = author;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(String blog_id) {
        this.blog_id = blog_id;
    }

    public String getCode_title() {
        return code_title;
    }

    public void setCode_title(String code_title) {
        this.code_title = code_title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
