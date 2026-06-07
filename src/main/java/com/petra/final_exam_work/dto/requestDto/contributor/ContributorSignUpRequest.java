package com.petra.final_exam_work.dto.requestDto.contributor;

import com.petra.final_exam_work.customValidate.PasswordMatches;
import jakarta.validation.constraints.*;

@PasswordMatches
public class ContributorSignUpRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 64, message = "username must be between 5 and 64 characters")
    private String username;

    @NotBlank(message = "password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,64}$",
        message = "Password must be between 8-64 characters and contain at leased 1 lowercase, 1 uppercase and 1 number")
    private String password;

    @NotBlank( message = "You have to confirm your password")
    private String confirmPassword;

    @NotBlank( message = "Email is required")
    @Email( message = "have to be a valid email")
    @Size(max = 64)
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

    public ContributorSignUpRequest() {
    }

    public ContributorSignUpRequest(String username, String password, String confirmPassword, String email,
                                    String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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

}
