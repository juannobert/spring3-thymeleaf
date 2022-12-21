package com.juannobert.store.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.juannobert.store.dtos.requests.UserRequest;
import com.juannobert.store.mappers.UserMapper;
import com.juannobert.store.models.User;
import com.juannobert.store.models.enums.UserType;
import com.juannobert.store.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	public User insert(UserRequest request) {
		User model = mapper.toModel(request);
		model.setUserType(UserType.ADMIN);
		
		String password = request.getPassword();
		model.setPassword(passwordEncoder.encode(password));
		
		model = repository.save(model);
		
		return model;
		
		
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByEmail(username);
		if(user.isEmpty()) {
			System.out.println("User email not found: " + username);
			repository.findAll().forEach(System.out::println);
			throw new UsernameNotFoundException("Not found");
		}
		return user.get();
	}
	
	
}
