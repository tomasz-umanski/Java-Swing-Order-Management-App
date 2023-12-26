package pl.tomek.ordermanagement.model;

import java.awt.*;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.Vector;

public class item {
    UUID id;
    String name;
    String description;
    String SKUId;
    BigDecimal estimatedNetUnitPrice;
    BigDecimal estimatedGrossUnitPrice;
    BigDecimal dimensionX;
    BigDecimal dimensionY;
    BigDecimal dimensionZ;
    BigDecimal weight;
}
