package com.traverse.command.domain.plan.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.traverse.command.domain.member.entity.Member;
import com.traverse.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder
@Entity
public class Plan extends BaseEntity {

	@GeneratedValue(strategy = IDENTITY)
	@Id
	@Column(name = "plan_id")
	private Long id;

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = false)
	private String title;

	@Default
	@OneToMany(mappedBy = "plan", cascade = ALL, orphanRemoval = true)
	private List<Destination> destinations = new ArrayList<>();


	public void changeTitle(String title) {
		this.title = title;
	}

	public void changeDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}
}
