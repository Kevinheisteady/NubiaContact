package com.kevin.test.nubia;

/**
 * Created by Kevin-He on 2016/8/26.
 */
public class Person {
    private String name;
    private String phoneNum;
    private String stuNum;
    private int id;
    public Person() {
    }

    public Person(String name, String phoneNum, String stuNum) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.stuNum = stuNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)){
            return false;
        }
        return this.name.equals(((Person) obj).name) && this.phoneNum.equals(((Person) obj).phoneNum)
                && this.stuNum.equals(((Person) obj).stuNum);
        //return super.equals(obj);

    }
}
