package com.hcmus.Utils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ConversionUtils {
   public static class DateTime {
      public static Date parseDate(String dateInput) {
         try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            return format.parse(dateInput);
         } catch (Exception ex) { }
         return null;
      }
      public static Date parseDate(String dateInput, String pattern) {
         try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(dateInput);
         } catch (Exception ex) { }
         return null;
      }
      public static String formatDate(Date dateInput) {
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
         return format.format(dateInput);
      }
      public static String formatTime(Date dateInput) {
         SimpleDateFormat format = new SimpleDateFormat("HH:mm");
         return format.format(dateInput);
      }

      public static String formatDate(Date dateInput, String pattern) {
         SimpleDateFormat format = new SimpleDateFormat(pattern);
         return format.format(dateInput);
      }
      public static Time parseTime(String timeInput) {
         try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return new Time(format.parse(timeInput).getTime());
         } catch (Exception ex) { }
         return null;
      }

      public static TimeZone getTimeZone(String timezone) {
         TimeZone oTimezone;
         if (timezone != null && !"".equals(timezone)) {
            oTimezone = TimeZone.getTimeZone(timezone);
         } else {
            oTimezone = TimeZone.getDefault();
         }
         return oTimezone;
      }

      public static TimeZone getCustomerTimeZone() {
         return getTimeZone("");
      }
      public static Date getNowDate() {
         TimeZone timeZone = getCustomerTimeZone();
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
         format.setTimeZone(timeZone);

         Calendar today = GregorianCalendar.getInstance(timeZone);
         String dateTime = format.format(today.getTime());

         try {
            format.setTimeZone(TimeZone.getDefault());
            Date date = format.parse(dateTime);

            return date;
         } catch (Exception ex) {
            return new Date();
         }
      }
      public static String getCurrentFullDateTime() {
         TimeZone timeZone = getCustomerTimeZone();
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
         format.setTimeZone(timeZone);

         Calendar today = GregorianCalendar.getInstance(timeZone);
         return format.format(today.getTime());
      }
   }
   public static class User {
      public static String EncryptPassword(String pwd) {
         return CryptoUtil.encryptWithSHA256(CryptoUtil.encodeWithBase64(pwd));
      }
      public static String FormatPhone(String phonenumber) {
         String phone = phonenumber.replaceAll("[^\\d.]", "");
         return phone.substring(0,3) + "-" + phone.substring(3,6) + "-" + phone.substring(6);
      }
   }
}
