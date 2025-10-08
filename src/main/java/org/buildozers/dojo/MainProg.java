package org.buildozers.dojo;

public class MainProg {

    public static void main(String... args) {
        System.out.println(MainProg.getMessage());
    }

    public static String getMessage() {
        return "Hello from Java : " + System.getProperty("java.version");
    }

}
