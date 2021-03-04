package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// readOnly = 성능상 조금 더 좋다.
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
    * 회원가입
    */
    // 세부 메소드에 트랜잭션이 우선시 된다.
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원 문제 -> 예외
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }


    /*회원 전체 조회*/
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    /*회원 조회*/
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
