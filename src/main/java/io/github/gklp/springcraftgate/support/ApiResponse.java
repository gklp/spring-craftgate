package io.github.gklp.springcraftgate.support;

import io.craftgate.response.common.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private ErrorResponse errors;

    private String status;

    private T data;
}
