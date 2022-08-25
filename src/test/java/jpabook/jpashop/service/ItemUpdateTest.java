package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception{
        //given
        Book book = em.find(Book.class, 1L);

        //transaction
        book.setName("sdfasd");
        // book에서 set으로 이름을 변경하고 jpa가 commit이 되면 jpa가 변경분을 자동으로 찾아서 반영을한다
        // 변경감지 == dirty checking
        // ex) order의 cancel < status를 cancel로 바꾸고 이를 디비에 반영하는 코드르 짜지 않았음



        //then

     }
}
