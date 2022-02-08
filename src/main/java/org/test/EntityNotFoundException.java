package org.test;

@SuppressWarnings("serial")
public class EntityNotFoundException extends Exception {
    EntityNotFoundException(String name){ 
        super("Not found: "+ name);
    }
}
