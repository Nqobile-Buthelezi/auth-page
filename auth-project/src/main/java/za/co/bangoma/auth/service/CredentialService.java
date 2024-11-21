package za.co.bangoma.auth.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.javalin.http.Context;
import za.co.bangoma.auth.model.CredentialType;
import za.co.bangoma.auth.model.UserCredentials;
import za.co.bangoma.auth.service.command.LoginCredentialCommand;
import za.co.bangoma.auth.service.command.SignupCredentialCommand;

public class CredentialService {

    private static final Logger logger = LogManager.getLogger( CredentialService.class );
    private static final CredentialService INSTANCE = new CredentialService();

    private CredentialService() {}

    public static CredentialService getInstance() {
        return INSTANCE;
    }

    /**
     * Extracts the user credentials from the context
     * @param ctx The context containing form parameters
     * @param type The type of credentials to extract (LOGIN or SIGNUP)
     * @return UserCredentials based on the specified type
     */
    public UserCredentials extractCredentials( Context ctx, CredentialType type ) 
    {
        logCredentialExtraction( type );
        return createCredentials( ctx, type );
    }

    /**
     * Creates the appropriate credentials object based on type
     * @param ctx The context containing form parameters
     * @param type The type of credentials to create
     * @return UserCredentials instance
     */
    private UserCredentials createCredentials( Context ctx, CredentialType type ) 
    {
        return type.equals( CredentialType.LOGIN ) 
            ? LoginCredentialCommand.getInstance().execute( ctx ) 
            : SignupCredentialCommand.getInstance().execute( ctx );
    }

    /**
     * Logs the credential extraction attempt
     * @param type The type of credentials being extracted
     */
    private void logCredentialExtraction( CredentialType type ) 
    {
        logger.debug( "Extracting {} user credentials from context", type );
    }

}
