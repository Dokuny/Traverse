package com.traverse.global.youtube;


import com.traverse.global.oauth.vendor.google.GoogleOAuthProps;
import com.traverse.global.oauth.vendor.google.client.GoogleApiClient;
import com.traverse.global.youtube.dto.YoutubeSearchResponse;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
@Component
public class DailyYoutubeCollector {

	private final GoogleApiClient googleApiClient;
	private final GoogleOAuthProps props;

	@Value("${google.api.key}")
	private String apiKey;

	public List<String> collectDailyYoutubeCodes(String query) {

		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.minusDays(1);

		MultiValueMap<String, String> params = getParams(query, start.toString().split("\\[")[0],
			end.toString().split("\\[")[0]);

		YoutubeSearchResponse response = googleApiClient.searchYoutube(params);

		return response.items().stream()
			.map(item -> item.id().videoId())
			.toList();
	}


	private MultiValueMap<String, String> getParams(
		String query, String startDate, String endDate
	) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add("key", apiKey);
		map.add("part", "snippet");
		map.add("maxResults", "15");
		map.add("order", "viewCount");
		map.add("publishedAfter", startDate);
		map.add("publishedBefore", endDate);
		map.add("q", query);
		return map;
	}
}
