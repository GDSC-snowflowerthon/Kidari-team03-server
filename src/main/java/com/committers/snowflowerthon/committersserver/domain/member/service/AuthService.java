package com.committers.snowflowerthon.committersserver.domain.member.service;

import com.committers.snowflowerthon.committersserver.auth.config.OAuth2MemberDto;
import com.committers.snowflowerthon.committersserver.auth.github.GitHubService;
import com.committers.snowflowerthon.committersserver.auth.jwt.CustomToken;
import com.committers.snowflowerthon.committersserver.auth.jwt.JwtUtils;
import com.committers.snowflowerthon.committersserver.domain.item.entity.Item;
import com.committers.snowflowerthon.committersserver.domain.item.entity.ItemRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Role;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.UnivRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtUtils jwtUtils;
    private final ItemRepository itemRepository;
    private final UnivRepository univRepository;
    private final MemberRepository memberRepository;
    private final GitHubService gitHubService;

    public HttpServletResponse login(HttpServletResponse response, String accessToken, String refreshToken) {
        // 쿠키 생성 및 추가
        Cookie accessCookie = new Cookie("accessToken", accessToken);
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);

        accessCookie.setPath("/");
        refreshCookie.setPath("/");

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        log.info("AuthService의 login");
        log.info("accessToken -> {}", accessToken);
        log.info("refreshToken -> {}", refreshToken);
        log.info("accessCookie -> {}", accessCookie);
        log.info("refreshCookie -> {}", refreshCookie);

        return response;
    }

    public CustomToken setToken(Member member, HttpServletResponse response) {

        log.info("로그인 로직");

        HashMap<String, String> m = new HashMap<>();
        m.put("nickname", String.valueOf(member.getNickname()));

        String accessToken = jwtUtils.createAccessToken(member);
        String refreshToken = jwtUtils.createRefreshToken(member);

        // redis에 refreshToken 업데이트
        jwtUtils.updateUserRefreshToken(member, refreshToken);

        // 토큰 DTO 생성
        CustomToken token = CustomToken.builder()
                .accessToken(jwtUtils.createAccessToken(member))
                .refreshToken(jwtUtils.createRefreshToken(member))
                .build();

        log.info("{}", token);

        return token;
    }

    public Optional<Member> signUp(OAuth2MemberDto memberDto) {

        log.info("회원가입 로직");
        log.info("authService에서 memberDto -> {}", memberDto.getNickname());

        // item 객체 만들고 저장하기
        Item item = Item.builder().build(); // 자동 생성
        itemRepository.save(item);
        log.info("아이템 객체 생성: " + item.getId() + " " + item.getSnowId() + " " + item.getHatId() + " " + item.getDecoId());

        // default univ 객체 저장하기
        Optional<Univ> defaultUniv = univRepository.findById(1L);
        if (!defaultUniv.isPresent()) {
            defaultUniv = Optional.ofNullable(Univ.builder()
                    .univName("error")
                    .belonged(0L)
                    .totalHeight(0L)
                    .build());
            log.info("error 로직 대학 이름 -> {}", defaultUniv.get().getUnivName());
            univRepository.save(defaultUniv.get());
        }

        // member 객체 만들고 저장하기
        Member member = Member.builder()
                .nickname(memberDto.getNickname())
//                .snowflake(Long.valueOf(0)) // 자동 생성
//                    .snowmanHeight(Long.valueOf(1)) // 자동 생성
//                    .attacking(Long.valueOf(0)) // 자동 생성
//                    .damage(Long.valueOf(0)) // 자동 생성
                .role(Role.ROLE_MEMBER)
                .univ(defaultUniv.get())
                .item(itemRepository.findById(item.getId()).get())
                .build();

        log.info("member -> {}", member.getNickname());

        memberRepository.save(member);

        // 눈송이 수 업데이트
        gitHubService.setSnowflake(memberDto);

        return memberRepository.findById(member.getId());
    }
}
