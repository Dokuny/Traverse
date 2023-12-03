package com.traverse.command.domain.member.dto;


import jakarta.validation.constraints.NotEmpty;

public record MemberModifyForm(

	@NotEmpty
	String email,

	@NotEmpty
	String nickname

) {
}
