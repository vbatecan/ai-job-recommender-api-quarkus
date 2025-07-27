package com.vbatecan.job_recommender.mapping;

import com.vbatecan.job_recommender.model.dto.UserSkillDTO;
import com.vbatecan.job_recommender.model.entity.UserSkill;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
public interface UserSkillMapper {
	UserSkill toEntity(UserSkillDTO userSkillDTO);

	UserSkillDTO toDto(UserSkill userSkill);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	UserSkill partialUpdate(UserSkillDTO userSkillDTO, @MappingTarget UserSkill userSkill);
}