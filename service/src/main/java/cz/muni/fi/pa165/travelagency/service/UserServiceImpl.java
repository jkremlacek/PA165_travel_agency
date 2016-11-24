package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.UserDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
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
        return userDao.findById(id);
    }

    @Override
    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> findByPersonalNumber(Long personalNumber) {
        return userDao.findByPersonalNumber(personalNumber);
    }

    @Override
    public List<User> findByMail(String mail) {
        return userDao.findByMail(mail);
    }

    @Override
    public List<User> findByPhoneNumber(Integer phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<User> findByReservation(Reservation reservation) {
        return userDao.findByReservation(reservation);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }
    
    @Override
    public void createRegisteredUser(User user, String password) {
         user.setPasswordHash(createHash(password));
         userDao.create(user);
        
    }

    @Override
    public List<User> findByBirthDate(Date date) {
        return userDao.findByBirthDate(date);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public Boolean isUserAdmin(User user) {
        return user.getIsAdmin();
    }

    @Override
    public boolean userAuthenticate(User user, String passwordHash) {
        return validatePassword(passwordHash, user.getPasswordHash());
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
        if (password == null) return false;
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

   
