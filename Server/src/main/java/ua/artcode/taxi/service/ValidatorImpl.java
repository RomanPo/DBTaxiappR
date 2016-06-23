package ua.artcode.taxi.service;

import ua.artcode.taxi.dao.AppDB;
import ua.artcode.taxi.exception.InputDataWrongException;
import ua.artcode.taxi.exception.RegisterException;
import ua.artcode.taxi.model.Address;
import ua.artcode.taxi.model.ClientAccessToken;
import ua.artcode.taxi.model.User;
import ua.artcode.taxi.model.UserIdentifier;

import java.util.Collection;
import java.util.logging.Logger;

public class ValidatorImpl implements Validator {

    private static final Logger log = Logger.getLogger(String.valueOf(ValidatorImpl.class));
    private AppDB appDB;
    private ClientAccessToken accessToken;

    public ValidatorImpl(AppDB appDB) {
        this.appDB = appDB;
    }

    public ValidatorImpl() {
    }

    @Override
    public boolean validateLogin(String phone, String password) {
       log.info("Validate Login method is in use");
        boolean result = false;

        Collection<User> users = appDB.getUsers().keySet();

        for (User user : users) {
            if (user.getPhone().equals(phone) && user.getPass().equals(password)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean validateRegistration(String phone) throws RegisterException {
        log.info("Validate registration method is in use");
        Collection<User> users = appDB.getUsers().keySet();

        for (User user : users) {
            if (user.getPhone().equals(phone)) {
                throw new RegisterException("This phone using already");
            }
        }

        return true;
    }

    @Override
    public boolean validateAddress(Address address) throws InputDataWrongException {
        log.info("Validate address method is in use");
        if (address.getCountry().equals("")) {
            throw new InputDataWrongException("Wrong data: country");
            //result = false;
        } else if (address.getCity().equals("")) {
            throw new InputDataWrongException("Wrong data: city");
            //result = false;
        } else if (address.getStreet().equals("")) {
            throw new InputDataWrongException("Wrong data: street");
            //result = false;
        } else if (address.getHouseNum().equals("")) {
            throw new InputDataWrongException("Wrong data: houseNum");
            //result = false;
        }

        return true;
    }

    @Override
    public boolean validateChangeRegistration(UserIdentifier identifier, int id, String phone)
                                                                        throws RegisterException {
        log.info("Validate Change registration method is in use");
        Collection<User> users = appDB.getUsers().keySet();

        for (User user : users) {
            if (user.getPhone().equals(phone) && user.getId() != id &&
                        user.getIdentifier().equals(identifier)) {
                throw new RegisterException("This phone using already");
            }
        }

        return true;
    }
}
