package andrey.dev.backendforcursach.exceptionHandler;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String status;
}
