package com.hcmus.Utils;

import com.hcmus.Const.Const;
import com.hcmus.DAO.ReviewDao;
import com.hcmus.DTO.ReviewDto;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
      public static String formatDate(String dateString) {
         String result = dateString;
         try {
            Date date = parseDate(dateString, "yyyy-MM-dd HH:mm:ss.SSS");
            result = formatDate(date, "dd/MM/yyyy");
         }
         catch (Exception ex) {
            try {
               Date date = parseDate(dateString, "yyyy-MM-dd");
               result = formatDate(date, "dd/MM/yyyy");
            }
            catch (Exception ex2) { }
         }
         return result;
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
      public static String formatTime(String timeInput) {
         String time = "00:00";
         try {
            Time t = parseTime(timeInput);
            time = formatTime(t);
         } catch (Exception ex) {}
         return time;
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
         return CryptoUtil.encryptWithSHA256(pwd);
      }
      public static String FormatPhone(String phonenumber) {
         String phone = phonenumber.replaceAll("[^\\d.]", "");
         return phone.substring(0,3) + "-" + phone.substring(3,6) + "-" + phone.substring(6);
      }
   }
   public static class Review {
      public static float GetReviewStarForShipper(int shipper_id) {
         List<ReviewDto> list = ReviewDao.GetAllShipperReviewForShipper(shipper_id);
         if (list.size() == 0)
            return Const.DEFAULT_RATING_STAR;
         float result = 0;
         for(ReviewDto review : list) {
            result += review.getRating();
         }
         result = result/list.size();
         return result;
      }
   }
}
