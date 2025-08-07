package com.vbatecan.job_recommender.mapping;

import com.vbatecan.job_recommender.model.dto.JobRequirementDTO;
import com.vbatecan.job_recommender.model.entity.JobRequirement;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface JobRequirementMapper {
	JobRequirement toEntity(JobRequirementDTO jobRequirementDTO);

	JobRequirementDTO toDto(JobRequirement jobRequirement);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	JobRequirement partialUpdate(JobRequirementDTO jobRequirementDTO, @MappingTarget JobRequirement jobRequirement);
}