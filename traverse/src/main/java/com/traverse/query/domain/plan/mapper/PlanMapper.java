package com.traverse.query.domain.plan.mapper;

import com.traverse.query.domain.plan.dto.NearPlanInfo;
import com.traverse.query.domain.plan.dto.PlanDetailInfo;
import com.traverse.query.domain.plan.dto.PlanInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlanMapper {

	List<PlanDetailInfo> getMyPlans(@Param("memberId") Long memberId);

	PlanDetailInfo getMyPlanDetail(@Param("planId") Long planId, @Param("memberId") Long memberId);

	NearPlanInfo getMyNearPlan(@Param("memberId") Long memberId);
}
