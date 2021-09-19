import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectToJsonConverterUsingAnnotations {

    public String convertToJson(Object object) throws InvocationTargetException, IllegalAccessException {
        checkIfSerializable(object);
        initializeObject(object);
        return getJsonString(object);
    }

    private String getJsonString(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(JsonElementKrishna.class)) {
                field.setAccessible(true);
                jsonMap.put(getKeyOfField(field), (String) field.get(object));
            }
        }
        String jsonString = jsonMap.entrySet().stream()
                .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
                .collect(Collectors.joining(","));
        System.out.println("{" + jsonString + "}");
        return "{" + jsonString + "}";
    }

    private String getKeyOfField(Field field) {
        String fieldAnnotatedKey = field.getAnnotation(JsonElementKrishna.class).key();
        String fieldAnnotatedKeyDefaultValue = "abc";
        return fieldAnnotatedKey.equals(fieldAnnotatedKeyDefaultValue) ? field.getName() : fieldAnnotatedKey;
    }

    private void initializeObject(Object object) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InitKrishna.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private void checkIfSerializable(Object object) {
        if(Objects.isNull(object)) {
            throw new JsonSerializationKrishnaException("The object to serialize is null");
        }
        Class<?> clazz = object.getClass();
        if(!clazz.isAnnotationPresent(JsonSerializableKrishna.class)) {
            throw new JsonSerializationKrishnaException("The class " + clazz.getName() + " is not annotated with JsonSerializableKrishna");
        }
    }

}
