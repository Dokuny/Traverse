package com.traverse.command.domain.plan.dto;


import com.traverse.command.domain.member.entity.Member;
import com.traverse.command.domain.place.entity.Place;
import com.traverse.command.domain.plan.entity.Destination;
import com.traverse.command.domain.plan.entity.Plan;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

public record PlanModifyForm(

	@NotEmpty
	String title,

	List<DestinationRegisterForm> destinations

) {

	public Plan toEntity(Member member) {
		return Plan.builder()
			.member(member)
			.title(title)
			.build();
	}

	public record DestinationRegisterForm(

		Long placeId,
		Long destinationId,
		LocalDateTime visitedAt,
		String comment
	) {

		public Destination toEntity(Place place, Plan plan) {
			return Destination.builder()
				.plan(plan)
				.place(place)
				.comment(comment)
				.visitedAt(visitedAt)
				.build();
		}
	}
}
