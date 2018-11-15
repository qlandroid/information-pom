package com.information.utils;

import java.util.Calendar;

public class DateUtils {


    /**
     * 通过月份 获得 开始时间 和最后一天的时间
     *
     * @param year
     * @param month 正常月-1
     */
    public static DateTime getStartAndEndTimeByMonth(int year, int month) {
        DateTime d = new DateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        d.startT = calendar.getTimeInMillis();
        if (month == 11) {

            calendar.set(year + 1, 0, 1, 0, 0, 0);
            d.endT = calendar.getTimeInMillis() - 1;
        } else {
            calendar.set(year, month + 1, 1, 0, 0, 0);
            d.endT = calendar.getTimeInMillis() - 1;
        }
        return d;
    }

    /**
     * 获得年份的第一天，和最后一天的时间
     *
     * @param year
     */
    public static DateTime getStartAndEndTimeByYear(int year) {
        DateTime d = new DateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, 0, 1, 0, 0, 0);
        d.startT = calendar.getTimeInMillis();
        calendar.set(year + 1, 0, 1, 0, 0, 0);
        d.endT = calendar.getTimeInMillis() - 1;

        return d;
    }

    public static class DateTime {
        public long startT;
        public long endT;
    }
}
