package com.gymdaus.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_role", uniqueConstraints = @UniqueConstraint(
		columnNames = {"role", "username"}))
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserRole {
	
	@Id
	@SequenceGenerator(name = "userRoleGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userRoleGenerator")
	@Column(name = "user_role_id", unique = true, nullable = false)
	private Integer userRoleId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "username", nullable = false)
	private User user;
	
	@Column(name = "role", nullable = false, length = 45)
	private String role;
	

}
