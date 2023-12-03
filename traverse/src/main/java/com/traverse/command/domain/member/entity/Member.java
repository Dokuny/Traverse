package com.traverse.command.domain.member.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


import com.traverse.command.domain.member.entity.embedded.OAuth;
import com.traverse.command.domain.member.entity.enums.MemberRole;
import com.traverse.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder
@Table(
	uniqueConstraints = @UniqueConstraint(
		name = "UQ_member_1",
		columnNames = {
			"oauth_domain", "oauth_id"
		})
)
@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Embedded
	private OAuth oAuth;

	@Column(nullable = false, length = 50)
	private String email;

	@Column(nullable = false, length = 50)
	private String nickname;

	@Column(nullable = false)
	private String thumbnail;

	@Column(nullable = false, length = 50)
	@Enumerated(STRING)
	private MemberRole role;

	public void changeInfo(String email, String nickname, String thumbnail) {
		this.email = email;
		this.nickname = nickname;
		this.thumbnail = thumbnail;
	}

	public void changeInfo(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

}
