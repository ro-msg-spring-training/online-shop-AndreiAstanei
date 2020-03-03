package ro.msg.learning.shop.csvConverter;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import ro.msg.learning.shop.DTOs.stockDto.StockDTOOutput;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertToCsv<T> {
    public List<T> fromCsv(Class<T> csvClass, InputStream csvData) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(csvClass);

        MappingIterator<T> it = mapper.readerFor(csvClass).with(schema)
                .readValues(csvData);

        return it.readAll();
    }

    public void toCsv(Class<T> csvClass, List<T> itemsList, OutputStream csvOutput) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(csvClass);
        ObjectWriter objectWriter = mapper.writer(schema);

        Field[] fields = StockDTOOutput.class.getDeclaredFields();
        StringBuilder csvValue = new StringBuilder();

        List<String> fieldNames = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());

        csvValue.append(objectWriter.writeValueAsString(fieldNames));
        for (T item : itemsList) {
            csvValue.append(objectWriter.writeValueAsString(item));
        }

        csvOutput.write(csvValue.toString().getBytes());
    }
}
