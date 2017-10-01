package lastunion.application.Views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
//import org.jetbrains.annotations.Nullable;

public final class ResponseCode<T> {
    private final boolean result;
    private final String errorMessage;
    private final T data;

    @JsonCreator
    public ResponseCode(@JsonProperty("result") boolean result,
                        @JsonProperty("errorMsg") String errorMessage,
                        @JsonProperty("data") T data) {
        this.result = result;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    @JsonCreator
    public ResponseCode(@JsonProperty("result") boolean result,
                        @JsonProperty("errorMsg") String errorMsg) {
        this.result = result;
        this.errorMessage = errorMsg;
        this.data = null;
    }

    public boolean getResult() { return result; }

    public String getErrorMessage() { return errorMessage;  }

    //@Nullable
    public T getData() { return data;   }
}
