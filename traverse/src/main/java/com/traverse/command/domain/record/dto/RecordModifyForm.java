package com.traverse.command.domain.record.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RecordModifyForm(

	@NotEmpty
	String content,
	Boolean highlight,
	@Size(min = 1, max = 10, message = "사진은 최소 한장부터 최대 열장까지 저장할 수 있습니다.")
	List<Long> images

) {
}
