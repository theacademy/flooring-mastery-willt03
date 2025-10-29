package com.flooring.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO{ //basic io methods needed for the project

    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        return Integer.parseInt(sc.nextLine());
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int num;
        do {
            print(prompt);
            num = Integer.parseInt(sc.nextLine());
        } while (num < min || num > max);

        return num;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }



}
