package com.traverse.command.domain.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Chat {

	private Long memberId;
	private String nickname;
	private String thumbnail;
	private String message;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년MM월dd일 HH시mm분ss초")
	private LocalDateTime sendTime;
}
