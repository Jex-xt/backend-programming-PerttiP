package backend.chapter2.domain;

public class Friend {
    private String firstName, lastName;

public Friend() {
}

public Friend(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
}

public String getFirstName() {
    return firstName;
}

public void setFirstName(String firstName) {
    this.firstName = firstName;
}

public String getLastName() {
    return lastName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}



}
