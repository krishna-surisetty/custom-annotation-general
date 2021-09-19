import java.lang.reflect.InvocationTargetException;

public class AnnotationsMain {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        Person person = new Person("krishna", "surisetty", "23", "Earth");
        System.out.println(person);

        ObjectToJsonConverterUsingAnnotations objectToJsonConverterUsingAnnotations = new ObjectToJsonConverterUsingAnnotations();
        objectToJsonConverterUsingAnnotations.convertToJson(person);

    }
}
