package org.example.birdsservice.service.mapper;

import org.example.birdsservice.domain.Bird;
import org.example.birdsservice.service.dto.BirdDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BirdMapper extends EntityMapper<BirdDTO, Bird> {

}
