package xyz.mwszksnmdys.apiclientsdk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Md5Form implements Serializable {
    private String salt;
    private String str;
}
