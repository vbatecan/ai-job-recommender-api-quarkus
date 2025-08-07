package com.vbatecan.job_recommender.mapping;

import com.vbatecan.job_recommender.model.dto.JobBenefitDTO;
import com.vbatecan.job_recommender.model.entity.JobBenefit;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface JobBenefitMapper {
	JobBenefit toEntity(JobBenefitDTO jobBenefitDTO);

	JobBenefitDTO toDto(JobBenefit jobBenefit);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	JobBenefit partialUpdate(JobBenefitDTO jobBenefitDTO, @MappingTarget JobBenefit jobBenefit);
}