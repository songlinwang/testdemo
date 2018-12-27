package example.demo.meta;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author wsl
 * @date 2018/11/23
 */
@Data
public class User implements Serializable {

    private int id;

    private String name;

    private int age;

    private String region;
}
