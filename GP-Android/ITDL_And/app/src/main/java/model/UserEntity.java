package model;

import org.json.JSONException;
import org.json.JSONObject;


public class UserEntity {
	long UserId;
	String UserName;
	String UserEmail;
	String UserTwitterAccount;
	String Gender;
	String UserPassword;
	String City ;
	String DateOfBirth;
	int age;
	
	
	public UserEntity(String userName, String userEmail,
			String userTwitterAccount, String gender, String userPassword,
			String city, String dateOfBirth) {
	
		UserName = userName;
		UserEmail = userEmail;
		UserTwitterAccount = userTwitterAccount;
		Gender = gender;
		UserPassword = userPassword;
		City = city;
		DateOfBirth = dateOfBirth;
	}

	public String getUserIDString()
	{
		return String.valueOf(UserId);
	}
	public UserEntity(String name, String email, String pass) {
		UserName =name;
		UserEmail =email;
		UserPassword =pass;
		
	}
	
	public UserEntity(String name, long id) {
		UserName =name;
		UserId = id;
		
	}
	public String getUserName() {
		return UserName;
	}
	public String getUserEmail() {
		return UserEmail;
	}
	public String getUserTwitterAccount() {
		return UserTwitterAccount;
	}
	public String getGender() {
		return Gender;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	public String getCity() {
		return City;
	}
	public String getDateOfBirth() {
		return DateOfBirth;
	}
	public int getAge() {
		return age;
	}
	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}
	
	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static UserEntity createLoginUser(String json) {

		
			JSONObject object;
			try {
				object = new JSONObject(json);
				 return new UserEntity( object.get("username").toString() ,
						 Long.parseLong(object.get("userId").toString()));
				//return (UserEntity) object.get("Result");
				
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return null;

	}
	
}
