package com.example.fi.rinkkasatiainen.model;

public enum Stars {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE;

    public static Stars from(int rating) {
        switch (rating){
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            default:
                return ZERO;
        }
    }
}
