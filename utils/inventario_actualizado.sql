-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 06-05-2025 a las 04:35:15
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30
CREATE DATABASE inventario;
USE inventario;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `inventario`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `ActualizarZapatilla` (IN `p_color` VARCHAR(100), IN `p_costo` DECIMAL(10.3), IN `p_talla` DECIMAL(10.3), IN `p_modelo_id` INT(11), IN `p_sku` VARCHAR(50))   BEGIN
  UPDATE zapato SET color = p_color, costo = p_costo, talla = p_talla, modelo_id = p_modelo_id WHERE sku = p_sku;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarZapatilla` (IN `p_sku` VARCHAR(50), IN `p_color` VARCHAR(100), IN `p_costo` DECIMAL(10.2), IN `p_talla` DECIMAL(3.1), IN `p_modelo_id` INT(11))   BEGIN
  INSERT INTO zapato(color, costo, sku, talla, modelo_id)
  VALUES(p_color, p_costo, p_sku, p_talla, p_modelo_id);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ListarInventario` ()   BEGIN   
	SELECT 
        z.sku,
        m.nombre AS modelo,
        z.talla AS talla,
        z.color AS color,
        ma.nombre AS marca,
        i.cantidad_actual AS stock,
        CONCAT(u.pasillo, '-', u.estante, '-', u.contenedor) AS almacen
    FROM inventario i
    JOIN zapato z ON i.zapato_id = z.zapato_id
    JOIN modelo m ON z.modelo_id = m.modelo_id
    JOIN marca ma ON m.marca_id = ma.marca_id
    JOIN ubicacion_almacen u ON i.ubicacion_id = u.ubicacion_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ListarZapatillas` ()   BEGIN
	 SELECT z.zapato_id, z.color, z.talla, z.costo, z.sku, z.precio, m.modelo_id, m.nombre as modelo, m.genero
    , mr.marca_id, mr.nombre as marca, ca.categoria_id, ca.nombre as categoria, ca.descripcion
    FROM zapato z JOIN modelo m ON z.modelo_id = m.modelo_id JOIN marca mr ON m.marca_id = mr.marca_id
    JOIN categoria ca ON ca.categoria_id = m.categoria_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ActualizarUbicacion` (IN `p_id` INT, IN `p_contenedor` VARCHAR(50), IN `p_estante` VARCHAR(50), IN `p_pasillo` VARCHAR(50))   BEGIN

    UPDATE ubicacion_almacen

    SET contenedor = p_contenedor,

        estante = p_estante,

        pasillo = p_pasillo

    WHERE ubicacion_id = p_id;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizar_categoria` (IN `cod` INT, IN `nom` VARCHAR(40), IN `des` TEXT)   BEGIN 

	update categoria
    set 
    nombre= nom,
    descripcion = des
    where 
    categoria_id=cod;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizar_marca` (IN `p_id` INT, IN `p_nombre` VARCHAR(100))   BEGIN
     UPDATE marca SET nombre = p_nombre WHERE marca_id = p_id;
 END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizar_modelo` (IN `p_nombre` VARCHAR(100), IN `p_genero` VARCHAR(50), IN `p_categoria_id` INT, IN `p_marca_id` INT, IN `p_modelo_id` INT)   BEGIN
    UPDATE modelo
    SET nombre = p_nombre,
        genero = p_genero,
        categoria_id = p_categoria_id,
        marca_id = p_marca_id
    WHERE modelo_id = p_modelo_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarUbicacionPorId` (IN `p_id` INT)   BEGIN

    SELECT * FROM ubicacion_almacen

    WHERE ubicacion_id = p_id;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscar_personal` (IN `valor` CHAR(8))   BEGIN
    SELECT p.dni, p.ap_paterno, p.ap_materno, p.nombre, p.genero, p.fecha_nacimiento, p.sueldo, c.codigo_cargo, c.cargo 
    FROM tb_personal p 
    JOIN tb_cargo c ON c.codigo_cargo = p.codigo_cargo 
    WHERE p.dni = valor 
    ORDER BY p.ap_paterno ASC;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscar_proveedor_por_ID` (IN `xProveedorId` INT)   BEGIN
	SELECT * FROM proveedor WHERE proveedor_id = xProveedorId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_crearUbicacion` (IN `p_contenedor` VARCHAR(50), IN `p_estante` VARCHAR(50), IN `p_pasillo` VARCHAR(50))   BEGIN

    -- Validar duplicados antes de insertar

    IF NOT EXISTS (

        SELECT 1 FROM ubicacion_almacen

        WHERE contenedor = p_contenedor AND estante = p_estante AND pasillo = p_pasillo

    ) THEN

        INSERT INTO ubicacion_almacen (contenedor, estante, pasillo)

        VALUES (p_contenedor, p_estante, p_pasillo);

    END IF;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_crear_categoria` (IN `nom` VARCHAR(255), IN `des` TEXT)   BEGIN 

	insert into categoria
    (nombre,descripcion)
    values
    (nom,des);

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EditarInventario` (IN `p_inventario_id` INT, IN `p_cantidad_actual` INT, IN `p_ubicacion_id` INT)   BEGIN
    UPDATE inventario
    SET cantidad_actual = p_cantidad_actual,
        ubicacion_id = p_ubicacion_id
    WHERE inventario_id = p_inventario_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_editar_personal` (IN `nd` CHAR(8), IN `app` VARCHAR(25), IN `apm` VARCHAR(25), IN `nom` VARCHAR(25), IN `gen` CHAR(1), IN `fnac` DATE, IN `sue` FLOAT, IN `cod_car` CHAR(5))   BEGIN
    UPDATE tb_personal
    SET ap_paterno = app, ap_materno = apm, nombre = nom, genero = gen, fecha_nacimiento = fnac, sueldo = sue, codigo_cargo = cod_car
    WHERE dni = nd;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_editar_proveedor` (IN `xProveedorId` INT, IN `xNombre` VARCHAR(255), IN `xContacto` VARCHAR(255), IN `xDireccion` LONGTEXT, IN `xEmail` VARCHAR(255), IN `xTelefono` VARCHAR(255), IN `xRuc` VARCHAR(255))   BEGIN
	UPDATE proveedor
    SET nombre    = xNombre,
		contacto  = xContacto,
		direccion = xDireccion,
        email     = xEmail,
        telefono  = xTelefono,
        ruc       = xRuc
	WHERE proveedor_id = xProveedorId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EliminarInventario` (IN `p_inventario_id` INT)   BEGIN
    DELETE FROM inventario
    WHERE inventario_id = p_inventario_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EliminarUbicacion` (IN `p_id` INT)   BEGIN

    DELETE FROM ubicacion_almacen

    WHERE ubicacion_id = p_id;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminar_categoria` (IN `cod` INT)   BEGIN 

	delete from categoria where categoria_id = cod;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminar_marca` (IN `p_id` INT)   BEGIN
     DELETE FROM marca WHERE marca_id = p_id;
 END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminar_modelo` (IN `p_modelo_id` INT)   BEGIN
    DELETE FROM modelo WHERE modelo_id = p_modelo_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminar_personal` (IN `valor` CHAR(8))   BEGIN
    DELETE FROM tb_personal WHERE dni = valor;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminar_proveedor` (IN `xProveedorId` INT)   BEGIN
	DELETE FROM proveedor WHERE proveedor_id = xProveedorId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_FiltrarUbicacionesNombre` (IN `p_contenedor` VARCHAR(100))   BEGIN

    SELECT * FROM ubicacion_almacen

    WHERE contenedor LIKE p_contenedor;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_filtrar_categoria` (IN `nom` VARCHAR(100))   BEGIN

	select * from categoria where nombre 
    COLLATE utf8mb4_general_ci
    like 
    concat('%',nom,'%');

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_filtrar_marca` (IN `p_nombre` VARCHAR(100))   BEGIN
     SELECT id, nombre
     FROM marca
     WHERE nombre LIKE CONCAT('%', p_nombre, '%');
 END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_filtrar_proveedor_por_nombre` (IN `xNombre` VARCHAR(255))   BEGIN
	SELECT * FROM proveedor
    WHERE nombre LIKE CONCAT('%', xNombre, '%');
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_proveedor` (IN `xNombre` VARCHAR(255), IN `xContacto` VARCHAR(255), IN `xDireccion` LONGTEXT, IN `xEmail` VARCHAR(255), IN `xTelefono` VARCHAR(255), IN `xRuc` VARCHAR(255))   BEGIN
	INSERT INTO proveedor(nombre, contacto, direccion, email, telefono, ruc)
    VALUES (xNombre, xContacto, xDireccion, xEmail, xTelefono, xRuc);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ListarInventario` ()   BEGIN
    SELECT 
        i.inventario_id, 
        z.sku,
        m.nombre AS modelo,
        z.talla AS talla,
        z.color AS color,
        ma.nombre AS marca,
        i.cantidad_actual AS stock,
        CONCAT(u.pasillo, '-', u.estante, '-', u.contenedor) AS almacen
    FROM inventario i
    JOIN zapato z ON i.zapato_id = z.zapato_id
    JOIN modelo m ON z.modelo_id = m.modelo_id
    JOIN marca ma ON m.marca_id = ma.marca_id
    JOIN ubicacion_almacen u ON i.ubicacion_id = u.ubicacion_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ListarUbicaciones` ()   BEGIN

    SELECT * FROM ubicacion_almacen;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listar_cargo` ()   BEGIN
    SELECT * FROM tb_cargo ORDER BY cargo ASC;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listar_categorias` ()   BEGIN 

	SELECT * FROM categoria;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listar_marcas` ()   BEGIN
     SELECT marca_id, nombre FROM marca ORDER BY marca_id;
 END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listar_modelos` ()   BEGIN
    SELECT m.modelo_id, m.nombre, m.genero,
           c.categoria_id, c.nombre AS categoria_nombre,
           ma.marca_id, ma.nombre AS marca_nombre
    FROM modelo m
    JOIN categoria c ON m.categoria_id = c.categoria_id
    JOIN marca ma ON m.marca_id = ma.marca_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listar_personal` ()   BEGIN
    SELECT p.dni, p.ap_paterno, p.ap_materno, p.nombre, p.genero, p.fecha_nacimiento, p.sueldo, c.codigo_cargo, c.cargo 
    FROM tb_personal p 
    JOIN tb_cargo c ON c.codigo_cargo = p.codigo_cargo 
    ORDER BY ap_paterno ASC;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listar_proveedor` ()   BEGIN
	SELECT * FROM proveedor;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ObtenerInventarioPorID` (IN `p_inventario_id` INT)   BEGIN
    SELECT 
        i.inventario_id,
        z.sku,
        m.nombre AS modelo,
        z.talla,
        z.color,
        ma.nombre AS marca,
        i.cantidad_actual,
        u.ubicacion_id,
        u.pasillo,
        u.estante,
        u.contenedor
    FROM inventario i
    JOIN zapato z ON i.zapato_id = z.zapato_id
    JOIN modelo m ON z.modelo_id = m.modelo_id
    JOIN marca ma ON m.marca_id = ma.marca_id
    JOIN ubicacion_almacen u ON i.ubicacion_id = u.ubicacion_id
    WHERE i.inventario_id = p_inventario_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtener_marca` (IN `p_id` INT)   BEGIN
     SELECT marca_id, nombre
     FROM marca
     WHERE marca_id = p_id;
 END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_RegistrarInventario` (IN `p_zapato_id` INT, IN `p_ubicacion_id` INT, IN `p_cantidad_actual` INT)   BEGIN
    INSERT INTO inventario (zapato_id, ubicacion_id, cantidad_actual)
    VALUES (p_zapato_id, p_ubicacion_id, p_cantidad_actual);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_registrar_marca` (IN `p_nombre` VARCHAR(100))   BEGIN
     INSERT INTO marca (nombre) VALUES (p_nombre);
 END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_registrar_modelo` (IN `p_nombre` VARCHAR(100), IN `p_genero` VARCHAR(20), IN `p_categoria_id` INT, IN `p_marca_id` INT)   BEGIN
    INSERT INTO modelo(nombre, genero, categoria_id, marca_id)
    VALUES (p_nombre, p_genero, p_categoria_id, p_marca_id);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_registrar_personal` (IN `nd` CHAR(8), IN `app` VARCHAR(25), IN `apm` VARCHAR(25), IN `nom` VARCHAR(25), IN `gen` CHAR(1), IN `fnac` DATE, IN `sue` FLOAT, IN `cod_car` CHAR(5))   BEGIN
    INSERT INTO tb_personal (dni, ap_paterno, ap_materno, nombre, genero, fecha_nacimiento, sueldo, codigo_cargo) 
    VALUES (nd, app, apm, nom, gen, fnac, sue, cod_car);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_traer_entidad` (IN `cod` INT)   BEGIN

select * from categoria where categoria_id = cod;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_traer_modelo_por_id` (IN `p_modelo_id` INT)   BEGIN
    SELECT m.modelo_id, m.nombre, m.genero,
           c.categoria_id, c.nombre AS categoria_nombre,
           ma.marca_id, ma.nombre AS marca_nombre
    FROM modelo m
    JOIN categoria c ON m.categoria_id = c.categoria_id
    JOIN marca ma ON m.marca_id = ma.marca_id
    WHERE m.modelo_id = p_modelo_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `verificar_usuario` (IN `p_usuario` VARCHAR(50), IN `p_contrasena` VARCHAR(255), OUT `p_existe` INT)   BEGIN
    SELECT COUNT(*) INTO p_existe
    FROM usuarios
    WHERE usuario = p_usuario AND contrasena = p_contrasena;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `categoria_id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`categoria_id`, `nombre`, `descripcion`) VALUES
(1, 'Formal', 'Calzado elegante para eventos formales'),
(2, 'Casual', 'Calzado cómodo para uso diario'),
(3, 'Deportiva', 'Calzado técnico para actividades deportivas'),
(5, '0', 'Nueva Marca');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_entrada`
--

CREATE TABLE `detalle_entrada` (
  `detalle_id` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_compra` decimal(10,2) NOT NULL,
  `entrada_id` int(11) NOT NULL,
  `ubicacion_id` int(11) NOT NULL,
  `zapato_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_entrada`
--

INSERT INTO `detalle_entrada` (`detalle_id`, `cantidad`, `precio_compra`, `entrada_id`, `ubicacion_id`, `zapato_id`) VALUES
(18, 50, 85.00, 1, 1, 1),
(19, 40, 105.00, 2, 2, 2),
(20, 60, 75.00, 3, 3, 3),
(21, 55, 95.00, 4, 4, 4),
(22, 45, 110.00, 5, 5, 5),
(23, 65, 55.00, 6, 6, 6),
(24, 70, 50.00, 7, 7, 7),
(25, 50, 65.00, 8, 8, 8),
(26, 60, 75.00, 9, 9, 9),
(27, 55, 60.00, 10, 10, 10),
(28, 45, 65.00, 11, 11, 11),
(29, 50, 80.00, 12, 12, 12),
(30, 60, 85.00, 13, 13, 13),
(31, 65, 90.00, 14, 14, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrada`
--

CREATE TABLE `entrada` (
  `entrada_id` int(11) NOT NULL,
  `fecha_entrada` date NOT NULL,
  `orden_compra` varchar(255) DEFAULT NULL,
  `proveedor_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `entrada`
--

INSERT INTO `entrada` (`entrada_id`, `fecha_entrada`, `orden_compra`, `proveedor_id`) VALUES
(1, '2025-01-05', 'OC-0001', 1),
(2, '2025-01-06', 'OC-0002', 2),
(3, '2025-01-07', 'OC-0003', 3),
(4, '2025-01-08', 'OC-0004', 4),
(5, '2025-01-09', 'OC-0005', 5),
(6, '2025-01-10', 'OC-0006', 6),
(7, '2025-01-11', 'OC-0007', 7),
(8, '2025-01-12', 'OC-0008', 8),
(9, '2025-01-13', 'OC-0009', 9),
(10, '2025-01-14', 'OC-0010', 10),
(11, '2025-01-15', 'OC-0011', 11),
(12, '2025-01-16', 'OC-0012', 12),
(13, '2025-01-17', 'OC-0013', 13),
(14, '2025-01-18', 'OC-0014', 14),
(15, '2025-01-19', 'OC-0015', 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
  `inventario_id` int(11) NOT NULL,
  `cantidad_actual` int(11) NOT NULL DEFAULT 0,
  `stock_maximo` int(11) NOT NULL DEFAULT 100,
  `stock_minimo` int(11) NOT NULL DEFAULT 10,
  `ultima_actualizacion` datetime(6) NOT NULL,
  `ubicacion_id` int(11) NOT NULL,
  `zapato_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`inventario_id`, `cantidad_actual`, `stock_maximo`, `stock_minimo`, `ultima_actualizacion`, `ubicacion_id`, `zapato_id`) VALUES
(3, 50, 100, 20, '2025-04-29 21:12:13.000000', 1, 1),
(4, 40, 80, 15, '2025-04-29 21:12:13.000000', 2, 2),
(5, 60, 120, 25, '2025-04-29 21:12:13.000000', 3, 3),
(6, 55, 110, 20, '2025-04-29 21:12:13.000000', 4, 4),
(7, 45, 90, 18, '2025-04-29 21:12:13.000000', 5, 5),
(8, 65, 130, 30, '2025-04-29 21:12:13.000000', 6, 6),
(9, 70, 140, 35, '2025-04-29 21:12:13.000000', 7, 7),
(10, 50, 100, 20, '2025-04-29 21:12:13.000000', 8, 8),
(11, 60, 120, 25, '2025-04-29 21:12:13.000000', 9, 9),
(12, 55, 110, 22, '2025-04-29 21:12:13.000000', 10, 10),
(13, 45, 90, 18, '2025-04-29 21:12:13.000000', 11, 11),
(14, 50, 100, 20, '2025-04-29 21:12:13.000000', 12, 12),
(15, 60, 120, 25, '2025-04-29 21:12:13.000000', 13, 13),
(16, 65, 130, 30, '2025-04-29 21:12:13.000000', 14, 14),
(17, 50, 25, 21, '2025-04-29 21:12:13.000000', 14, 37);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `marca_id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`marca_id`, `nombre`) VALUES
(2, 'Adidas'),
(6, 'Clarks'),
(14, 'Converse'),
(8, 'Ecco'),
(7, 'Geox'),
(15, 'md'),
(10, 'Merrell'),
(11, 'New Balance'),
(1, 'Nike 15'),
(3, 'Puma'),
(4, 'Reebok'),
(9, 'Salomon'),
(12, 'Skechers'),
(5, 'Timberland'),
(13, 'Vans');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `modelo`
--

CREATE TABLE `modelo` (
  `modelo_id` int(11) NOT NULL,
  `genero` enum('Femenino','Masculino','Unisex') NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `categoria_id` int(11) NOT NULL,
  `marca_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `modelo`
--

INSERT INTO `modelo` (`modelo_id`, `genero`, `nombre`, `categoria_id`, `marca_id`) VALUES
(1, 'Masculino', 'Oxford Clásico', 1, 1),
(2, 'Femenino', 'Tacón Elegante', 1, 2),
(3, 'Unisex', 'Mocasín Premium', 1, 3),
(4, 'Masculino', 'Zapato de Vestir', 1, 4),
(5, 'Femenino', 'Bailarina Sofisticada', 1, 5),
(6, 'Masculino', 'Zapato Casual', 2, 6),
(7, 'Femenino', 'Alpargata Confort', 2, 7),
(8, 'Unisex', 'Mocasín Informal', 2, 8),
(9, 'Masculino', 'Bota Rugosa', 2, 9),
(10, 'Femenino', 'Sandalia Relax', 2, 10),
(11, 'Masculino', 'Zapatilla Running', 3, 11),
(12, 'Femenino', 'Zapatilla Trail', 3, 12),
(13, 'Unisex', 'Zapato Trekking', 3, 13),
(14, 'Masculino', 'Bota Futbol', 3, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `proveedor_id` int(11) NOT NULL,
  `contacto` varchar(255) DEFAULT NULL,
  `direccion` longtext DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `ruc` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`proveedor_id`, `contacto`, `direccion`, `email`, `nombre`, `telefono`, `ruc`) VALUES
(1, 'Carlos Méndez', 'Av. Principal 123', 'elegante@mail.com', 'Zapatería Elegante', '912345678', '12345678901'),
(2, 'Laura Gómez', 'Calle Deportes 45', 'deportivos@mail.com', 'Deportivos SA', '987654321', '23456789012'),
(3, 'Pedro López', 'Jr. Informal 67', 'casual@mail.com', 'Moda Casual', '934567890', '34567890123'),
(4, 'Ana Torres', 'Av. Calidad 890', 'premium@mail.com', 'Calzados Premium', '945678901', '45678901234'),
(5, 'Juan Castro', 'Calle Veloz 123', 'rapida@mail.com', 'Distribuidora Rápida', '956789012', '56789012345'),
(6, 'Marta Ríos', 'Av. Mundial 456', 'global@mail.com', 'Suministros Globales', '967890123', '67890123456'),
(7, 'Diego Soto', 'Jr. Ciudad 789', 'urbanos@mail.com', 'Calzados Urbanos', '978901234', '78901234567'),
(8, 'Lucía Vargas', 'Av. Aventura 012', 'extremos@mail.com', 'Deportes Extremos', '989012345', '89012345678'),
(9, 'Sergio Peña', 'Calle Tradición 345', 'clasicos@mail.com', 'Zapatos Clásicos', '990123456', '90123456789'),
(10, 'Valeria Cruz', 'Av. Global 678', 'internacional@mail.com', 'Moda Internacional', '901234567', '01234567890'),
(11, 'Andrés Mora', 'Jr. Innovación 901', 'tecnico@mail.com', 'Calzado Técnico', '912345670', '11223344556'),
(12, 'Camila Rojas', 'Av. Unión 234', 'unidas@mail.com', 'Distribuciones Unidas', '923456789', '22334455667'),
(13, 'Gabriel Paredes', 'Calle Moda 567', 'moderna@mail.com', 'Zapatería Moderna', '934567890', '33445566778'),
(14, 'Fernanda Silva', 'Av. Energía 890', 'total@mail.com', 'Deportes Total', '945678901', '44556677889'),
(15, 'Ricardo Castro', 'Calle Exclusiva 123', 'elite@mail.com', 'Calzados Élite', '956789012', '55667788990');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicacion_almacen`
--

CREATE TABLE `ubicacion_almacen` (
  `ubicacion_id` int(11) NOT NULL,
  `contenedor` varchar(255) NOT NULL,
  `estante` varchar(255) NOT NULL,
  `pasillo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ubicacion_almacen`
--

INSERT INTO `ubicacion_almacen` (`ubicacion_id`, `contenedor`, `estante`, `pasillo`) VALUES
(1, '1', '1', 'A'),
(2, '2', '1', 'A'),
(3, '3', '1', 'A'),
(4, '1', '2', 'A'),
(5, '2', '2', 'A'),
(6, '3', '2', 'A'),
(7, '1', '1', 'B'),
(8, '2', '1', 'B'),
(9, '3', '1', 'B'),
(10, '1', '2', 'B'),
(11, '2', '2', 'B'),
(12, '3', '2', 'B'),
(13, '1', '1', 'C'),
(14, '2', '1', 'C'),
(15, '3', '1', 'C');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `usuario` varchar(30) NOT NULL,
  `contrasena` varchar(30) NOT NULL
) ;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`usuario`, `contrasena`) VALUES
('root', '123');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zapato`
--

CREATE TABLE `zapato` (
  `zapato_id` int(11) NOT NULL,
  `color` varchar(255) NOT NULL,
  `costo` decimal(10,2) NOT NULL,
  `sku` varchar(255) NOT NULL,
  `talla` decimal(3,1) NOT NULL,
  `modelo_id` int(11) NOT NULL,
  `porcentaje_ganancia` decimal(5,2) NOT NULL DEFAULT 0.00,
  `precio` decimal(10,2) GENERATED ALWAYS AS (`costo` * (1 + `porcentaje_ganancia` / 100)) STORED
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `zapato`
--

INSERT INTO `zapato` (`zapato_id`, `color`, `costo`, `sku`, `talla`, `modelo_id`, `porcentaje_ganancia`) VALUES
(1, 'Negro', 120.00, 'SKU-FORM-001', 42.0, 1, 30.00),
(2, 'Marrón', 150.00, 'SKU-FORM-002', 38.0, 2, 25.00),
(3, 'Azul Marino', 110.00, 'SKU-FORM-003', 40.0, 3, 35.00),
(4, 'Café', 130.00, 'SKU-FORM-004', 41.0, 4, 28.00),
(5, 'Negro', 140.00, 'SKU-FORM-005', 39.0, 5, 30.00),
(6, 'Claro', 250.00, 'SKU-CAS-001', 39.0, 14, 40.00),
(7, 'Blanco', 75.00, 'SKU-CAS-002', 36.0, 7, 35.00),
(8, 'Gris', 90.00, 'SKU-CAS-003', 37.0, 8, 30.00),
(9, 'Marrón', 100.00, 'SKU-CAS-004', 44.0, 9, 25.00),
(10, 'Azul', 85.00, 'SKU-CAS-005', 42.0, 10, 40.00),
(11, 'Rojo', 95.00, 'SKU-DEP-001', 45.0, 11, 50.00),
(12, 'Negro/Amarillo', 110.00, 'SKU-DEP-002', 39.0, 12, 45.00),
(13, 'Verde', 120.00, 'SKU-DEP-003', 40.0, 13, 40.00),
(14, 'Blanco/Negro', 130.00, 'SKU-DEP-004', 41.0, 14, 35.00),
(30, 'Fucsia', 500.00, 'DJF-MULTI', 39.0, 12, 0.00),
(31, 'Rojo', 25.00, 'SKU-01', 39.0, 10, 0.00),
(32, 'Negro', 350.00, 'SKU-02', 39.0, 11, 0.00),
(34, 'Dorado', 5550.00, 'SKU-03', 44.0, 10, 0.00),
(35, 'Verde', 400.00, 'SKU-04', 41.0, 14, 0.00),
(36, 'Azul', 350.00, 'SKU-05', 42.0, 11, 0.00),
(37, 'Verde', 10.00, 'FFFF-15', 29.0, 3, 0.00);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`categoria_id`),
  ADD UNIQUE KEY `UK35t4wyxqrevf09uwx9e9p6o75` (`nombre`);

--
-- Indices de la tabla `detalle_entrada`
--
ALTER TABLE `detalle_entrada`
  ADD PRIMARY KEY (`detalle_id`),
  ADD KEY `FKlab3gmfvasge851t36uvs4e82` (`entrada_id`),
  ADD KEY `FK5n6yil44smxfey7c9prc3h410` (`ubicacion_id`),
  ADD KEY `FK3dhujupnk5apy9l3ccr6s4tll` (`zapato_id`);

--
-- Indices de la tabla `entrada`
--
ALTER TABLE `entrada`
  ADD PRIMARY KEY (`entrada_id`),
  ADD KEY `FKs90da0scilue4ye83wyl94ftk` (`proveedor_id`);

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`inventario_id`),
  ADD UNIQUE KEY `UKnodkk1p2phdynmtai5m7fudig` (`zapato_id`,`ubicacion_id`),
  ADD KEY `FK38apr4nts8dxpgbd968kupm29` (`ubicacion_id`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`marca_id`),
  ADD UNIQUE KEY `UKg42kcgw83i65q054yikohi8b9` (`nombre`);

--
-- Indices de la tabla `modelo`
--
ALTER TABLE `modelo`
  ADD PRIMARY KEY (`modelo_id`),
  ADD KEY `FK1qunbybfv0exqex3lmvsp1byk` (`categoria_id`),
  ADD KEY `FKllxq2dldvhxvb5q9csar7vdfy` (`marca_id`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`proveedor_id`);

--
-- Indices de la tabla `ubicacion_almacen`
--
ALTER TABLE `ubicacion_almacen`
  ADD PRIMARY KEY (`ubicacion_id`),
  ADD UNIQUE KEY `UKh7fs8iogkbujfh51udk6tfrr9` (`pasillo`,`estante`,`contenedor`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `zapato`
--
ALTER TABLE `zapato`
  ADD PRIMARY KEY (`zapato_id`),
  ADD UNIQUE KEY `UKlgowa1fj85y63muajtvsl23dl` (`sku`),
  ADD KEY `FKohn0ot0qaplh60glogx40nf2u` (`modelo_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `categoria_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `detalle_entrada`
--
ALTER TABLE `detalle_entrada`
  MODIFY `detalle_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `entrada`
--
ALTER TABLE `entrada`
  MODIFY `entrada_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `inventario`
--
ALTER TABLE `inventario`
  MODIFY `inventario_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `marca_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `modelo`
--
ALTER TABLE `modelo`
  MODIFY `modelo_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `proveedor_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `ubicacion_almacen`
--
ALTER TABLE `ubicacion_almacen`
  MODIFY `ubicacion_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `zapato`
--
ALTER TABLE `zapato`
  MODIFY `zapato_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_entrada`
--
ALTER TABLE `detalle_entrada`
  ADD CONSTRAINT `FK3dhujupnk5apy9l3ccr6s4tll` FOREIGN KEY (`zapato_id`) REFERENCES `zapato` (`zapato_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK5n6yil44smxfey7c9prc3h410` FOREIGN KEY (`ubicacion_id`) REFERENCES `ubicacion_almacen` (`ubicacion_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKlab3gmfvasge851t36uvs4e82` FOREIGN KEY (`entrada_id`) REFERENCES `entrada` (`entrada_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `entrada`
--
ALTER TABLE `entrada`
  ADD CONSTRAINT `FKs90da0scilue4ye83wyl94ftk` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`proveedor_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD CONSTRAINT `FK38apr4nts8dxpgbd968kupm29` FOREIGN KEY (`ubicacion_id`) REFERENCES `ubicacion_almacen` (`ubicacion_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKss4cebmfji3enbjenpvaogv2r` FOREIGN KEY (`zapato_id`) REFERENCES `zapato` (`zapato_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `modelo`
--
ALTER TABLE `modelo`
  ADD CONSTRAINT `FK1qunbybfv0exqex3lmvsp1byk` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`categoria_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKllxq2dldvhxvb5q9csar7vdfy` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`marca_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `zapato`
--
ALTER TABLE `zapato`
  ADD CONSTRAINT `FKohn0ot0qaplh60glogx40nf2u` FOREIGN KEY (`modelo_id`) REFERENCES `modelo` (`modelo_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
