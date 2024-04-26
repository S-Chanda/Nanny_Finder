package com.project.nannyfinder;

import com.project.nannyfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NannyfinderApplication implements CommandLineRunner{

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(NannyfinderApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
        System.out.println("starting code");

//        User user = new User();
//
//        user.setUsername("admin");
//        //encoding password with BCryptPasswordEncoder
//        user.setPassword(this.passwordEncoder.encode("admin"));
//        user.setEmail("chandashah1228@gmail.com");
//        user.setAddress("thimi");
//        user.setPhone("9800000000");
//        user.setProfile("default.png");
//        Set<UserRole> roles = new HashSet<>();
//
//
//        //role info
//        Role role1 = new Role();
//        role1.setRoleId(44L);
//        role1.setRoleName("ADMIN");
//
//        //create role set to set user in userRole
//        Set<UserRole> userRoleSet = new HashSet<>();
//        UserRole userRole = new UserRole();
//        userRole.setRole(role1);
//        userRole.setUser(user);
//
//        userRoleSet.add(userRole);
//        User user1 = this.userService.createUser(user, userRoleSet);
//        System.out.println(user1.getUsername());



	}

//	@Autowired
//	private EmailService emailService;
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void sendMail(){
//		emailService.sendEmail("chandashah1228@gmail.com", "OTP verification for NannyFinder","Body of email" );
//	}
}
