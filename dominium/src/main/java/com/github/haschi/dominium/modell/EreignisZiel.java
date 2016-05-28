package com.github.haschi.dominium.modell;

public interface EreignisZiel<T> {

    void falls(Domänenereignis<T> ereignis);
}
