package com.traverse.query.domain.place.service;

import com.traverse.query.domain.place.dto.PlaceInfo;
import com.traverse.query.domain.place.dto.PlaceSearchCond;
import com.traverse.query.domain.place.mapper.PlaceMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlaceQueryService {

	private final PlaceMapper placeMapper;

	public List<PlaceInfo> searchPlaces(PlaceSearchCond cond) {
		return placeMapper.searchPlaces(cond);
	}

	public Boolean checkDuplicate(String name, String address, String type) {
		return placeMapper.checkDuplicate(name, address, type);
	}
}
