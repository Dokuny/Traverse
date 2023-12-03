package com.traverse.command.domain.dashboard.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.traverse.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder
@Entity
public class DailyYoutube extends BaseEntity {

	@GeneratedValue(strategy = IDENTITY)
	@Id
	@Column(name = "daily_youtube_id")
	private Long id;

	@Column(nullable = false)
	private String code;

}
