package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em; //spring이 EntityManager를 만들어서 주입시켜준다


   // @PersistenceUnitManagerFactory //내가 직접 매니저 팩토리를 주입

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findALL() {
        //jpql작성 조회 타입, 반환 타입 / 아이디로 검색 / from의 대상이 테이블이 아닌 객체
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        //객체에 대한 쿼리로 작성해야 한다
    }

    public List<Member> findByName(String name) { //이름으로 검색
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
