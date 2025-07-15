package com.example.model;

public class User {
    private String student_num;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private byte[] profilePic;

    public User(String student_num, String name, String surname, String email, String phone, String password, byte[] profilePic) {
        this.student_num = student_num;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profilePic = profilePic;
    }

    //old constructor for registration
    public User(String student_num, String name, String surname, String email, String phone, String password) {
        this(student_num, name, surname, email, phone, password, null); // default to null for new users
    }

    // Getters
    public String getStudent_num() { return student_num; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
    public byte[] getProfilePic() { return profilePic; }
}
