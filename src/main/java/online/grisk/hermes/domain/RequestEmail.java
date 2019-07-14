package online.grisk.hermes.domain;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RequestEmail implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 1, max = 2147483647)
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Correo electrónico no válido")
    private String address;

    @NotBlank
    @Size(min = 1, max = 2147483647)
    private String step;

    @NotBlank
    @Size(min = 1, max = 2147483647)    private String token;

    public RequestEmail(@NotBlank @Size(min = 1, max = 2147483647) @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Correo electrónico no válido") String address, @NotBlank @Size(min = 1, max = 2147483647) String step, @NotBlank @Size(min = 1, max = 2147483647) String token) {
        this.address = address;
        this.step = step;
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public String getStep() {
        return step;
    }

    public String getToken() {
        return token;
    }
}
