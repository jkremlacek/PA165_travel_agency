package cz.muni.fi.pa165.travelagency.facade.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Katerina Caletkova
 */
public class UserAuthenticateDto {
    
    @NotNull    
    private Long id;

    @NotNull
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    
      

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
