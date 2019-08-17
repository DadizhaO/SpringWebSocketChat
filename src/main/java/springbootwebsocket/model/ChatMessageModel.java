package springbootwebsocket.model;


import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class ChatMessageModel {
    @Id
    private String id;

    private String text;
    private String author;
    private LocalDate createDate;

    public ChatMessageModel() {
    }

    public ChatMessageModel(String text, String author, LocalDate createDate) {
        this.text = text;
        this.author = author;
        this.createDate = createDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ChatMessageModel{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
