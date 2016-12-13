package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.UserDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.service.exception.PersistenceException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

/**
 * Implementation of UserService interface
 * @author Kateřina Caletková
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Inject
    private UserDao userDao;

    @Override
    public User findById(Long id) {
        try {
            return userDao.findById(id);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<User> findByName(String name) {
        try {
            return userDao.findByName(name);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<User> findByPersonalNumber(String personalNumber) {
        try {
            return userDao.findByPersonalNumber(personalNumber);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public User findByMail(String mail) {
        try {
            return userDao.findByMail(mail);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<User> findByPhoneNumber(String phoneNumber) {
        try {
            return userDao.findByPhoneNumber(phoneNumber);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<User> findByReservation(Reservation reservation) {
        try {
            return userDao.findByReservation(reservation);
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return userDao.findAll();
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        try {
            userDao.delete(user);
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    @Override
    public Long createRegisteredUser(User user, String password) {
        try {
            user.setPasswordHash(createHash(password));
            return userDao.create(user);
        } catch (NullPointerException| ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
        
    }

    @Override
    public List<User> findByBirthDate(Date date) {
        try {
            return userDao.findByBirthDate(date);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try {
            userDao.update(user);
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public Boolean isUserAdmin(User user) {
        try {
            return user.getIsAdmin();
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public boolean userAuthenticate(User user, String password) {
        try {
            return validatePassword(password, user.getPasswordHash());
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    private static String createHash(String password) {
        final int sizeOfsalt = 24;
        final int sizeOfhash = 24;
        final int iterations = 1000;
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[sizeOfsalt];
        random.nextBytes(salt);
        byte[] hash = pbkdf2(password.toCharArray(), salt, iterations, sizeOfhash);
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validatePassword(String password, String correctHash) {
        if (password == null) throw new NullPointerException("password cannot be null");
        if (correctHash == null) throw new IllegalArgumentException("password hash is null");
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bigInteger = new BigInteger(1, array);
        String hex = bigInteger.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }

}

   
