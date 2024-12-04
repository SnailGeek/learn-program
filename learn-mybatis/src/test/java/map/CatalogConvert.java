package map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CatalogConvert {
    CatalogConvert INSTANCE = Mappers.getMapper(CatalogConvert.class);

    EarthworkTaskLayerCatalogMapSaveDTO convert(EarthworkTaskLayerCatalog earthworkTaskLayerCatalog);
}
