
IF DB_ID('inventario_elite') IS NULL
    CREATE DATABASE inventario_elite;
GO
USE inventario_elite;
GO


IF OBJECT_ID('detalle_entrada','U')    IS NOT NULL DROP TABLE detalle_entrada;
IF OBJECT_ID('inventario','U')         IS NOT NULL DROP TABLE inventario;
IF OBJECT_ID('entrada','U')            IS NOT NULL DROP TABLE entrada;
IF OBJECT_ID('zapato','U')             IS NOT NULL DROP TABLE zapato;
IF OBJECT_ID('modelo','U')             IS NOT NULL DROP TABLE modelo;
IF OBJECT_ID('ubicacion_almacen','U')  IS NOT NULL DROP TABLE ubicacion_almacen;
IF OBJECT_ID('proveedor','U')          IS NOT NULL DROP TABLE proveedor;
IF OBJECT_ID('marca','U')              IS NOT NULL DROP TABLE marca;
IF OBJECT_ID('categoria','U')          IS NOT NULL DROP TABLE categoria;
IF OBJECT_ID('[user]','U')             IS NOT NULL DROP TABLE [user];
GO

-- Tabla: categoria
CREATE TABLE categoria (
  categoria_id    INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  nombre          NVARCHAR(255)   NOT NULL,
  descripcion     NVARCHAR(MAX)   NULL,
  CONSTRAINT UK35t4wyxqrevf09uwx9e9p6o75 UNIQUE NONCLUSTERED(nombre)
);
GO

-- Tabla: marca
CREATE TABLE marca (
  marca_id        INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  nombre          NVARCHAR(255)   NOT NULL,
  CONSTRAINT UKg42kcgw83i65q054yikohi8b9 UNIQUE NONCLUSTERED(nombre)
);
GO

-- Tabla: proveedor
CREATE TABLE proveedor (
  proveedor_id    INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  contacto        NVARCHAR(255)   NULL,
  direccion       NVARCHAR(MAX)   NULL,
  email           NVARCHAR(255)   NULL,
  nombre          NVARCHAR(255)   NOT NULL,
  telefono        NVARCHAR(255)   NULL,
  ruc             NVARCHAR(255)   NULL
);
GO

-- Tabla: ubicacion_almacen
CREATE TABLE ubicacion_almacen (
  ubicacion_id    INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  contenedor      NVARCHAR(255)   NOT NULL,
  estante         NVARCHAR(255)   NOT NULL,
  pasillo         NVARCHAR(255)   NOT NULL,
  CONSTRAINT UKh7fs8iogkbujfh51udk6tfrr9 UNIQUE NONCLUSTERED(pasillo,estante,contenedor)
);
GO

-- Tabla: modelo
CREATE TABLE modelo (
  modelo_id       INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  genero          NVARCHAR(10)    NOT NULL CHECK (genero IN ('Femenino','Masculino','Unisex')),
  nombre          NVARCHAR(255)   NOT NULL,
  categoria_id    INT             NOT NULL,
  marca_id        INT             NOT NULL,
  CONSTRAINT FK1qunbybfv0exqex3lmvsp1byk FOREIGN KEY (categoria_id) REFERENCES categoria(categoria_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FKllxq2dldvhxvb5q9csar7vdfy FOREIGN KEY (marca_id)     REFERENCES marca(marca_id)       ON DELETE CASCADE ON UPDATE CASCADE
);
GO
CREATE INDEX IX_modelo_categoria ON modelo(categoria_id);
CREATE INDEX IX_modelo_marca     ON modelo(marca_id);
GO

-- Tabla: zapato
CREATE TABLE zapato (
  zapato_id            INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  color                NVARCHAR(255)   NOT NULL,
  costo                DECIMAL(10,2)   NOT NULL,
  sku                  NVARCHAR(255)   NOT NULL,
  talla                DECIMAL(3,1)    NOT NULL,
  modelo_id            INT             NOT NULL,
  porcentaje_ganancia  DECIMAL(5,2)    NOT NULL DEFAULT 0.00,
  precio               AS (costo * (1 + porcentaje_ganancia/100.0)) PERSISTED,
  CONSTRAINT FKohn0ot0qaplh60glogx40nf2u FOREIGN KEY (modelo_id) REFERENCES modelo(modelo_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UKm8w1f6gnh2v76mwnpox2ftn3a UNIQUE NONCLUSTERED(modelo_id,talla,color),
  CONSTRAINT UKlgowa1fj85y63muajtvsl23dl UNIQUE NONCLUSTERED(sku)
);
GO
CREATE INDEX IX_zapato_modelo ON zapato(modelo_id);
GO

-- Tabla: entrada
CREATE TABLE entrada (
  entrada_id      INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  fecha_entrada   DATE            NOT NULL,
  orden_compra    NVARCHAR(255)   NULL,
  proveedor_id    INT             NOT NULL,
  CONSTRAINT FKs90da0scilue4ye83wyl94ftk FOREIGN KEY (proveedor_id) REFERENCES proveedor(proveedor_id) ON DELETE CASCADE ON UPDATE CASCADE
);
GO
CREATE INDEX IX_entrada_proveedor ON entrada(proveedor_id);
GO

-- Tabla: inventario
CREATE TABLE inventario (
  inventario_id   INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  cantidad_actual INT             NOT NULL DEFAULT 0,
  stock_maximo    INT             NOT NULL DEFAULT 100,
  stock_minimo    INT             NOT NULL DEFAULT 10,
  ultima_actualizacion DATETIME2(6) NOT NULL,
  ubicacion_id    INT             NOT NULL,
  zapato_id       INT             NOT NULL,
  CONSTRAINT UKnodkk1p2phdynmtai5m7fudig UNIQUE NONCLUSTERED(zapato_id,ubicacion_id),
  CONSTRAINT FK38apr4nts8dxpgbd968kupm29 FOREIGN KEY (ubicacion_id) REFERENCES ubicacion_almacen(ubicacion_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FKss4cebmfji3enbjenpvaogv2r FOREIGN KEY (zapato_id)       REFERENCES zapato(zapato_id)       ON DELETE CASCADE ON UPDATE CASCADE
);
GO
CREATE INDEX IX_inventario_ubicacion ON inventario(ubicacion_id);
CREATE INDEX IX_inventario_zapato    ON inventario(zapato_id);
GO

-- Tabla: detalle_entrada
CREATE TABLE detalle_entrada (
  detalle_id      INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  cantidad        INT             NOT NULL,
  precio_compra   DECIMAL(10,2)   NOT NULL,
  entrada_id      INT             NOT NULL,
  ubicacion_id    INT             NOT NULL,
  zapato_id       INT             NOT NULL,
  CONSTRAINT FKlab3gmfvasge851t36uvs4e82 FOREIGN KEY (entrada_id)   REFERENCES entrada(entrada_id)          ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK5n6yil44smxfey7c9prc3h410 FOREIGN KEY (ubicacion_id) REFERENCES ubicacion_almacen(ubicacion_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK3dhujupnk5apy9l3ccr6s4tll FOREIGN KEY (zapato_id)    REFERENCES zapato(zapato_id)           ON DELETE CASCADE ON UPDATE CASCADE
);
GO
CREATE INDEX IX_detalle_entrada_entrada   ON detalle_entrada(entrada_id);
CREATE INDEX IX_detalle_entrada_ubicacion ON detalle_entrada(ubicacion_id);
CREATE INDEX IX_detalle_entrada_zapato     ON detalle_entrada(zapato_id);
GO

-- Tabla: user
CREATE TABLE [user] (
  id               INT             IDENTITY(1,1) NOT NULL PRIMARY KEY,
  created_at       DATETIME2(6)    NULL,
  email            NVARCHAR(255)   NULL,
  password         NVARCHAR(255)   NULL,
  updated_at       DATETIME2(6)    NULL,
  username         NVARCHAR(255)   NULL,
  fecha_creacion   DATETIME2(6)    NULL
);
GO

----------------------------------------
-- Datos de ejemplo (manteniendo IDs) --
----------------------------------------

-- categoria
SET IDENTITY_INSERT categoria ON;
INSERT INTO categoria (categoria_id,nombre,descripcion) VALUES
 (1,'Deportivo','Calzado especializado para hacer deport.'),
 (2,'Escolar','Calzado para uso escolar.'),
 (3,'Urbano','Calzado para uso casual en la ciudad'),
 (4,'Formal','Calzado para eventos y reuniones formales');
SET IDENTITY_INSERT categoria OFF;
GO

-- marca
SET IDENTITY_INSERT marca ON;
INSERT INTO marca (marca_id,nombre) VALUES
 (3,'Adidas'),(6,'New Balance'),(1,'Nike'),
 (4,'Puma'),(2,'Rebook'),(5,'Vans');
SET IDENTITY_INSERT marca OFF;
GO

-- modelo
SET IDENTITY_INSERT modelo ON;
INSERT INTO modelo (modelo_id,genero,nombre,categoria_id,marca_id) VALUES
 (1,'Masculino','Air Zoom Pegasus',1,1),
 (2,'Femenino','Fresh Foam 1080',1,6),
 (3,'Unisex','Classic Slip-On',3,5),
 (4,'Masculino','Club C 85',3,2),
 (5,'Femenino','Cali Star',3,4),
 (8,'Masculino','Roma Basic',4,4),
 (9,'Femenino','Royal Complete Clean',4,2),
 (10,'Unisex','Old Skool',3,5),
 (11,'Masculino','Air Max 90',1,1),
 (12,'Femenino','FuelCell Rebel',1,6),
 (13,'Unisex','Gazelle',3,3),
 (14,'Masculino','Nano X3',1,2),
 (15,'Femenino','Mayze Stack',3,4),
 (16,'Unisex','Sk8-Hi',3,5),
 (17,'Femenino','Air Max Dia',1,1),
 (18,'Masculino','Nitrel v4',1,6),
 (19,'Femenino','Stan Smith Bold',4,3),
 (20,'Unisex','Club C Revenge',3,2),
 (21,'Masculino','RS-X',3,4),
 (22,'Femenino','Era Platform',3,5),
 (23,'Unisex','Air Force 1',3,1),
 (24,'Masculino','Fresh Foam Hierro',1,6),
 (25,'Femenino','Superstar Bold',4,3),
 (26,'Unisex','Zig Kinetica',1,2),
 (27,'Masculino','Caven',3,4),
 (28,'Femenino','UltraRange Rapidweld',1,5);
SET IDENTITY_INSERT modelo OFF;
GO

-- proveedor
SET IDENTITY_INSERT proveedor ON;
INSERT INTO proveedor (proveedor_id,contacto,direccion,email,nombre,telefono,ruc) VALUES
 (13,'Raul Mendez','Tupac','yolu@gmail.comm','Yolusa','939428950','123457'),
 (14,'Lucía Romero','Av. Los Olivos 123','lucia@urbanstep.com','UrbanStep','987654321','20145678900'),
 (15,'Carlos Peña','Calle Lima 456','carlos@maxshoes.com','MaxShoes','912345678','20356987412'),
 (16,'Andrea Flores','Jr. Libertad 789','andrea@zonasneaker.pe','ZonaSneaker','900112233','20547896547'),
 (17,'Miguel Torres','Av. Grau 1020','miguel@deporcenter.com','DeportesCenter','911223344','20984512365');
SET IDENTITY_INSERT proveedor OFF;
GO

-- ubicacion_almacen
SET IDENTITY_INSERT ubicacion_almacen ON;
INSERT INTO ubicacion_almacen (ubicacion_id,contenedor,estante,pasillo) VALUES
 (1,'A-Adidas','Estante A1','Pasillo 1'),
 (5,'A-Adidas','Estante A2','Pasillo 1'),
 (6,'A-Adidas','Estante A3','Pasillo 1'),
 (37,'V-Vans','estante n5','pasillo 1'),
 (7,'N-Nike','Estante N1','Pasillo 2'),
 (8,'N-Nike','Estante N2','Pasillo 2'),
 (9,'N-Nike','Estante N3','Pasillo 2'),
 (23,'N-Nike','ESTANTE N4','pasillo 3'),
 (10,'P-Puma','Estante P1','Pasillo 3'),
 (11,'P-Puma','Estante P2','Pasillo 3'),
 (12,'P-Puma','Estante P3','Pasillo 3'),
 (13,'NB-NewBalance','Estante NB1','Pasillo 4'),
 (14,'NB-NewBalance','Estante NB2','Pasillo 4'),
 (15,'NB-NewBalance','Estante NB3','Pasillo 4'),
 (4,'A-Adidas','Estante A1','Pasillo 5'),
 (16,'R-Reebok','Estante R1','Pasillo 5'),
 (17,'R-Reebok','Estante R2','Pasillo 5'),
 (18,'R-Reebok','Estante R3','Pasillo 5'),
 (19,'V-Vans','Estante V1','Pasillo 6'),
 (20,'V-Vans','Estante V2','Pasillo 6'),
 (21,'V-Vans','Estante V3','Pasillo 6');
SET IDENTITY_INSERT ubicacion_almacen OFF;
GO

-- user
SET IDENTITY_INSERT [user] ON;
INSERT INTO [user] (id,created_at,email,password,updated_at,username,fecha_creacion) VALUES
 (3,NULL,NULL,'$2a$10$E9Sbqb3p9.Q2w402KTQLDeAMQYRymXF6ehDe70uUulpdYS5h0i8ee',NULL,'anthony',NULL);
SET IDENTITY_INSERT [user] OFF;
GO

-- zapato
SET IDENTITY_INSERT zapato ON;
INSERT INTO zapato (zapato_id,color,costo,sku,talla,modelo_id,porcentaje_ganancia) VALUES
 (63,'Negro',120.00,'NK-AZP-001',42.0,1,25.00),
 (64,'Rosado',130.50,'NB-FF1080-002',38.0,2,30.00),
 (65,'Blanco',110.00,'VN-CS-003',41.0,3,20.00),
 (66,'Gris',100.00,'RB-CLUB-004',43.0,2,15.00),
 (67,'Blanco',105.00,'PM-CSTAR-005',39.0,5,18.00),
 (70,'Blanco',140.00,'PM-ROMA-008',42.0,8,22.00),
 (71,'Beige',135.00,'RB-RCC-009',37.5,9,25.00),
 (72,'Negro',125.00,'VN-OSK-010',40.0,10,18.50),
 (73,'Rojo',145.00,'NK-AM90-011',42.5,11,28.00),
 (74,'Celeste',138.00,'NB-FUEL-012',36.0,12,26.00),
 (75,'Verde',115.00,'AD-GAZ-013',44.0,13,19.00),
 (76,'Amarillo',98.00,'RB-NANO-014',41.0,14,20.00),
 (77,'Rosa',88.00,'PM-MAYZE-015',37.0,15,17.50),
 (78,'ROJO',90.00,'ZA-Air-RO',44.0,1,12.00),
 (79,'a',2.00,'sd-23',3.0,1,3.00),
 (83,'ROjo',230.00,'sd-sd',23.0,5,10.00),
 (85,'ROjo',199.00,'old-ro-a3',42.0,10,16.25),
 (86,'azul',200.00,'ad-rojo-44',44.0,1,15.25),
 (128,'azul',140.00,'AZ-OLD',41.0,10,15.00),
 (135,'azul',180.00,'nano-azul',42.0,14,15.00);
SET IDENTITY_INSERT zapato OFF;
GO


CREATE PROCEDURE ListarZapatillas
AS
BEGIN
    SELECT z.zapato_id, z.color, z.talla, z.costo, z.sku, z.precio, m.modelo_id, m.nombre as modelo, m.genero
    , mr.marca_id, mr.nombre as marca, ca.categoria_id, ca.nombre as categoria, ca.descripcion
    FROM zapato z JOIN modelo m ON z.modelo_id = m.modelo_id JOIN marca mr ON m.marca_id = mr.marca_id
    JOIN categoria ca ON ca.categoria_id = m.categoria_id;
END

--EXEC ListarZapatillas;