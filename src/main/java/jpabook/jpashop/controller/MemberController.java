package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new") //화면 열어보기
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") //데이터 등록
    public String create(@Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers(); //<== member entity를 그대로 뿌리기 보단 dto를 통해 화면에 뿌리는게 좋다 현재는 간단하기 때문에 가능
        //api를 만들땐 절대 entity를 외부로 반환하면 안된다. 만약 반환하게 되면 엔터티에 하나를 추가했을 때 api스펙이 변확되고 추가된 정보가 외부로 유출된다.
        model.addAttribute("members", members);
        return "members/memberList";
    }
    //entity는 최대한 순수하게 깨끗하게 만들어야 한다
    //그래야 유지보수하기가 편하다 실무에선 엔터티에는 핵심 비즈니스 로직만 가지고 있고 화면을 위한 로직은 없다
    //화면은 dto나 form객체에서 만든다
}
