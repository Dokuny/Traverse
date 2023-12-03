package com.traverse.command.domain.record.entity;


import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.traverse.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder
@Entity
public class RecordImage extends BaseEntity {

	@GeneratedValue(strategy = IDENTITY)
	@Id
	@Column(name = "record_image_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "record_id", nullable = false)
	private Record record;

	@Column(nullable = false)
	private String url;

}
