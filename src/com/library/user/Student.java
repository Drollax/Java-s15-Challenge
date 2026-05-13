package com.library.user;

import com.library.constants.Type;

public class Student extends MemberRecord {

    private String studentId;

    public Student(Long personId, String name, String address, String phoneNo, String studentId) {
        super(personId, name, address, phoneNo, Type.STUDENT);
        this.studentId = studentId;
    }

    @Override
    public void whoYouAre() {
        System.out.println("I am a Student. ID: " + studentId + ", Name: " + getName());
    }

    public String getStudentId() { return studentId; }
}
