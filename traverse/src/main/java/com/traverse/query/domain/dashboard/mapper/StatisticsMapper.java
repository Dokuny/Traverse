package com.traverse.query.domain.dashboard.mapper;

import com.traverse.query.domain.dashboard.dto.MyTouringAreasStatisticsInfo;
import com.traverse.query.domain.dashboard.dto.MyTouringTypesStatisticsInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StatisticsMapper {


	List<MyTouringTypesStatisticsInfo> getMyTouringTypesStatistics(@Param("memberId") Long memberId);
	MyTouringAreasStatisticsInfo getMyTouringAreasStatistics(@Param("memberId") Long memberId);
}
