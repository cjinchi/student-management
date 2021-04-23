package com.chenjinchi.studentmanagement.batch;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator {

    private static final String[] genders = { "女", "男" };

    public static String getGender() {
        return genders[ThreadLocalRandom.current().nextInt(genders.length)];
    }

    public static LocalDate getDate() {
        long startEpochDate = LocalDate.of(1990, 1, 1).toEpochDay();
        long endEpochDate = LocalDate.of(2000, 12, 31).toEpochDay();
        long randomEpochDate = ThreadLocalRandom.current().nextLong(startEpochDate, endEpochDate);
        return LocalDate.ofEpochDay(randomEpochDate);
    }

}
