package com.example.hibernate.demo.entity.oneToMany;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/1/4 16:24
 * Modified By:
 * Description:
 */
@Entity
@Table(name = "book_info")
@DynamicUpdate()
@DynamicInsert()
public class BookInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 相当于identity主键生成策略
    private Integer id;

    @Column(name = "book_name", nullable = false, columnDefinition = "varchar(100) COMMENT '书名'")
    private String bookName;

    @Column(name = "publication_time", nullable = false, columnDefinition = "bigint(20) COMMENT '出版日期'")
    private Long publicationTime;

    @Column(name = "price", nullable = false, columnDefinition = "double COMMENT '书本价格'")
    private Double price;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnore
    private AuthorInfo authorInfo; // 描述书属于那一个作者

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(Long publicationTime) {
        this.publicationTime = publicationTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public AuthorInfo getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", publicationTime=" + publicationTime +
                ", price=" + price +
                '}';
    }
}
