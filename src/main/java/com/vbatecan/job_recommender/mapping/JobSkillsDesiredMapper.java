package com.vbatecan.job_recommender.mapping;

import com.vbatecan.job_recommender.model.dto.JobSkillsDesiredDTO;
import com.vbatecan.job_recommender.model.entity.JobSkillsDesired;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {UserSkillMapper.class})
public interface JobSkillsDesiredMapper {
	JobSkillsDesired toEntity(JobSkillsDesiredDTO jobSkillsDesiredDTO);

	JobSkillsDesiredDTO toDto(JobSkillsDesired jobSkillsDesired);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	JobSkillsDesired partialUpdate(JobSkillsDesiredDTO jobSkillsDesiredDTO, @MappingTarget JobSkillsDesired jobSkillsDesired);
}