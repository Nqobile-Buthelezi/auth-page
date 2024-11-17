package za.co.bangoma.auth;

import za.co.bangoma.auth.config.AppConfig;

public class App {

    public static void main( String[] args ) {
        AppConfig.createApp().start( 7070 );
    }

}
