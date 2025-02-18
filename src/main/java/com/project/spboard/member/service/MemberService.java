package com.project.spboard.member.service;

import com.project.spboard.core.dto.ApiResponse;
import com.project.spboard.member.dto.JoinReqDto;
import com.project.spboard.member.entity.Member;
import com.project.spboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<ApiResponse<String>> saveMember(JoinReqDto joinReqDto) {
        try {
            Member member = Member.builder()
                .email(joinReqDto.getEmail())
                .name(joinReqDto.getName())
                .password(passwordEncoder.encode(joinReqDto.getPassword()))
                .build();
            memberRepository.save(member);
            //TODO location 추가 예정 : /member/[id]를 통해 유저 정보 확인
            URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(member.getId())
                .toUri();

            return ApiResponse.created(location, null);
        } catch (Exception e) {
            return ApiResponse.error("회원가입 요청이 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<ApiResponse<LoginResDto>> login(LoginReqDto loginReqDto){
//        try {
//            return memberRepository.findByEmail(loginReqDto.getEmail())
//                    .map(member -> {
//                        if(passwordEncoder.matches(loginReqDto.getPassword(),member.getPassword())){
//                            return ApiResponse.success(member.toLoginResDto());
//                        } else{
//                            return ApiResponse.<LoginResDto>error("로그인에 실패했습니다.", HttpStatus.BAD_REQUEST);
//                        }
//                    })
//                    .orElseThrow();
//        } catch(Exception e) {
//            System.out.println(e);
//            return ApiResponse.error("로그인에 실패했습니다.", HttpStatus.BAD_REQUEST);
//        }
//    }

}
