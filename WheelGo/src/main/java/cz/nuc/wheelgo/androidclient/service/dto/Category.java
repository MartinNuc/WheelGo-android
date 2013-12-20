package cz.nuc.wheelgo.androidclient.service.dto;

/**
 * Created with IntelliJ IDEA.
 * User: mist
 * Date: 21.11.13
 * Time: 22:30
 */
public enum Category {
    TECHNICKA_ZAVADA("Technická závada"),
    HAVARIE("Havárie"),
    LESENI("Lešení"),
    STAVEBNI_PRACE("Stavební práce"),
    PREKAZKA("Překážka");

    private String category;

    Category(String category) {
        this.category = category;
    }

    @Override public String toString() {
        return category;
    }
}
