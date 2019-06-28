package com.sagi.smartshopping.utilities;

import java.util.ArrayList;

public class MockDataHandler {

    public static void addCategories(ArrayList<String> allCategoriesList) {
        allCategoriesList.add("דומה לפוסטים אחרונים שראית");
        allCategoriesList.add("אוכל");
        allCategoriesList.add("אופנה");
        allCategoriesList.add("טכנולוגיה");
        allCategoriesList.add("ספורט");
        allCategoriesList.add("פנאי");
        allCategoriesList.add("אירועים");
        allCategoriesList.add("כללי");
    }

    public static void addSuggestions(ArrayList<String> allSuggestionsList) {
        allSuggestionsList.add("מומלץ עבורך");
        allSuggestionsList.add("פוסטים חמים");
        allSuggestionsList.add("המלצת העורך");
        allSuggestionsList.add("גישה");
        allSuggestionsList.add("בסביבה");
        allSuggestionsList.add("קטגוריות");
    }
}
