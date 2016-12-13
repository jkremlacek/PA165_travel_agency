package cz.muni.fi.pa165.travelagency.facade.dto;

import java.util.Date;
import java.util.Objects;

/**
 * DTO for create user entity
 * @author Katerina Caletkova
 */
public class UserCreateDto {
       
    private String name;
    
    private String passwordHash;
    
    private Boolean isAdmin;
    
    private Date birthDate;
    
    private String personalNumber;
    
    private String mail;
    
    private String phoneNumber;
    

    @Override
    public int hashCode() {
        int hash = 7;        
        hash = 47 * hash + Objects.hashCode(this.getPersonalNumber());       
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserCreateDto)) {
            return false;
        }
        final UserCreateDto other = (UserCreateDto) obj;        
        
        if (!Objects.equals(this.getPersonalNumber(), other.getPersonalNumber())) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

        
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
