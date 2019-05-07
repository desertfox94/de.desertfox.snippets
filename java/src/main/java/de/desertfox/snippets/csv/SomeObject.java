package de.desertfox.snippets.csv;

public class SomeObject {

	private int id;

	private String name;

	private double height;

	private Short age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "SomeObject [id=" + id + ", name=" + name + ", height=" + height + ", age=" + age + "]";
	}

}
