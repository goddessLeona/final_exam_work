package com.petra.final_exam_work.controller.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/test")
    public String memberTest(){
        return "Hello MEMBER, you are now authenticated !";
    }
}
