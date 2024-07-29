package vn.io.vutiendat3601.beatbuddy.converter;

import jakarta.persistence.AttributeConverter;
import java.util.HashSet;
import java.util.Set;

public class StringSetConverter implements AttributeConverter<Set<String>, String> {

  @Override
  public String convertToDatabaseColumn(Set<String> set) {
    String result = "";
    if (set != null) {
      result = String.join(",", set);
    }
    return result;
  }

  @Override
  public Set<String> convertToEntityAttribute(String dbData) {
    final Set<String> result = new HashSet<>();
    if (dbData != null && !dbData.isBlank()) {
      String[] data = dbData.split(",");
      for (String item : data) {
        result.add(item);
      }
    }
    return result;
  }
}
