package com.bock.forum_hub.domain;

public enum Category {
    BACKEND("backend"),
    FRONTEND("frontend"),
    MOBILE("mobile");

    private final String category;

    Category(String category) {
        this.category = category;
    }

    Category fromString(String desiredCategory) {
        for (Category category : Category.values()) {
            if (category.category.equalsIgnoreCase(desiredCategory)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a categoria: " + desiredCategory);
    }
}
