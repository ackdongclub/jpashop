package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional //pk 값이 동일하면 같은 연속성 컨텍스트에서 하나로 관리가 된다
//test이기 때문에 마지막에 롤백을 해준다
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");
        //when
        Long saveId = memberService.join(member);

        //then
        //em.flush(); //내용읇 볼 수 있다
        assertEquals(member, memberRepository.findOne(saveId)); //저장한 멤버와 찾아온 멤버가 동일한 값이 나오면 가입이 정상적으로 완료된다

     }

     @Test
     public void 중복_회원_예외() throws Exception {

         //given
         Member member1 = new Member();
         member1.setName("kim");

         Member member2 = new Member();
         member2.setName("kim");

         //when
         memberService.join(member1);

         //then
         IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
         assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
     }

}