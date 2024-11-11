package com.example.demo;

import entity.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class DemoController {

    public ArrayList<Contact> contactList = new ArrayList<>();

    public DemoController() {
        contactList.add(new Contact("Janna", "janna@gmail.com", "94499371"));
        contactList.add(new Contact("Nora", "nora@gmail.com", "94499371"));
        contactList.add(new Contact("Fatma", "fatma@gmail.com", "94499371"));
    }


    @PostMapping("/contacts")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        if (contact.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        } else if (contactList.stream().anyMatch(c -> c.getEmail().equals(contact.getEmail()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            contactList.add(contact);
            return ResponseEntity.status(HttpStatus.OK).body(contact);
        }
    }

    @GetMapping("/contact")
    public String getContactDetail(@RequestParam String contactName) {
        for (Contact contact : contactList) {
            if (contact.getName().equals(contactName)) {
                return contact.toString();
            }
        }
        return "Contact not found";
    }

    // to test use:
    // http://localhost:8080/contact?contactName=Nora
}
