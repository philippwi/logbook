package beans;

import db.models.UserEntity;
import db.operation.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class SomeBean {

	private String username;
	private String password;

	//getter

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}


	//setter

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getMsg() {
		return "hi";
	}

	public void createUser(){
		UserEntity user = new UserEntity(username, password, (byte) 0);
		UserManager.start().addUser(user);
	}

}
