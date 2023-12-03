package com.traverse.command.domain.place.service;

import com.traverse.command.domain.place.dto.PlaceRegisterForm;
import com.traverse.command.domain.place.exception.PlaceErrorCode;
import com.traverse.command.domain.place.exception.PlaceException;
import com.traverse.command.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PlaceService {

	private final PlaceRepository placeRepository;

	public Long registerPlace(PlaceRegisterForm form) {
		if (placeRepository.
			existsByNameAndAddressAndPlaceType(form.name(), form.address(), form.placeType())) {
			throw new PlaceException(PlaceErrorCode.ALREADY_EXISTS_PLACE);
		}

		return placeRepository.save(form.toEntity()).getId();
	}

}
