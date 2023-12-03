package com.traverse.query.domain.plan.service;

import com.traverse.query.domain.plan.dto.NearPlanInfo;
import com.traverse.query.domain.plan.dto.PlanDetailInfo;
import com.traverse.query.domain.plan.dto.PlanInfo;
import com.traverse.query.domain.plan.mapper.PlanMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlanQueryService {

	private final PlanMapper planMapper;

	public List<PlanDetailInfo> getMyPlans(Long memberId) {
		return planMapper.getMyPlans(memberId);
	}

	public PlanDetailInfo getMyPlan(Long planId, Long memberId) {
		return planMapper.getMyPlanDetail(planId, memberId);
	}

	public NearPlanInfo getMyNearPlan(Long memberId) {
		return planMapper.getMyNearPlan(memberId);
	}
}
