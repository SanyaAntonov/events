package ru.antonov.events.util;

import java.time.Month;

public class MonthConverter {
    public static String toStringInTo(Month month) {
        switch (month) {
            case JANUARY -> {
                return "Января";
            }
            case FEBRUARY -> {
                return "Февраля";
            }
            case MARCH -> {
                return "Марта";
            }
            case APRIL -> {
                return "Апреля";
            }
            case MAY -> {
                return "Мая";
            }
            case JUNE -> {
                return "Июня";
            }
            case JULY -> {
                return "Июля";
            }
            case AUGUST -> {
                return "Августа";
            }
            case SEPTEMBER -> {
                return "Сентября";
            }
            case OCTOBER -> {
                return "Октября";
            }
            case NOVEMBER -> {
                return "Ноября";
            }
            case DECEMBER -> {
                return "Декабря";
            }
        }
        return "";
    }

    public static String toStringInSchedule(Month month) {
        switch (month) {
            case JANUARY -> {
                return "Январь";
            }
            case FEBRUARY -> {
                return "Февраль";
            }
            case MARCH -> {
                return "Март";
            }
            case APRIL -> {
                return "Апрель";
            }
            case MAY -> {
                return "Май";
            }
            case JUNE -> {
                return "Июнь";
            }
            case JULY -> {
                return "Июль";
            }
            case AUGUST -> {
                return "Август";
            }
            case SEPTEMBER -> {
                return "Сентябрь";
            }
            case OCTOBER -> {
                return "Октябрь";
            }
            case NOVEMBER -> {
                return "Ноябрь";
            }
            case DECEMBER -> {
                return "Декабрь";
            }
        }
        return "";
    }
    public static Month toMonth(String month) {
        if (month.equals("Январь") || month.equals("Января"))
            return Month.JANUARY;
        if (month.equals("Февраль") || month.equals("Февраля"))
            return Month.FEBRUARY;
        if (month.equals("Март") || month.equals("Марта"))
            return Month.MARCH;
        if (month.equals("Апрель") || month.equals("Апреля"))
            return Month.APRIL;
        if (month.equals("Май") || month.equals("Мая"))
            return Month.MAY;
        if (month.equals("Июнь") || month.equals("Июня"))
            return Month.JUNE;
        if (month.equals("Июль") || month.equals("Июля"))
            return Month.JULY;
        if (month.equals("Август") || month.equals("Августа"))
            return Month.AUGUST;
        if (month.equals("Сентябрь") || month.equals("Сентября"))
            return Month.SEPTEMBER;
        if (month.equals("Октябрь") || month.equals("Октября"))
            return Month.OCTOBER;
        if (month.equals("Ноябрь") || month.equals("Ноября"))
            return Month.NOVEMBER;
        if (month.equals("Декабрь") || month.equals("Декабря"))
            return Month.DECEMBER;

        return null;
    }
}
