package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    
    @GetMapping("/members/new")  // members/new로 url을 입력되면 members/createMemberFrom.html로 이동
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")   // /members/new에서 post를 하면 Member 객체 만들고 html에 입력된 form.getName()을 통해 받은 값을 memberService.join(member)하여
    public String create(MemberForm form) {    //   값을 저장, /
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping(value = "/members")    //  /members이라는 url로 조회되면 members에는 memberService.findMembers()의 반환값이 리스트로 저장되고, Model 객체에 members라는 이름으로
    public String list(Model model) {    // members라는 객체가 저장되고, View에서 members를 볼 수 있다. 마지막으로 members/memberList 경로로 이동한다.
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
