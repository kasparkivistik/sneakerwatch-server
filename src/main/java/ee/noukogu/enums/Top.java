package ee.noukogu.enums;


public enum Top {
    HIGH("High"), MID("Mid"), LOW("Low");


    String value;

    Top(String top) {
        this.value = top;
    }

    public String getValue() {
        return value;
    }
}
