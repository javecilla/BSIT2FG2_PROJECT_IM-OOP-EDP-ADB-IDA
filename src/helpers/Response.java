package helpers;

public class Response<T> {
    private boolean success;
    private String message;
    private T data;

    // Constructor for responses with data
    public Response(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Constructor for responses without data
    public Response(boolean success, String message) {
        this(success, message, null);
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    // Static factory methods for common responses
    public static <T> Response<T> success(String message, T data) {
        return new Response<>(true, message, data);
    }

    public static <T> Response<T> success(String message) {
        return new Response<>(true, message);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(false, message);
    }
}
