### Custom Annotations

In this example, I have created three custom annotations 
1. @JsonSerializableKrishna - To mark that the class is Serializable
2. @JsonElementKrishna - To mark that the element is serializable
3. @InitKrishna - To execute the method without any other explicit call to the method

In addition to these annotations, a custom Exception is used

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface JsonSerializableKrishna {
    }

---

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface JsonElementKrishna {
    public String key() default "abc";
    }

---

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface InitKrishna {
    }


As we can notice, I have replicated the processing of actual @JsonElement and @JsonSerializable

The class __ObjectToJsonConverterUsingAnnotations__ has the logic, which specifies the annotations purpose

The method checkIfSerializable() checks if the class has annotation "JsonSerializableKrishna" and to serialize to json

    private void checkIfSerializable(Object object) {
        if(Objects.isNull(object)) {
            throw new JsonSerializationKrishnaException("The object to serialize is null");
        }
        Class<?> clazz = object.getClass();
        if(!clazz.isAnnotationPresent(JsonSerializableKrishna.class)) {
            throw new JsonSerializationKrishnaException("The class " + clazz.getName() + " is not annotated with JsonSerializableKrishna");
        }
    }


The method initializeObject() helps in defining the use of @InitKrishna
We are setting the value method.setAccessible(true) because the method - capitalizeFirstAndLastNames() is private in our Person class

    private void initializeObject(Object object) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InitKrishna.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

The method getJsonString() is used to convert the object to Json, which specifies the use of @JsonElementKrishna
The method parses the fields which only has @JsonElementKrishna

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

To test the working status of annotations, we are using AnnotationsMain which creates a Person object 
and uses ObjectToJsonConverterUsingAnnotations to parse the object to json

        Person person = new Person("krishna", "surisetty", "23", "Earth");
        System.out.println(person);

        ObjectToJsonConverterUsingAnnotations objectToJsonConverterUsingAnnotations = new ObjectToJsonConverterUsingAnnotations();
        objectToJsonConverterUsingAnnotations.convertToJson(person);


## TODO
### Need to try custom annotation in spring boot 