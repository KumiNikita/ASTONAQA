package Lesson13;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PhoneBook {
    private final Map<String, List<String>> phoneBook;

    public PhoneBook() {
        phoneBook = new HashMap<>();
    }


    public void add(String surname, String phoneNumber) {
        if (!phoneBook.containsKey(surname)) {
            phoneBook.put(surname, new ArrayList<>());
        }
        phoneBook.get(surname).add(phoneNumber);
    }


    public List<String> get(String surname) {
        return phoneBook.getOrDefault(surname, new ArrayList<>());
    }
}

public class Book {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();


        phoneBook.add("Ivanov", "123456");
        phoneBook.add("Ivanov", "789012");
        phoneBook.add("Petrov", "345678");
        phoneBook.add("Sidorov", "901234");
        phoneBook.add("Petrov", "567890");


        System.out.println("Телефоны Иванова: " + phoneBook.get("Ivanov"));
        System.out.println("Телефоны Петрова: " + phoneBook.get("Petrov"));
        System.out.println("Телефоны Сидорова: " + phoneBook.get("Sidorov"));
        System.out.println("Телефоны Смирнова: " + phoneBook.get("Smirnov")); // Неизвестная фамилия
    }
}
