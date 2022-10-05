package com.zerobase.domain.util;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class Aes256utilTest {



    @Test
    void encrypt() {

        String encrypt = Aes256util.encrypt("Hello world");
        assertEquals(Aes256util.decrypt(encrypt), "Hello world");
    }

    @Test
    void decrypt() {
    }
}