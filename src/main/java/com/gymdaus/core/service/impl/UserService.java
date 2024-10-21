package com.gymdaus.core.service.impl;


import com.gymdaus.core.entity.UserRole;
import com.gymdaus.core.mapper.MapperUser;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.repository.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("userService")
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MapperUser mapperUser;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.gymdaus.core.entity.User user = userRepository.findByUsername(username);
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserRole());
		return buildUser(user, authorities);
	}

	public boolean comparePassword(String newPass, String oldPass) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(newPass, oldPass);
	}

	public String encodePassword(String newPass) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(newPass);
	}

	public UserModel findModelByUsername(String username) throws NoResultException {
		com.gymdaus.core.entity.User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new NoResultException();
		}
		return mapperUser.entity2Model(user);
	}

	public void updatePass(UserModel usuario) throws PersistenceException {
		usuario.setModificationDate(new Date());
		try {
			userRepository.save(mapperUser.model2Entity(usuario));
		} catch (Exception exception) {
			throw new PersistenceException();
		}
	}

	public com.gymdaus.core.entity.User getLoggedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return findByUsername(user.getUsername());
	}

	public com.gymdaus.core.entity.User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	private User buildUser(com.gymdaus.core.entity.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
				true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildAuthorities (Set<UserRole> userRoles) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
		for (UserRole userRole: userRoles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		return new ArrayList<>(grantedAuthorities);
	}
}
