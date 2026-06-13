    import entidades.*;
    import repository.CategoriaRepository;
    import repository.ProductoRepository;

    public static void main(String[] args) {

        CategoriaRepository categoriaRepo = new CategoriaRepository();
        ProductoRepository productoRepo = new ProductoRepository();

        int opcion;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - ABM Categorias");
            System.out.println("2 - ABM Productos");
            System.out.println("3 - Reportes");
            System.out.println("0 - Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();
            switch(opcion) {
                case 1:
                    // ir a menu categorias
                    menuCategorias(sc, categoriaRepo);
                    break;

                case 2:
                    // ir a menu productos
                    menuProductos(sc, productoRepo, categoriaRepo);
                    break;

                case 3:
                    // ir a reportes
                    System.out.println("Menu Reportes");
                    menuReportes(sc, productoRepo, categoriaRepo);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida");
            }


        } while (opcion != 0);

    }

    //MENU CATEGORIAS
    public static void menuCategorias(Scanner sc, CategoriaRepository categoriaRepo) {
        int opcion;
        do {
            System.out.println("Menu Categorias");
            System.out.println("1 - Alta");
            System.out.println("2 - Baja");
            System.out.println("3 - Modificación");
            System.out.println("4 - Listado");
            System.out.println("0 - Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion) {
                case 1:
                    // ir a menu categorias
                    String nombre;
                    String descripcion;

                    do {
                        System.out.print("Nombre: ");
                        nombre = sc.nextLine();
                        if (nombre.isEmpty()) {
                            System.out.println("El nombre no puede estar vacio");
                        }
                    } while (nombre.isEmpty());

                    System.out.print("Descripcion: ");
                    descripcion = sc.nextLine();

                    Categoria categoria = Categoria.builder()
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .createdAt(LocalDateTime.now())
                            .build();
                    categoria = categoriaRepo.guardar(categoria);

                    System.out.println("Categoria creada con ID: " + categoria.getId());
                    break;

                case 2:
                    // baja
                    System.out.print("Ingrese el ID de la categoria: ");
                    Long bajaId = sc.nextLong();
                    sc.nextLine();
                    boolean eliminado = categoriaRepo.eliminarLogico(bajaId);

                    if(!eliminado) {
                        System.out.println("No se encontro una categoria con ese ID");
                    } else {
                        System.out.println("Categoria con ID: "+bajaId + " eliminada correctamente");
                    }

                    break;

                case 3:
                    // modificar categoria
                     List<Categoria> categorias = mostrarCategorias(categoriaRepo);

                    Optional<Categoria> encontrada;
                    do {
                        System.out.println("Ingrese ID de la categoria a modificar: ");
                        Long Id = sc.nextLong();
                        sc.nextLine();
                        encontrada = categoriaRepo.buscarPorId(Id);
                        if(encontrada.isEmpty()) {
                            System.out.println("Ingrese una categoria valida");
                        }
                    } while (encontrada.isEmpty());

                    Categoria cat = encontrada.get();
                    System.out.println("Nombre actual: "+cat.getNombre());
                    System.out.println("Descripcion actual: "+cat.getDescripcion());

                    System.out.print("Nuevo nombre (enter para mantener): ");
                    String nuevoNombre = sc.nextLine();
                    System.out.print("Nueva descripcion (enter para mantener): ");
                    String nuevaDescripcion = sc.nextLine();

                    if (!nuevoNombre.isEmpty()) {
                        cat.setNombre(nuevoNombre);
                    }

                    if (!nuevaDescripcion.isEmpty()) {
                        cat.setDescripcion(nuevaDescripcion);
                    }
                    categoriaRepo.guardar(cat);
                    System.out.println("Categoria actualizada correctamente");
                    break;

                case 4:
                    // listar categorias
                    mostrarCategorias(categoriaRepo);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }


    //MENU PRODUCTOS
    public static void menuProductos(Scanner sc, ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        int opcion;
        do {
            System.out.println("Menu Productos");
            System.out.println("1 - Alta");
            System.out.println("2 - Baja");
            System.out.println("3 - Modificación");
            System.out.println("4 - Listado");
            System.out.println("0 - Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion) {
                case 1:
                    // alta
                    List<Categoria> categorias = mostrarCategorias(categoriaRepo);
                    if (categorias.isEmpty()) {
                        break;
                    }

                    Optional<Categoria> categoriaOpt;

                    do {
                        System.out.println("Seleccione la categoria por su ID: ");
                        Long categoriaId = sc.nextLong();
                        sc.nextLine();

                        categoriaOpt = categoriaRepo.buscarPorId(categoriaId);
                        if(categoriaOpt.isEmpty()) {
                            System.out.println("Categoria invalida");
                        }
                    }while (categoriaOpt.isEmpty());

                    Categoria categoria = categoriaOpt.get();

                    String nombre;
                    String descripcion;
                    double precio;
                    int stock;

                    //validar nombre
                    do {
                        System.out.print("Nombre: ");
                        nombre = sc.nextLine();
                        if (nombre.isEmpty()) {
                            System.out.println("El nombre no puede estar vacio");
                        }
                    } while (nombre.isEmpty());

                    System.out.print("Descripcion: ");
                    descripcion = sc.nextLine();

                    //validar precio
                    do {
                        System.out.print("Precio: ");
                        precio = sc.nextDouble();

                        if (precio <= 0) {
                            System.out.println("El precio debe ser mayor a 0");
                        }

                    } while (precio <= 0);

                    //validar stock
                    do {
                        System.out.print("Stock: ");
                        stock = sc.nextInt();

                        if (stock < 0) {
                            System.out.println("El stock no puede ser negativo");
                        }

                    } while (stock < 0);

                    sc.nextLine();

                    Producto producto = Producto.builder()
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .precio(precio)
                            .stock(stock)
                            .disponible(true)
                            .categoria(categoria)
                            .createdAt(LocalDateTime.now())
                            .build();
                    producto = productoRepo.guardar(producto);

                    System.out.println("Producto creado con ID: " + producto.getId());
                    break;

                case 2:
                    // baja
                    System.out.print("Ingrese el ID del producto: ");
                    Long bajaId = sc.nextLong();
                    sc.nextLine();
                    boolean eliminado = productoRepo.eliminarLogico(bajaId);

                    if(!eliminado) {
                        System.out.println("No se encontro un producto con ese ID");
                    } else {
                        System.out.println("Producto con ID: "+bajaId + " eliminado correctamente");
                    }

                    break;

                case 3:
                    // modificar producto
                    List<Producto> listaProductos = mostrarProductos(productoRepo);

                    if (listaProductos.isEmpty()) {
                        break;
                    }

                    Optional<Producto> productoOpt;

                    do {
                        System.out.print("Ingrese ID del producto a modificar: ");
                        Long id = sc.nextLong();
                        sc.nextLine();

                        productoOpt = productoRepo.buscarPorId(id);

                        if (productoOpt.isEmpty()) {
                            System.out.println("Producto no encontrado");
                        }

                    } while (productoOpt.isEmpty());

                    Producto productoMod = productoOpt.get();

                    System.out.println("Nombre actual: " + productoMod.getNombre());
                    System.out.println("Precio actual: " + productoMod.getPrecio());
                    System.out.println("Stock actual: " + productoMod.getStock());

                    System.out.print("Nuevo nombre (enter para mantener): ");
                    String nuevoNombre = sc.nextLine();
                    System.out.print("Nuevo precio (0 para mantener): ");
                    double nuevoPrecio = sc.nextDouble();
                    System.out.print("Nuevo stock (-1 para mantener): ");
                    int nuevoStock = sc.nextInt();
                    sc.nextLine();

                    if (!nuevoNombre.isEmpty()) {
                        productoMod.setNombre(nuevoNombre);
                    }

                    if (nuevoPrecio > 0) {
                        productoMod.setPrecio(nuevoPrecio);
                    }

                    if (nuevoStock >= 0) {
                        productoMod.setStock(nuevoStock);
                    }

                    productoRepo.guardar(productoMod);
                    System.out.println("Producto actualizado correctmente");

                    break;

                case 4:
                    // listar productos
                    mostrarProductos(productoRepo);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion != 0);

    }

    //MENU REPORTES
    public static void menuReportes(Scanner sc, ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
            List<Categoria> categorias = mostrarCategorias(categoriaRepo);
            Optional<Categoria> categoriaOpt;
            Long categoriaId;
            if (!categorias.isEmpty()) {
                do {
                    System.out.println("Ingrese ID de categoria para filtrar productos: ");
                    categoriaId = sc.nextLong();
                    sc.nextLine();

                    categoriaOpt = categoriaRepo.buscarPorId(categoriaId);

                    if(categoriaOpt.isEmpty()) {
                        System.out.println("Categoria invalida");
                    }
                } while (categoriaOpt.isEmpty());

                List<Producto> productos = productoRepo.buscarPorCategoria(categoriaId);

                if (productos.isEmpty()) {
                    System.out.println("No hay productos en esta categoria");
                } else {
                    System.out.println("\n--- PRODUCTOS DE LA CATEGORIA ---");

                    for (Producto p : productos) {
                        System.out.println(
                                "ID: " + p.getId() +
                                        " | Nombre: " + p.getNombre() +
                                        " | Precio: $" + p.getPrecio() +
                                        " | Stock: " + p.getStock()
                        );
                    }
                }
            }
    }

    // METODOS REUTILIZABLES para mostrar productos y categorias
    public static List<Categoria> mostrarCategorias(CategoriaRepository categoriaRepo) {
        List<Categoria> listaCategorias = categoriaRepo.listarActivos();
        if (listaCategorias.isEmpty()) {
            System.out.println("No hay categorias disponibles");
        } else {
            System.out.println("\n--- LISTADO DE CATEGORIAS ---");
            for (Categoria c : listaCategorias) {
                System.out.println(c.getId() + " - " + c.getNombre() + " - " + c.getDescripcion());
            }
        }
        return listaCategorias;
    }

    public static List<Producto> mostrarProductos(ProductoRepository productoRepo) {
        List<Producto> listaProductos = productoRepo.listarActivos();
        if (listaProductos.isEmpty()) {
            System.out.println("No hay productos disponibles");
        } else {
            System.out.println("\n--- LISTADO DE PRODUCTOS ---");
            for (Producto p : listaProductos) {
                System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() +
                        " | $" + p.getPrecio() + " | Stock: " + p.getStock());
            }
        }
        return listaProductos;
    }
