package com.library.user;

import com.library.constants.Type;

public class Faculty extends MemberRecord{
    private String facultyId;

    public Faculty(Long personId, String name, String address, String phoneNo, String facultyId) {
        super(personId, name, address, phoneNo, Type.FACULTY);
        this.facultyId = facultyId;
    }

    public String getFacultyId() {return facultyId;}
}
