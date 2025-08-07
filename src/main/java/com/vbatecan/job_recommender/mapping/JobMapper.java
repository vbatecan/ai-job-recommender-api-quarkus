package com.vbatecan.job_recommender.mapping;

import com.vbatecan.job_recommender.model.dto.JobDTO;
import com.vbatecan.job_recommender.model.entity.Job;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {UserMapper.class, ApplicationMapper.class})
public interface JobMapper {


	Job toEntity(JobDTO jobDTO);

	@AfterMapping
	default void linkApplications(@MappingTarget Job job) {
		job.getApplications().forEach(application -> application.setJob(job));
	}

	@AfterMapping
	default void linkJobTags(@MappingTarget Job job) {
		job.getJobTags().forEach(jobTag -> jobTag.setJob(job));
	}

	JobDTO toDto(Job job);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Job partialUpdate(JobDTO jobDTO, @MappingTarget Job job);
}