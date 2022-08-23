package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //jpa의 모든 데이터 변경이나 로직은 모두 transaction안에서 실행되어야 한다.
@RequiredArgsConstructor //lombok final에 있는 필드만 가지고 생성자를 만들어준다 > 즉 3번을 자동 생성
//default는 readOnly = false이기 때문에 전체에 true를 주고 join에 따로 transaction을 줘서 우선권을 가지게 하여 거기만 false가 적용되게 한다
//readOnly = true하면 조회에 최적화 된다
public class MemberService {

    /*
    1.
    @Autowired
    private MemberRepository memberRepository;
    */
    //보통은 이렇게 많이 사용하는데 이 문법의 단점이 프로그래밍 하다가 이를 바꾸야 할 때가 있는데 변경 불가능
    private final MemberRepository memberRepository;//final을 쓰면 컴파일 시 체크 가능

    //SetterInjection
    /*
    2.
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/
    //test코드 작성 시 주입 가능
    //단점은 runtime 시 누가 이걸 바꿀 수 있음

    //3. 생성자 인젝션
    //권장하는 방법
    /*
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */

    //생성 시 이미 생성이 끝나기 때문에 중간에 set해서 바꿀 수 없음
    //spring에서 생성자가 1개만 있으면 거기에 자동적으로 Autowired를 해준다


    /*회원 가입*/
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //회원 가입 시 중복 이름 사용 불가
        memberRepository.save(member);
        return member.getId();
    }
    //회원 가입 시 중복 이름 사용 불가
    private void validateDuplicateMember(Member member) {
        //Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        //이렇게 하면 만약에 동시간대에 동일한 이름으로 가입을 하면??
        //그래서 이를 방지하기 위해 멀티쓰레드를 대비해 디비의 멤버를 유니크 제약조건을 달아준다
        if(!findMembers.isEmpty()) { //findMembers가 빈값이 아니면 이미 사용중인 이름이 있다는 뜻
            throw new IllegalStateException("이미 존재하는 회원입니다"); //IllegalStateException <-- 중복에러
        }
    }
    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findALL();
    }

    //회원 하나만 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
