package com.example.hibernate.demo;

import com.example.hibernate.demo.dao.oneToMany.IAuthorRepository;
import com.example.hibernate.demo.dao.oneToMany.IBookRepository;
import com.example.hibernate.demo.entity.oneToMany.AuthorInfo;
import com.example.hibernate.demo.entity.oneToMany.BookInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author zhaolei
 * Create: 2019/4/19 18:54
 * Modified By:
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateDemoOneToManyTests {
    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private IBookRepository bookRepository;

    /**
     * 添加时设置bookInfo的作者属性把两者关联起来
     */
    @Test
    public void saveAuthor() {
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.setAuthorName("猫腻");
        authorInfo.setAuthorAge(65);
        authorRepository.save(authorInfo);
    }

    @Test
    public void saveBook() {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookName("庆余年");
        bookInfo.setPrice(160.20);
        bookInfo.setPublicationTime(new Date().getTime());
        bookInfo.setAuthorInfo(authorRepository.findById(1).get());
        bookRepository.save(bookInfo);
    }

    //删除作者会级联删除掉关联的作品，cascade:指定级联操作的行为all
    @Test
    public void deleteAuthor() {
        authorRepository.deleteById(3);
    }

    //做book的删除操作时，book实体中author实体若是：FetchType.EAGER：急加载，则无法进行删除操作。
    // 原因：逻辑相悖的矛盾，无法解决，发生了事务的回滚
    @Test
    public void deleteBook() {
        bookRepository.deleteById(4);
    }

    //在@test中执行报错，在实际接口中可以通过book得get方法懒加载获取author实体数据
    @Test
    public void findBook() {
        BookInfo bookInfo = bookRepository.findById(3).get();
        AuthorInfo authorInfo = bookInfo.getAuthorInfo();
        System.out.println(authorInfo);
    }

}
