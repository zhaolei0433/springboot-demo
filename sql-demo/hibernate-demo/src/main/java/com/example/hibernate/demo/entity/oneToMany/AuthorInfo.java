package com.example.hibernate.demo.entity.oneToMany;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author zhaolei
 * Create: 2019/2/28 17:56
 * Modified By:
 * Description:
 */
@Entity
@Table(name = "author_info")
@DynamicUpdate()
@DynamicInsert()
public class AuthorInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 相当于identity主键生成策略
    private Integer id;

    @Column(name = "author_name", nullable = false, columnDefinition = "varchar(100) COMMENT '作者名'")
    private String authorName;

    @Column(name = "author_age", nullable = false, columnDefinition = "int(20) COMMENT '作者年龄'")
    private Integer authorAge;

    /**
     * 描述一个作者有多本书
     * 特别注意：onetomany标识这是级联1对多的关系。cascade={CascadeType.ALL}表示主表的增查删改都会直接通过关联字段对从表进行相应操作。
     * 例如删除主表的一个author实例，从表与author相关联books将被删除。
     * fetch=FetchType.EAGER表示急加载，即指一旦主表进行了相应操作，则从表也将立即进行相应的级联操作。
     * 例如，一旦读取了author表的某一个实例，则author会立即加载books；而fetch=FetchType.LAZY为懒加载，当需要使用到getBooks()方法时，才会读取相关联的级联表数据
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "authorInfo")
    @JsonIgnore //作用：在json序列化时将java bean中的一些属性忽略掉，序列化和反序列化都受影响。
    private Set<BookInfo> bookInfos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getAuthorAge() {
        return authorAge;
    }

    public void setAuthorAge(Integer authorAge) {
        this.authorAge = authorAge;
    }

    public Set<BookInfo> getBookInfos() {
        return bookInfos;
    }

    public void setBookInfos(Set<BookInfo> bookInfos) {
        this.bookInfos = bookInfos;
    }

    @Override
    public String toString() {
        return "AuthorInfo{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", authorAge=" + authorAge +
                '}';
    }
}
