package com.suixing.testGanv;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class testLogin {
    @Test
    public static void main(String[] args) {
        String code = String.valueOf(UUID.randomUUID().toString().hashCode()).replaceAll("-","").substring(0,6);

        System.out.println("code:"+code);
    }
}
