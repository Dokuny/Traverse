package com.traverse.command.domain.member.service;

import static com.traverse.command.domain.member.exception.MemberErrorCode.NOT_EXISTS_MEMBER;

import com.traverse.command.domain.member.dto.MemberInfo;
import com.traverse.command.domain.member.dto.MemberModifyForm;
import com.traverse.command.domain.member.entity.Member;
import com.traverse.command.domain.member.exception.MemberException;
import com.traverse.command.domain.member.repository.MemberRepository;
import com.traverse.global.oci.bucket.ObjectStorageService;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final ObjectStorageService objectStorageService;

	public MemberInfo modifyMemberInfo(Long memberId, MultipartFile thumbnail, MemberModifyForm form)
		throws IOException, ExecutionException, InterruptedException {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(NOT_EXISTS_MEMBER));

		if (thumbnail != null) {
			String url = objectStorageService.uploadImage(thumbnail).get();
			member.changeInfo(form.email(), form.nickname(), url);
		}else {
			member.changeInfo(form.email(), form.nickname());
		}

		return MemberInfo.builder()
			.id(memberId)
			.nickname(member.getNickname())
			.email(member.getEmail())
			.thumbnail(member.getThumbnail())
			.build();
	}

	public Long resignMember(Long memberId) {
		memberRepository.deleteById(memberId);
		return memberId;
	}

}
