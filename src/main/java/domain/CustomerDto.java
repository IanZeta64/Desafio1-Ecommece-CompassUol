package domain;

public class CustomerDto {
    private final String name;
    private final String document;

    public CustomerDto(String name, String document) {
        this.name = name;
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

}
