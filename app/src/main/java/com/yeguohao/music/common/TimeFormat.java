package com.yeguohao.music.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeFormat {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
    public String timeStamp2Date(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return dateFormat.format(calendar.getTime());
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSS");
    public long date2TimeStamp(String date) {
        try {
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
