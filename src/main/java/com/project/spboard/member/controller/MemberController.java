package com.project.spboard.member.controller;

import com.project.spboard.core.dto.ApiResponse;
import com.project.spboard.member.dto.JoinReqDto;
import com.project.spboard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping ("/join")
    public ResponseEntity<ApiResponse<String>> join(@RequestBody JoinReqDto joinReqDto) {
        return memberService.saveMember(joinReqDto);
    }

    //    @PostMapping("/login")
    //    public ResponseEntity<ApiResponse<LoginResDto>> login(@RequestBody LoginReqDto loginReqDto){
    //        return memberService.login(loginReqDto);
    //    }

}
