package com.giovanni.bettingapp.mapper;

import com.giovanni.bettingapp.dto.BetDto;
import com.giovanni.bettingapp.model.Bet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MatchMapper.class} )
public interface BetMapper {

    @Mapping(source = "user.username", target = "user")
    @Mapping(source = "match", target = "match")
    BetDto toBetDto(Bet bet);

    List<BetDto> toBetDtoList(List<Bet> bets);
}
