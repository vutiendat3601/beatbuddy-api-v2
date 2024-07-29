package vn.io.vutiendat3601.beatbuddy.converter;

import jakarta.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

public class StringListConverter implements AttributeConverter<List<String>, String> {
  @Override
  public String convertToDatabaseColumn(List<String> list) {
    String result = "";
    if (list != null) {
      result = String.join(",", list);
    }
    return result;
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    final List<String> result = new ArrayList<>();
    if (dbData != null && !dbData.isBlank()) {
      String[] data = dbData.split(",");
      for (String item : data) {
        result.add(item);
      }
    }
    return result;
  }
}
