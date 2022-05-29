package q.sdm.constant;

import java.util.regex.Pattern;

public class Constants {
    public static final String DB_NAME = "mvvm.db";
    public static final String PREF_NAME = "mvvm.prefs";

    public static final String VALUE_BEARER_TOKEN_DEFAULT="NULL";

    //Local Action manager
    public static final String ACTION_EXPIRED_TOKEN ="ACTION_EXPIRED_TOKEN";

    public static final int CASH=1;
    public static final int CARD=2;
    public static final int DONE=1;
    public static final int CANCEL=2;
    public static final String PROVINCE_KIND_PROVINCE = "PROVINCE_KIND_PROVINCE";
    public static final String PROVINCE_KIND_DISTRICT = "PROVINCE_KIND_DISTRICT";
    public static final String PROVINCE_KIND_COMMUNE = "PROVINCE_KIND_COMMUNE";

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private Constants(){

    }
}
