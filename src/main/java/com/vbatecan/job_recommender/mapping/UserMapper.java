package com.vbatecan.job_recommender.mapping;

import com.vbatecan.job_recommender.model.dto.UserDTO;
import com.vbatecan.job_recommender.model.entity.User;
import com.vbatecan.job_recommender.model.input.RegistrationRequest;
import com.vbatecan.job_recommender.model.input.UserUpdateInput;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {ApplicationMapper.class})
public interface UserMapper {

	UserDTO toDto(User user);

	User toEntity(UserDTO userDTO);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void partialUpdate(UserUpdateInput updateInput, @MappingTarget User user);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void partialUpdate(RegistrationRequest registrationRequest, @MappingTarget User user);
}
