package mart.mono.inventory.catalog;

import mart.mono.inventory.lib.Catalog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public List<Catalog> getAll() {
        return catalogRepository.findAll().stream()
            .map(this::toCatalog)
            .toList();
    }

    private Catalog toCatalog(CatalogEntity catalogEntity) {
        return Catalog.builder()
            .id(catalogEntity.getId())
            .displayName(catalogEntity.getDisplayName())
            .build();
    }
}
