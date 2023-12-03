package com.traverse.command.domain.member.entity.enums;

public enum MemberRole {
	MEMBER, ADMIN;

	public static MemberRole fromName(String roleName) {
		return MemberRole.valueOf(roleName.toUpperCase());
	}

}
