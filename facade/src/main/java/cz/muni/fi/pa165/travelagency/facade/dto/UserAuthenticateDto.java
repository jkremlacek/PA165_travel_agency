package cz.muni.fi.pa165.travelagency.facade.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Katerina Caletkova
 */
public class UserAuthenticateDto {
    
    @Email
    @Column(length = 50, nullable = true)
    private String mail;

    @NotNull
    private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
