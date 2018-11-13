package com.url;

public class ClassDemo {
	 public static void main(String[] args){
//		 User user = new User("1221", 30);
//		 Class<? extends User> class1 = user.getClass();
//		 String name = class1.getName();
		Class<?> forName;
		try {
			forName = Class.forName("com.url.User");
			try {
				User newInstance = (User) forName.newInstance();
				newInstance.setAge(30);
				System.out.println(newInstance);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}

class User{
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public User(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
	
	
}
