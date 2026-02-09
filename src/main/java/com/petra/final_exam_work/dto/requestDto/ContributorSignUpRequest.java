package com.petra.final_exam_work.dto.requestDto;

import jakarta.validation.constraints.*;

public class ContributorSignUpRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 64, message= "username must be between 5 and 64 characters")
    private String username;

    @NotBlank(message = "password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,64}$",
        message = "Password must be between 8-64 characters and contain at leased 1 lowercase, 1 uppercase and 1 number")
    private String password;

    @NotBlank( message = "You have to confirm your password")
    private String confirmPassword;

    @NotBlank( message = "Email is required")
    @Email( message = "have to be a valid email")
    @Size(max = 128)
    private String email;

    @NotBlank( message = "First name have to be filled in")
    @Size(min = 2, max = 64, message = "First name have to contain anything between 2-64 letters")
    @Pattern(regexp = "^[A-Za-zÅÄÖåäö\\- ]+$",
            message = "First name can only contain letters")
    private String firstName;

    @NotBlank( message = "Last name have to be filled in")
    @Size(min = 2, max = 64, message = "Last name have to contain between 2-64 letters")
    @Pattern(regexp = "^[A-Za-zÅÄÖåäö\\- ]+$",
            message = "Last name can only contain letters ")
    private String lastName;

    @NotNull
    @Min(1) @Max(12)
    private Integer birthMonth;

    @NotNull( message = "You have to add the year and month you where born")
    @Min(4) @Max(4)
    private Integer born;

    public ContributorSignUpRequest() {
    }

    public ContributorSignUpRequest(String username, String password, String confirmPassword, String email,
                                    String firstName, String lastName, Integer birthMonth, Integer born) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthMonth = birthMonth;
        this.born = born;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    public Integer getBorn() {
        return born;
    }

    public void setBorn(Integer born) {
        this.born = born;
    }
}
