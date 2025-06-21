package coding.gabs.todolist.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class NullAttributesValidator {
    public static String[] getNullAttributes(Object bean) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(bean);

        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();

        Set<String> emptyAttributes = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object propertyValue = beanWrapper.getPropertyValue(pd.getName());

            if (propertyValue == null) {
                emptyAttributes.add(pd.getName());
            }
        }

        String[] result = new String[emptyAttributes.size()];
        return emptyAttributes.toArray(result);
    }

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(
                source,
                target,
                getNullAttributes(source));
    }
}
