package com.czhang.dashboard.dashboard.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

@Entity
@Table(name = "db_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="username")
	private String username;
	
	private String password;
	
	private UserRoleType role;

}
