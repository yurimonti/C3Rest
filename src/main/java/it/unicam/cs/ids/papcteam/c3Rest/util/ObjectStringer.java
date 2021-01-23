package it.unicam.cs.ids.papcteam.c3Rest.util;

import java.util.List;
import java.util.stream.Collectors;

public interface ObjectStringer<T> {
    String objectToString(T object);
    default String objectToString(List<T> objects){
        StringBuilder s = new StringBuilder();
        s.append("[ ");
        List<String> list = objects.stream().map(this::objectToString).collect(Collectors.toList());
        for (String stringa:list) {
            s.append(stringa).append("\n");
        }
        s.append(" ]");
        return s.toString();
    }
}