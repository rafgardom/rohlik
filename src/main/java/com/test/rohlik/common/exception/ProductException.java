package com.test.rohlik.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductException extends RuntimeException {

    private List<String> errorMessageList;

    public static String prettifyErrors(List<String> errors) {
        StringBuilder result = new StringBuilder();
        for (String s : errors) {
            result.append(s).append("\n");
        }
        return result.toString();
    }
}
