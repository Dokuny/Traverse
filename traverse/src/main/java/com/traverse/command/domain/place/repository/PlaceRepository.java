package com.traverse.command.domain.place.repository;

import com.traverse.command.domain.place.entity.Place;
import com.traverse.command.domain.place.entity.enums.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

	boolean existsByNameAndAddressAndPlaceType(String name, String address, PlaceType placeType);

}
