package com.khalilayache.starcode.utils;

import android.content.Context;

import com.khalilayache.starcode.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author USUARIO
 * @since 25/01/2017.
 */

public class StringUtils {

    public static String upperFirstLetter(String word){


        if(word.contains("n/a"))
            return word;

        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    public static String formatDateFromApi(String dataString) {
        DateFormat formatterToDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = formatterToDate.parse(dataString);
            DateFormat formatterToString = new SimpleDateFormat("dd/MM/yyyy");
            return formatterToString.format(data);
        } catch (ParseException e) {
            e.printStackTrace();

        }

        return dataString;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String formatTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    public static String internacionalizeBirth(String birth_year, Context context){

        if(birth_year.equals("unknown")){
            return upperFirstLetter(context.getString(R.string.info_unknown));
        }
        return birth_year;

    }

    public static String internacionalizeEyeColor(String eye_color, Context context){

        switch (eye_color) {
            case "black":
                return upperFirstLetter(context.getString(R.string.eye_color_black));
            case "blue":
                return upperFirstLetter(context.getString(R.string.eye_color_blue));
            case "blue-gray":
                return upperFirstLetter(context.getString(R.string.eye_color_blue_gray));
            case "brown":
                return upperFirstLetter(context.getString(R.string.eye_color_brown));
            case "dark":
                return upperFirstLetter(context.getString(R.string.eye_color_dark));
            case "gold":
                return upperFirstLetter(context.getString(R.string.eye_color_gold));
            case "green, yellow":
                return upperFirstLetter(context.getString(R.string.eye_color_green_yellow));
            case "orange":
                return upperFirstLetter(context.getString(R.string.eye_color_orange));
            case "pink":
                return upperFirstLetter(context.getString(R.string.eye_color_pink));
            case "red":
                return upperFirstLetter(context.getString(R.string.eye_color_red));
            case "red, blue":
                return upperFirstLetter(context.getString(R.string.eye_color_red_blue));
            case "unknown":
                return upperFirstLetter(context.getString(R.string.info_unknown));
            case "white":
                return upperFirstLetter(context.getString(R.string.eye_color_white));
            case "yellow":
                return upperFirstLetter(context.getString(R.string.eye_color_yellow));
        }

        return eye_color;
    }

    public static String internacionalizeGender(String gender, Context context){

        switch (gender) {
            case "female":
                return upperFirstLetter(context.getString(R.string.gender_female));
            case "hermaphrodite":
                return upperFirstLetter(context.getString(R.string.gender_hermaphrodite));
            case "male":
                return upperFirstLetter(context.getString(R.string.gender_male));
            case "n/a":
                return upperFirstLetter(context.getString(R.string.gender_na));
            case "none":
                return upperFirstLetter(context.getString(R.string.gender_none));
        }

        return gender;
    }

    public static String internacionalizeHairColor(String hair_color, Context context){

        switch (hair_color) {
            case "auburn":
                return upperFirstLetter(context.getString(R.string.hair_color_auburn));
            case "auburn, grey":
                return upperFirstLetter(context.getString(R.string.hair_color_auburn_grey));
            case "auburn, white":
                return upperFirstLetter(context.getString(R.string.hair_color_auburn_white));
            case "black":
                return upperFirstLetter(context.getString(R.string.hair_color_black));
            case "blond":
                return upperFirstLetter(context.getString(R.string.hair_color_blond));
            case "blonde":
                return upperFirstLetter(context.getString(R.string.hair_color_blonde));
            case "brown":
                return upperFirstLetter(context.getString(R.string.hair_color_brown));
            case "brown, grey":
                return upperFirstLetter(context.getString(R.string.hair_color_brown_grey));
            case "grey":
                return upperFirstLetter(context.getString(R.string.hair_color_grey));
            case "n/a":
                return upperFirstLetter(context.getString(R.string.hair_color_na));
            case "none":
                return upperFirstLetter(context.getString(R.string.hair_color_none));
            case "white":
                return upperFirstLetter(context.getString(R.string.hair_color_white));
        }

        return hair_color;
    }

    public static String internacionalizeSkinColor(String skin_color, Context context){

        switch (skin_color) {
            case "blue":
                return upperFirstLetter(context.getString(R.string.skin_color_blue));
            case "blue, grey":
                return upperFirstLetter(context.getString(R.string.skin_color_blue_grey));
            case "brown":
                return upperFirstLetter(context.getString(R.string.skin_color_brown));
            case "brown mottle":
                return upperFirstLetter(context.getString(R.string.skin_color_brown_mottle));
            case "brown, white":
                return upperFirstLetter(context.getString(R.string.skin_color_brown_white));
            case "dark":
                return upperFirstLetter(context.getString(R.string.skin_color_dark));
            case "fair":
                return upperFirstLetter(context.getString(R.string.skin_color_fair));
            case "fair, green, yellow":
                return upperFirstLetter(context.getString(R.string.skin_color_fair_green_yellow));
            case "gold":
                return upperFirstLetter(context.getString(R.string.skin_color_gold));
            case "green":
                return upperFirstLetter(context.getString(R.string.skin_color_green));
            case "green, gray":
                return upperFirstLetter(context.getString(R.string.skin_color_green_gray));
            case "green-tan, brown":
                return upperFirstLetter(context.getString(R.string.skin_color_green_tan_brown));
            case "grey":
                return upperFirstLetter(context.getString(R.string.hair_color_white));
            case "grey, blue":
                return upperFirstLetter(context.getString(R.string.skin_color_grey_blue));
            case "grey, green, yellow":
                return upperFirstLetter(context.getString(R.string.skin_color_grey_green_yellow));
            case "grey, red":
                return upperFirstLetter(context.getString(R.string.skin_color_grey_red));
            case "light":
                return upperFirstLetter(context.getString(R.string.skin_color_light));
            case "metal":
                return upperFirstLetter(context.getString(R.string.skin_color_metal));
            case "mottled green":
                return upperFirstLetter(context.getString(R.string.skin_color_mottled_green));
            case "none":
                return upperFirstLetter(context.getString(R.string.skin_color_none));
            case "orange":
                return upperFirstLetter(context.getString(R.string.skin_color_orange));
            case "pale":
                return upperFirstLetter(context.getString(R.string.skin_color_pale));
            case "red":
                return upperFirstLetter(context.getString(R.string.skin_color_red));
            case "red, blue, white":
                return upperFirstLetter(context.getString(R.string.skin_color_red_blue_white));
            case "silver, red":
                return upperFirstLetter(context.getString(R.string.skin_color_silver_red));
            case "tan":
                return upperFirstLetter(context.getString(R.string.skin_color_tan));
            case "white":
                return upperFirstLetter(context.getString(R.string.skin_color_white));
            case "white, blue":
                return upperFirstLetter(context.getString(R.string.skin_color_white_blue));
            case "white, red":
                return upperFirstLetter(context.getString(R.string.skin_color_white_red));
            case "yellow":
                return upperFirstLetter(context.getString(R.string.skin_color_yellow));

        }

        return skin_color;
    }

    public static String internacionalizeSpecies(String species, Context context){

        switch (species) {
            case "Droid":
                return upperFirstLetter(context.getString(R.string.species_droid));
            case "Human":
                return upperFirstLetter(context.getString(R.string.gender_human));
            case "Yoda's species":
                return upperFirstLetter(context.getString(R.string.gender_yoda));
        }
        return species;
    }


}
