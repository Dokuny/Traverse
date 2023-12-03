package com.traverse.query.domain.dashboard.mapper;

import com.traverse.query.domain.dashboard.dto.YoutubeInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YoutubeMapper {

	List<YoutubeInfo> getDailyYoutube();

}
