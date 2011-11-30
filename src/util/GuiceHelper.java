package util;

import com.google.inject.Binder;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: inkblot
 * Date: 10/18/11
 * Time: 7:25 AM
 */
public class GuiceHelper {

    public static <T> void bindFromProperty(Binder binder, Class<T> bindingClass, Properties properties) {
        Class<? extends T> implClass = getClassFromProperty(properties, bindingClass, null);
        if (implClass != null) {
            binder.bind(bindingClass).to(implClass);
        }
    }

    public static <T> Class<? extends T> getClassFromProperty(Properties properties, Class<T> interfaceClass, Class<? extends T> defaultImplClass) {
        String implClassName = properties.getProperty(interfaceClass.getSimpleName());
        if (implClassName != null) {
            try {
                Class<?> someClass = Class.forName(implClassName);
                if (interfaceClass.isAssignableFrom(someClass)) {
                    //noinspection unchecked
                    return (Class<? extends T>) someClass;
                }
            } catch (ClassNotFoundException e) {
                // ignore
            }
        }
        return defaultImplClass;
    }

}
