package com.example.calculatorapi21;

public enum ButtonText {
    NUL("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    CLEAR("C"),
    BACK(""),
    PLUS("+"),
    MINUS("-"),
    MULT("*"),
    DIV("/"),
    EQUAL("=");
    private String value;
    ButtonText(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
