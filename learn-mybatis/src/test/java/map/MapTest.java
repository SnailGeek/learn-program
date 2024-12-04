package map;

import org.junit.jupiter.api.Test;

public class MapTest {
    @Test
    public void test() {
        EarthworkTaskLayerCatalog earthworkTaskLayerCatalog = new EarthworkTaskLayerCatalog();
        earthworkTaskLayerCatalog.setId(1L);
        earthworkTaskLayerCatalog.setName("test");
        earthworkTaskLayerCatalog.setOrder(1);
        earthworkTaskLayerCatalog.setParentId(1L);
        earthworkTaskLayerCatalog.setProjectId(1L);
        earthworkTaskLayerCatalog.setTaskId(1L);

        EarthworkTaskLayerCatalogMapSaveDTO convert = CatalogConvert.INSTANCE.convert(earthworkTaskLayerCatalog);


    }
}
