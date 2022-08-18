package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) { //id값이 없으면(jpa는 등록되기 전까지 아이디 값이 없다) 신규등록 이다
            em.persist(item);
        }
        else {
            em.merge(item); //update와 비슷하다고 생각하면 된다
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class) //쿼리문 작성으로 아이템 찾기
                .getResultList();
    }
}
