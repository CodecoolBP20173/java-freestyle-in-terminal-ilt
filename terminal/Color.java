package com.codecool.termlib;

public enum Color {
    BLACK("0;30"),
    RED("0;31"),
    GREEN("0;32"),
    YELLOW("0;33"),
    BLUE("0;34"),
    MAGENTA("0;35"),
    CYAN("0;36"),
    WHITE("0;37");

    private String formatString;

    Color(String format){
        this.formatString = format;
    }

    public String colorString() {
        return formatString;
    }
}
