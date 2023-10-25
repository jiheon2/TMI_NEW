package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {
    private String shopCode;
    private String productCode;
    private String productName;
    private String productContent;
    private String price;
    private String image;
}
