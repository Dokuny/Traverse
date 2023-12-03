package com.traverse.query.domain.place.mapper;

import com.traverse.query.domain.place.dto.PlaceInfo;
import com.traverse.query.domain.place.dto.PlaceSearchCond;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlaceMapper {

	List<PlaceInfo> searchPlaces(PlaceSearchCond cond);


	Boolean checkDuplicate(
		@Param("name") String name, @Param("address") String address, @Param("type") String type);
}
