/**
 * 用户实体类
 * 实现Comparable接口用于排序比较
 */
public class User implements Comparable<User> {
	String name;
	int age;

	public User(int age) {
		this.age = age;
	}

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

	@Override
	public int compareTo(User user) {
		return this.getAge() - user.getAge();
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
