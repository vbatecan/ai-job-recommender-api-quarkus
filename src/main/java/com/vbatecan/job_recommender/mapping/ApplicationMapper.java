package com.vbatecan.job_recommender.mapping;

import com.vbatecan.job_recommender.model.dto.ApplicationDTO;
import com.vbatecan.job_recommender.model.entity.Application;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {UserMapper.class, ResumeMapper.class})
public interface ApplicationMapper {
	Application toEntity(ApplicationDTO applicationDTO);

	ApplicationDTO toDto(Application application);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Application partialUpdate(ApplicationDTO applicationDTO, @MappingTarget Application application);
}