package com.traverse.global.weather.dto;

import java.util.List;

public record PublicDataWeatherResponse(
	Response response

) {


	public record Response(
		Header header,
		Body body
	) {

		public record Header(
			String resultCode,
			String resultMsg
		) {

		}

		public record Body(
			String dataType,

			Items items
		) {

			public record Items(

				List<Item> item

			) {

				public record Item(
					String baseDate,
					String baseTime,
					String category,
					String fcstDate,
					String fcstValue,
					Integer nx,
					Integer ny
				) {

				}
			}
		}

	}
}
