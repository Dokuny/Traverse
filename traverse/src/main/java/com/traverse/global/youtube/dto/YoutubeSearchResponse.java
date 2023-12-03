package com.traverse.global.youtube.dto;


import java.time.LocalDateTime;
import java.util.List;


public record YoutubeSearchResponse(
	String kind,
	String etag,
	String nextPageToken,
	String regionCode,
	PageInfo pageInfo,
	List<Item> items
) {

	public record PageInfo(
		Long totalResults,
		Long resultPerPage
	){ }

	public record Item(
		String kind,
		String etag,
		Id id,
		Snippet snippet,
		String channelTitle,
		String liveBroadcastContent,
		LocalDateTime publishTime

		) {

		public record Id(
			String kind,
			String videoId
		){ }

		public record Snippet (
			LocalDateTime publishedAt,
			String channelId,
			String title,
			String description
		) { }

	}

}
