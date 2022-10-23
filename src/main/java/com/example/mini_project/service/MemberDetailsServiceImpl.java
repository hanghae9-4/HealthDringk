package com.example.mini_project.service;

import com.example.mini_project.entity.Member;
import com.example.mini_project.entity.MemberDetailsImpl;
import com.example.mini_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Member member = memberRepository.findByName(name).orElseThrow(
                () -> new RuntimeException("Not Found Member")
        );

        MemberDetailsImpl memberDetails = new MemberDetailsImpl();
        memberDetails.setMember(member);
        return memberDetails;
    }
}
