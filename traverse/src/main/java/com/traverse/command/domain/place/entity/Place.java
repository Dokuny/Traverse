package com.traverse.command.domain.place.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.traverse.command.domain.place.entity.enums.PlaceType;
import com.traverse.global.common.BaseEntity;
import com.traverse.global.common.PlaceTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(uniqueConstraints = @UniqueConstraint(
	name = "UQ_place_1",
	columnNames = {"place_type", "name", "address"})
)
@Entity
public class Place extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "place_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(length = 1000)
	private String introduce;

	@Column
	private String address;

	@Column(nullable = false)
	private Double lat;

	@Column(nullable = false)
	private Double lng;

	@Column
	private String thumbnail;

	@Convert(converter = PlaceTypeConverter.class)
	@Column(name = "place_type", nullable = false, length = 50)
	private PlaceType placeType;

}
