package com.traverse.command.domain.member.repository;


import com.traverse.command.domain.member.entity.Member;
import com.traverse.command.domain.member.entity.embedded.OAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByoAuth(OAuth oAuth);

}
