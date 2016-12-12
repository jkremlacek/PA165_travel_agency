package cz.muni.fi.pa165.travelagency.facade.dto;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Katerina Caletkova
 */
public class UserAuthenticateDto {
    
    //@NotNull    
    private String mail;

    //@NotNull
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
