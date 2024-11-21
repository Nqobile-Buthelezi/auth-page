package za.co.bangoma.auth.model;

public interface UserCredentials {

    String getUsername();
    String getPassword();
    boolean hasEmptyFields();

}
