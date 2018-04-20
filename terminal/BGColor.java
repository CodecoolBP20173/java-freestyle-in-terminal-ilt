package com.codecool.termlib;

public enum BGColor {
    BLACK("0;40"),
    RED("0;41"),
    GREEN("0;42"),
    YELLOW("0;43"),
    BLUE("0;44"),
    MAGENTA("0;45"),
    CYAN("0;46"),
    WHITE("0;47");

    private String formatString;

    BGColor(String format) {
        this.formatString = format;
    }
    
    public String colorString() {
        return formatString;
    }
}
