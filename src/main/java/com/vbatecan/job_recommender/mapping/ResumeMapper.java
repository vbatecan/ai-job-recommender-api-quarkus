package com.vbatecan.job_recommender.mapping;

import com.vbatecan.job_recommender.model.dto.ResumeDTO;
import com.vbatecan.job_recommender.model.entity.Resume;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
public interface ResumeMapper {
	Resume toEntity(ResumeDTO resumeDTO);

	ResumeDTO toDto(Resume resume);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Resume partialUpdate(ResumeDTO resumeDTO, @MappingTarget Resume resume);
}