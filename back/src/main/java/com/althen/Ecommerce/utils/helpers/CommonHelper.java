package com.althen.Ecommerce.utils.helpers;

import java.util.Random;

public class CommonHelper {

    public static String generate_code(){
        Random random = new Random();
        int randomNumber = 0;
        for (int i = 0; i < 6; i++) {
            randomNumber = randomNumber * 10 + random.nextInt(10);
        }
        return String.valueOf(randomNumber);
    }

}
