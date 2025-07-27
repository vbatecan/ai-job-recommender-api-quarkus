package com.vbatecan.job_recommender.mapping;

import com.vbatecan.job_recommender.model.dto.UserDTO;
import com.vbatecan.job_recommender.model.entity.User;
import com.vbatecan.job_recommender.model.input.RegistrationRequest;
import com.vbatecan.job_recommender.model.input.UserUpdateInput;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDTO toDto(User user);
	User toEntity(UserDTO userDTO);
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void partialUpdate(UserUpdateInput updateInput, @MappingTarget User user);
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void partialUpdate(RegistrationRequest registrationRequest, @MappingTarget User user);
}
