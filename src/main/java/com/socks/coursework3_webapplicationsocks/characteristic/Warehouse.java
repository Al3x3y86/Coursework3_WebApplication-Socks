package com.socks.coursework3_webapplicationsocks.characteristic;

import lombok.Data;

@Data
public class Warehouse {

    private int cottonPart;
    private SocksColor socksColor;
    private SocksSize socksSize;
    private int quantity;

    public Warehouse(int cottonPart,
                     SocksColor socksColor,
                     SocksSize socksSize,
                     int quantity) {
        this.cottonPart = cottonPart;
        this.socksColor = socksColor;
        this.socksSize = socksSize;
        this.quantity = quantity;
    }


}
