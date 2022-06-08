package q.sdm.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    private TimeUtils(){}

    public static Date UTCToITC(Date date){
        if (date == null){
            return null;
        }
        return new Date(date.getTime() + TimeUnit.HOURS.toMillis(7));

    }

    public static Date ITCToUTC(Date date){
        if (date == null){
            return null;
        }
        return new Date(date.getTime() - TimeUnit.HOURS.toMillis(7));
    }
}
