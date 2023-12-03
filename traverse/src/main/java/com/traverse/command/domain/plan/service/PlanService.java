package com.traverse.command.domain.plan.service;

import com.traverse.command.domain.member.entity.Member;
import com.traverse.command.domain.plan.dto.PlanModifyForm;
import com.traverse.command.domain.plan.dto.PlanModifyForm.DestinationRegisterForm;
import com.traverse.command.domain.plan.exception.PlanErrorCode;
import com.traverse.command.domain.plan.exception.PlanException;
import com.traverse.command.domain.plan.repository.PlanRepository;
import com.traverse.command.domain.member.repository.MemberRepository;
import com.traverse.command.domain.place.entity.Place;
import com.traverse.command.domain.place.repository.PlaceRepository;
import com.traverse.command.domain.plan.dto.PlanRegisterForm;
import com.traverse.command.domain.plan.entity.Destination;
import com.traverse.command.domain.plan.entity.Plan;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlanService {

	private final PlanRepository planRepository;
	private final PlaceRepository placeRepository;
	private final MemberRepository memberRepository;

	public Long registerPlan(Long memberId, PlanRegisterForm form) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new PlanException(PlanErrorCode.NOT_EXISTS_MEMBER));

		Plan newPlan = form.toEntity(member);

		// 장소가 실존하는지 확인
		form.destinations()
			.forEach(destinationRegisterForm -> {
				Place place = placeRepository.findById(destinationRegisterForm.placeId())
					.orElseThrow(() -> new PlanException(PlanErrorCode.NOT_EXISTS_PLACE));

				newPlan.getDestinations().add(destinationRegisterForm.toEntity(place, newPlan));
			});

		return planRepository.save(newPlan).getId();
	}

	public Long deletePlan(Long memberId, Long planId) {
		Plan plan = planRepository.findById(planId)
			.orElseThrow(() -> new PlanException(PlanErrorCode.NOT_EXISTS_PLACE));

		if (!plan.getMember().getId().equals(memberId)) {
			throw new PlanException(PlanErrorCode.NO_PERMISSION);
		}

		planRepository.delete(plan);

		return planId;
	}

	public Long modifyPlan(Long memberId, Long planId, PlanModifyForm form) {
		Plan plan = planRepository.findByIdWithDestinations(planId)
			.orElseThrow(() -> new PlanException(PlanErrorCode.NOT_EXISTS_PLACE));

		if (!plan.getMember().getId().equals(memberId)) {
			throw new PlanException(PlanErrorCode.NO_PERMISSION);
		}

		plan.changeTitle(form.title());

		List<DestinationRegisterForm> modifyList = form.destinations();

		List<Destination> destinations = plan.getDestinations();

		int idx = 0;
		while (destinations.size() != idx) {

			boolean isDelete = true;
			Destination destination = destinations.get(idx);

			for (DestinationRegisterForm destinationForm : modifyList) {
				if (destination.getId().equals(destinationForm.destinationId())) {
					destination.change(destinationForm.comment(), destinationForm.visitedAt());
					isDelete = false;
					break;
				}
			}

			if (isDelete) {
				destinations.remove(idx);
			} else {
				idx++;
			}
		}

		modifyList.stream()
			.filter(destinationRegisterForm -> destinationRegisterForm.destinationId() == null)
			.forEach(destinationRegisterForm -> {
				Place place = placeRepository.findById(destinationRegisterForm.placeId())
					.orElseThrow(() -> new PlanException(PlanErrorCode.NOT_EXISTS_PLACE));

				destinations.add(destinationRegisterForm.toEntity(place, plan));
			});

		return planId;
	}
}
