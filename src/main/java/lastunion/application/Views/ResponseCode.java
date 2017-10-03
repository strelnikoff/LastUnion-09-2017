package lastunion.application.Views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("MissortedModifiers")
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

    @SuppressWarnings("unused")
    public boolean getResult() { return result; }

    @SuppressWarnings("unused")
    public String getErrorMessage() { return errorMessage;  }

    @Nullable
    @SuppressWarnings("unused")
    public T getData() { return data;   }
}
