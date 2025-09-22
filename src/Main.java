import entidades.*;
import repositorio.InMemoryRepository;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n =-=-=-=-==-=-=-=-==-=-=-=-=TRAZA 1=-=-=-=-==-=-=-=-==-=-=-=-=");
        InMemoryRepository<Empresa> empresaRepo = new InMemoryRepository<>();
        // País
        Pais argentina = Pais.builder().id(1L).nombre("Argentina").build();
        // Provincias
        Provincia buenosAires = Provincia.builder().id(1L).nombre("Buenos Aires").pais(argentina).build();
        Provincia cordoba = Provincia.builder().id(2L).nombre("Córdoba").pais(argentina).build();
        argentina.getProvincias().add(buenosAires);
        argentina.getProvincias().add(cordoba);
        // Localidades
        Localidad caba = Localidad.builder().id(1L).nombre("CABA").provincia(buenosAires).build();
        Localidad laPlata = Localidad.builder().id(2L).nombre("La Plata").provincia(buenosAires).build();
        Localidad cordobaCapital = Localidad.builder().id(3L).nombre("Córdoba Capital").provincia(cordoba).build();
        Localidad villaCarlosPaz = Localidad.builder().id(4L).nombre("Villa Carlos Paz").provincia(cordoba).build();
        // Domicilios
        Domicilio domCaba = Domicilio.builder().id(1L).calle("Av. Rivadavia").numero(4500).cp(1406).localidad(caba).build();
        Domicilio domLaPlata = Domicilio.builder().id(2L).calle("Diagonal 74").numero(120).cp(1900).localidad(laPlata).build();
        Domicilio domCordobaCap = Domicilio.builder().id(3L).calle("Av. Colón").numero(800).cp(5000).localidad(cordobaCapital).build();
        Domicilio domVCP = Domicilio.builder().id(4L).calle("Av. Cárcano").numero(250).cp(5152).localidad(villaCarlosPaz).build();
        // Sucursales
        Sucursal suc1 = Sucursal.builder().id(1L).nombre("Sucursal CABA").horarioApertura(LocalTime.of(9, 0)).horarioCierre(LocalTime.of(18, 0)).esCasaMatriz(true).domicilio(domCaba).build();
        Sucursal suc2 = Sucursal.builder().id(2L).nombre("Sucursal La Plata").horarioApertura(LocalTime.of(9, 0)).horarioCierre(LocalTime.of(18, 0)).esCasaMatriz(false).domicilio(domLaPlata).build();
        Sucursal suc3 = Sucursal.builder().id(3L).nombre("Sucursal Córdoba Capital").horarioApertura(LocalTime.of(8, 30)).horarioCierre(LocalTime.of(17, 30)).esCasaMatriz(true).domicilio(domCordobaCap).build();
        Sucursal suc4 = Sucursal.builder().id(4L).nombre("Sucursal Villa Carlos Paz").horarioApertura(LocalTime.of(8, 30)).horarioCierre(LocalTime.of(17, 30)).esCasaMatriz(false).domicilio(domVCP).build();
        // Empresas
        Empresa empresa1 = Empresa.builder()
                .id(1L).nombre("Empresa 1").razonSocial("Empresa Uno SRL").cuit("20111123456")
                .sucursales(new HashSet<>(Set.of(suc1, suc2)))
                .build();
        Empresa empresa2 = Empresa.builder()
                .id(2L).nombre("Empresa 2").razonSocial("Empresa Dos SA").cuit("27300987654")
                .sucursales(new HashSet<>(Set.of(suc3, suc4)))
                .build();
        empresaRepo.save(empresa1);
        empresaRepo.save(empresa2);
        // Mostrar todas las empresas
        System.out.println("=== TODAS LAS EMPRESAS ===");
        empresaRepo.findAll().forEach(System.out::println);
        // Buscar por ID
        System.out.println("\n=== BUSCAR EMPRESA POR ID 1 ===");
        empresaRepo.findById(1L).ifPresent(System.out::println);
        // Buscar por nombre
        System.out.println("\n=== BUSCAR EMPRESA POR NOMBRE 'Empresa 2' ===");
        empresaRepo.findByField("nombre", "Empresa 2").forEach(System.out::println);
        // Actualizar empresa
        Empresa empresaActualizada = Empresa.builder()
                .id(1L).nombre("Empresa 1 Actualizada").razonSocial("Empresa Uno SRL Actualizada").cuit("20111111111")
                .sucursales(empresa1.getSucursales())
                .build();
        empresaRepo.update(1L, empresaActualizada);
        System.out.println("\n=== EMPRESA 1 DESPUÉS DE ACTUALIZACIÓN ===");
        empresaRepo.findById(1L).ifPresent(System.out::println);
        // Eliminar empresa
        empresaRepo.delete(2L);
        System.out.println("\n=== EMPRESAS DESPUÉS DE ELIMINAR ID 2 ===");
        empresaRepo.findAll().forEach(System.out::println);

        System.out.println("\n =-=-=-=-==-=-=-=-==-=-=-=-=TRAZA 2=-=-=-=-==-=-=-=-==-=-=-=-=");
        // Repositorios
        InMemoryRepository<Categoria> categoriaRepo = new InMemoryRepository<>();
        InMemoryRepository<ArticuloInsumo> insumoRepo = new InMemoryRepository<>();
        InMemoryRepository<ArticuloManufacturado> manufacturadoRepo = new InMemoryRepository<>();
        InMemoryRepository<UnidadMedida> unidadRepo = new InMemoryRepository<>();
        // Categorías
        Categoria pizzas = Categoria.builder().id(1L).denominacion("Pizzas").build();
        Categoria lomos = Categoria.builder().id(2L).denominacion("Lomos").build();
        Categoria sandwiches = Categoria.builder().id(3L).denominacion("Sandwiches").build();
        Categoria insumos = Categoria.builder().id(4L).denominacion("Insumos").build();
        categoriaRepo.save(pizzas);
        categoriaRepo.save(lomos);
        categoriaRepo.save(sandwiches);
        categoriaRepo.save(insumos);
        // Unidades de medida
        UnidadMedida kg = UnidadMedida.builder().id(1L).denominacion("Kg").build();
        UnidadMedida litro = UnidadMedida.builder().id(2L).denominacion("Litro").build();
        UnidadMedida gramos = UnidadMedida.builder().id(3L).denominacion("Gramos").build();
        unidadRepo.save(kg);
        unidadRepo.save(litro);
        unidadRepo.save(gramos);
        // Insumos
        ArticuloInsumo sal = ArticuloInsumo.builder().id(1L).denominacion("Sal").precioCompra(1.0).stockActual(100).stockMaximo(200).esParaElaborar(true).unidadMedida(gramos).build();
        ArticuloInsumo harina = ArticuloInsumo.builder().id(2L).denominacion("Harina").precioCompra(0.5).stockActual(50).stockMaximo(100).esParaElaborar(true).unidadMedida(kg).build();
        ArticuloInsumo aceite = ArticuloInsumo.builder().id(3L).denominacion("Aceite").precioCompra(3.0).stockActual(30).stockMaximo(60).esParaElaborar(true).unidadMedida(litro).build();
        ArticuloInsumo carne = ArticuloInsumo.builder().id(4L).denominacion("Carne").precioCompra(5.0).stockActual(20).stockMaximo(40).esParaElaborar(true).unidadMedida(kg).build();
        insumoRepo.save(sal);
        insumoRepo.save(harina);
        insumoRepo.save(aceite);
        insumoRepo.save(carne);
        // Imágenes
        ImagenArticulo img1 = ImagenArticulo.builder().id(1L).name("HawainaPizza1").url("http://example.com/pizza1").build();
        ImagenArticulo img2 = ImagenArticulo.builder().id(2L).name("HawainaPizza2").url("http://example.com/pizza2").build();
        ImagenArticulo img3 = ImagenArticulo.builder().id(3L).name("HawainaPizza3").url("http://example.com/pizza3").build();
        ImagenArticulo img4 = ImagenArticulo.builder().id(4L).name("LomoCompleto1").url("http://example.com/lomo1").build();
        ImagenArticulo img5 = ImagenArticulo.builder().id(5L).name("LomoCompleto2").url("http://example.com/lomo2").build();
        ImagenArticulo img6 = ImagenArticulo.builder().id(6L).name("LomoCompleto3").url("http://example.com/lomo3").build();
        // Detalles
        ArticuloManufacturadoDetalle detallePizza1 = ArticuloManufacturadoDetalle.builder().id(1L).cantidad(1).articuloInsumo(sal).build();
        ArticuloManufacturadoDetalle detallePizza2 = ArticuloManufacturadoDetalle.builder().id(2L).cantidad(2).articuloInsumo(harina).build();
        ArticuloManufacturadoDetalle detallePizza3 = ArticuloManufacturadoDetalle.builder().id(3L).cantidad(1).articuloInsumo(aceite).build();
        ArticuloManufacturadoDetalle detalleLomo1 = ArticuloManufacturadoDetalle.builder().id(4L).cantidad(1).articuloInsumo(sal).build();
        ArticuloManufacturadoDetalle detalleLomo2 = ArticuloManufacturadoDetalle.builder().id(5L).cantidad(1).articuloInsumo(aceite).build();
        ArticuloManufacturadoDetalle detalleLomo3 = ArticuloManufacturadoDetalle.builder().id(6L).cantidad(1).articuloInsumo(carne).build();
        // Manufacturados
        ArticuloManufacturado pizzaHawaina = ArticuloManufacturado.builder()
                .id(1L).denominacion("Pizza Hawaina").descripcion("Pizza con piña y jamón")
                .tiempoEstimadoMinutos(20).preparacion("Hornear 20 min").unidadMedida(kg)
                .imagenes(new HashSet<>(Set.of(img1, img2, img3)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detallePizza1, detallePizza2, detallePizza3)))
                .build();
        ArticuloManufacturado lomoCompleto = ArticuloManufacturado.builder()
                .id(2L).denominacion("Lomo Completo").descripcion("Lomo completo con todo")
                .tiempoEstimadoMinutos(25).preparacion("Cocinar a la parrilla").unidadMedida(kg)
                .imagenes(new HashSet<>(Set.of(img4, img5, img6)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detalleLomo1, detalleLomo2, detalleLomo3)))
                .build();
        manufacturadoRepo.save(pizzaHawaina);
        manufacturadoRepo.save(lomoCompleto);
        // Listados
        System.out.println("\nCategorías:");
        categoriaRepo.findAll().forEach(System.out::println);

        System.out.println("\nInsumos:");
        insumoRepo.findAll().forEach(System.out::println);

        System.out.println("\nManufacturados:");
        manufacturadoRepo.findAll().forEach(System.out::println);
        // Buscar manufacturado por ID
        System.out.println("\nBuscar manufacturado ID=1:");
        manufacturadoRepo.findById(1L).ifPresent(System.out::println);
        // Actualizar manufacturado
        ArticuloManufacturado pizzaActualizada = ArticuloManufacturado.builder()
                .id(1L).denominacion("Pizza Hawaina Actualizada").descripcion("Pizza con piña, jamón y queso extra")
                .tiempoEstimadoMinutos(22).preparacion("Hornear 22 min").unidadMedida(kg)
                .imagenes(new HashSet<>(Set.of(img1, img2, img3)))
                .articuloManufacturadoDetalles(new HashSet<>(Set.of(detallePizza1, detallePizza2, detallePizza3)))
                .build();
        manufacturadoRepo.update(1L, pizzaActualizada);

        System.out.println("\nDespués de actualización:");
        manufacturadoRepo.findById(1L).ifPresent(System.out::println);
        // Eliminar manufacturado
        manufacturadoRepo.delete(1L);
        System.out.println("\nDespués de eliminar manufacturado ID=1:");
        manufacturadoRepo.findAll().forEach(System.out::println);

    }
}
