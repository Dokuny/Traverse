package com.traverse.command.domain.plan.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.traverse.command.domain.place.entity.Place;
import com.traverse.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
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
		name = "UQ_destination_1",
		columnNames = {
			"plan_id", "place_id", "visited_at"
		})
)
@Entity
public class Destination extends BaseEntity {

	@GeneratedValue(strategy = IDENTITY)
	@Id
	@Column(name = "destination_id")
	private Long id;

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "plan_id", nullable = false)
	private Plan plan;

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;

	@Column(nullable = false)
	private String comment;

	@Column(nullable = false, name = "visited_at")
	private LocalDateTime visitedAt;

	public void change(String comment, LocalDateTime visitedAt) {
		this.comment = comment;
		this.visitedAt = visitedAt;
	}
}
