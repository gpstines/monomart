package mart.mono;

import mart.mono.catalog.Catalog;
import mart.mono.product.Product;
import mart.mono.catalog.CatalogService;
import mart.mono.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MonomartApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService mockProductService;

    @MockBean
    private CatalogService mockCatalogService;

    @Test
    void contextLoads() {
    }

    @Test
    void products_list() throws Exception {

        when(this.mockProductService.getForCatalog("electronics")).thenReturn(singletonList(Product.builder().build()));

        mockMvc.perform(get("/api/products?catalog={catalog}", "electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)))
                .andExpect(jsonPath("$[0]", hasKey("name")))
                .andExpect(jsonPath("$[0]", hasKey("catalogId")))
                .andDo(print())
        ;
    }

    @Test
    void catalogs_list() throws Exception {

        when(this.mockCatalogService.getAll()).thenReturn(singletonList(Catalog.builder().build()));

        mockMvc.perform(get("/api/catalogs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$[0]", hasKey("id")))
                .andExpect(jsonPath("$[0]", hasKey("catalogKey")))
                .andExpect(jsonPath("$[0]", hasKey("displayName")))
        ;
    }


    @Test
    void purchases_list() throws Exception {
        mockMvc.perform(get("/api/purchases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }
}
