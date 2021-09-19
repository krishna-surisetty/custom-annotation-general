@JsonSerializableKrishna
public class Person {
    @JsonElementKrishna
    private String firstName;
    @JsonElementKrishna
    private String lastName;
    @JsonElementKrishna(key = "personAge")
    private String age;
    private String address;

    public Person(String firstName, String lastName, String age, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
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

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                '}';

    }

    @InitKrishna
    private void capitalizeFirstAndLastNames() {
        System.out.println("Init method is called");
        this.firstName = this.firstName.substring(0,1).toUpperCase() + this.firstName.substring(1);
        this.lastName = this.lastName.substring(0,1).toUpperCase() + this.lastName.substring(1);
    }

}
