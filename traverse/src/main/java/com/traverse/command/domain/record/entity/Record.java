package com.traverse.command.domain.record.entity;


import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.traverse.command.domain.place.entity.Place;
import com.traverse.command.domain.plan.entity.Plan;
import com.traverse.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder
@Entity
public class Record extends BaseEntity {

	@GeneratedValue(strategy = IDENTITY)
	@Id
	@Column(name = "record_id")
	private Long id;

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "plan_id", nullable = false)
	private Plan plan;

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;

	@Lob
	@Column(nullable = false)
	private String content;

	@Default
	@OneToMany(mappedBy = "record", cascade = ALL, orphanRemoval = true)
	List<RecordImage> images = new ArrayList<>();

	@Column
	private Boolean isHighlight;

	public void changeContent(String content) {
		this.content = content;
	}

	public void addImage(RecordImage image) {
		this.images.add(image);
	}

	public void changeHighlight(Boolean isHighlight) {
		this.isHighlight = isHighlight;
	}
}
