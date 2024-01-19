package tamir.ma.tamir.error.auth;

import tamir.ma.tamir.error.ShopException;

public class EmailAlreadyExistsException extends ShopException {
    public EmailAlreadyExistsException(String email) {
        super(String.format("Email %s already exists", email));
    }
}
