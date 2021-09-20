package com.giovanni.bettingapp.mapper;

import com.giovanni.bettingapp.dto.MatchDto;
import com.giovanni.bettingapp.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(source = "teamHome.name", target = "teamHome")
    @Mapping(source = "teamAway.name", target = "teamAway")
    MatchDto toMatchDto(Match match);

    List<MatchDto> toMatchDtoList(List<Match> matches);
}
