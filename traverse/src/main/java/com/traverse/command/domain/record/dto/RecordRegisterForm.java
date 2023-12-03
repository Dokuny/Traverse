package com.traverse.command.domain.record.dto;


import com.traverse.command.domain.place.entity.Place;
import com.traverse.command.domain.plan.entity.Plan;
import com.traverse.command.domain.record.entity.Record;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RecordRegisterForm(

        Long placeId,
        @NotNull
        Long planId,
        @NotEmpty
        String content,
        @NotNull
        Boolean isHighlight

) {

    public Record toEntity(Plan plan, Place place) {
        return Record.builder()
                .place(place)
                .plan(plan)
                .content(content)
                .isHighlight(isHighlight)
                .build();
    }
}
