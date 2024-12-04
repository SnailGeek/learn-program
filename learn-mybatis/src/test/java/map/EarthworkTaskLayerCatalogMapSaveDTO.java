package map;

import lombok.Data;

@Data
public class EarthworkTaskLayerCatalogMapSaveDTO {
    private Long layerId;

    private String name;

    private Long resourceId;

    private Integer type;
}
