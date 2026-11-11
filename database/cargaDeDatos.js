// ==========================================================
// SCRIPT DE INSERCIÓN DE DATOS DE PRUEBA - TXANDASPORT
// creacion de datos por orden de inserción y dependencias caoturando _ids
// comprovación de relaciones cruzadas entre colecciones con insertId
// ==========================================================

print("--- Limpiando colecciones anteriores ---");
db.logros.deleteMany({});
db.polideportivos.deleteMany({});
db.usuarios.deleteMany({});
db.pistas.deleteMany({});
db.actividades.deleteMany({});
db.reservas.deleteMany({});


// ----------------------------------------------------------
// 1. LOGROS (Independientes)
// ----------------------------------------------------------
print("--- Insertando logros y capturando sus IDs ---");
const logro1 = db.logros.insertOne({
  nombre: "Primer Partido",
  descripcion: "Has completado tu primera reserva en el polideportivo.",
  iconoUrl: "https://txandasport.eus/logros/primer_partido.png"
}).insertedId;

const logro2 = db.logros.insertOne({
  nombre: "Deportista Frecuente",
  descripcion: "Has participado en más de 5 actividades o reservas.",
  iconoUrl: "https://txandasport.eus/logros/frecuente.png"
}).insertedId;

const logro3 = db.logros.insertOne({
  nombre: "Maestro del Pádel",
  descripcion: "Has jugado 10 partidos de pádel.",
  iconoUrl: "https://txandasport.eus/logros/padel_master.png"
}).insertedId;


// ----------------------------------------------------------
// 2. POLIDEPORTIVOS (Independientes)
// ----------------------------------------------------------
print("--- Insertando polideportivos y capturando sus IDs ---");
const poliErandio = db.polideportivos.insertOne({
  nombre: "Polideportivo de Erandio",
  direccion: {
    calle: "Jose Luis Goyoaga",
    numero: "30",
    localidad: "Erandio",
    codigoPostal: "48950",
    provincia: "Bizkaia"
  },
  actividades: [],
  valoraciones: []
}).insertedId;

const poliZorroza = db.polideportivos.insertOne({
  nombre: "Polideportivo de Zorroza",
  direccion: {
    calle: "Zorrozgoiti",
    numero: "5",
    localidad: "Bilbao",
    codigoPostal: "48016",
    provincia: "Bizkaia"
  },
  actividades: [],
  valoraciones: []
}).insertedId;

const poliArtxanda = db.polideportivos.insertOne({
  nombre: "Polideportivo de Artxanda",
  direccion: {
    calle: "Monte Artxanda",
    numero: "s/n",
    localidad: "Bilbao",
    codigoPostal: "48015",
    provincia: "Bizkaia"
  },
  actividades: [],
  valoraciones: []
}).insertedId;


// ----------------------------------------------------------
// 3. USUARIOS (Administrador, Empleados y Clientes)
// ----------------------------------------------------------
print("--- Insertando usuarios y capturando sus IDs ---");

// Administrador
const adminMaster = db.usuarios.insertOne({
  dni: "11111111A",
  rol: "Administrador",
  nombreUsuario: "admin",
  contrasenaUsuario: "123",
  nombre: "Admin",
  apellidos: "General",
  email: "admin@txandasport.com",
  telefono: "600000000",
  direccion: {
    calle: "Gran Vía",
    numero: "1",
    localidad: "Bilbao", // <--- Unificado a localidad
    codigoPostal: "48001",
    provincia: "Bizkaia"
  }
}).insertedId;

// Empleado (Unai)
const empUnai = db.usuarios.insertOne({
  dni: "44444444D",
  rol: "Empleado",
  nombreUsuario: "unai",
  contrasenaUsuario: "123",
  nombre: "Unai",
  apellidos: "Lopez Lopez",
  email: "unai.lopez@email.com",
  telefono: "600000004",
  direccion: {
    calle: "Calle Mayor",
    numero: "10",
    localidad: "Bilbao", // <--- Unificado a localidad
    codigoPostal: "48001",
    provincia: "Bizkaia"
  },
  puesto: "Coordinador Deportivo",
  actividadesAsignadas: []
}).insertedId;

// Cliente (Ugaitz)
const cliUgaitz = db.usuarios.insertOne({
  dni: "55555555E",
  rol: "Cliente",
  nombreUsuario: "ugaitz",
  contrasenaUsuario: "123",
  nombre: "Ugaitz",
  apellidos: "Sanchez Molino",
  email: "ugaitz.sanchez@email.com",
  telefono: "600000005",
  direccion: {
    calle: "Ribera",
    numero: "12",
    localidad: "Erandio", // <--- Unificado a localidad
    codigoPostal: "48950",
    provincia: "Bizkaia"
  },
  puntos: 150,
  logrosObtenidos: [logro1, logro2]
}).insertedId;

// Cliente (Ikerne)
const cliIkerne = db.usuarios.insertOne({
  dni: "66666666F",
  rol: "Cliente",
  nombreUsuario: "ikerne",
  contrasenaUsuario: "123",
  nombre: "Ikerne",
  apellidos: "Sevilla Gal",
  email: "ikerne.sevilla@email.com",
  telefono: "600000006",
  direccion: {
    calle: "Lehendakari Aguirre",
    numero: "22",
    localidad: "Bilbao", // <--- Unificado a localidad
    codigoPostal: "48014",
    provincia: "Bizkaia"
  },
  puntos: 80,
  logrosObtenidos: [logro1]
}).insertedId;

// Cliente (Kepa)
const cliKepa = db.usuarios.insertOne({
  dni: "77777777G",
  rol: "Cliente",
  nombreUsuario: "kepa",
  contrasenaUsuario: "123",
  nombre: "Kepa",
  apellidos: "Luengo Dice",
  email: "kepa.luengo@email.com",
  telefono: "600000007",
  direccion: {
    calle: "Botika Zaharra",
    numero: "4",
    localidad: "Bilbao", // <--- Unificado a localidad
    codigoPostal: "48014",
    provincia: "Bizkaia"
  },
  puntos: 30,
  logrosObtenidos: []
}).insertedId;


// ----------------------------------------------------------
// 4. PISTAS (Dependen de los Polideportivos creados arriba)
// ----------------------------------------------------------
print("--- Insertando pistas vinculadas a polideportivos ---");
const pistaPadel = db.pistas.insertOne({
  nombre: "Pista Pádel 1",
  tipo: "Pádel",
  localizacion: poliErandio,
  precioHora: 12.5,
  estado: "Libre"
}).insertedId;

const pistaTenis = db.pistas.insertOne({
  nombre: "Pista Tenis Exterior",
  tipo: "Tenis",
  localizacion: poliZorroza,
  precioHora: 15.0,
  estado: "Ocupada"
}).insertedId;

const pistaMulti = db.pistas.insertOne({
  nombre: "Pista Polideportiva Cubierta",
  tipo: "Multideporte",
  localizacion: poliArtxanda,
  precioHora: 20.0,
  estado: "Mantenimiento"
}).insertedId;


// ----------------------------------------------------------
// 5. ACTIVIDADES (Dependen de Polideportivos y Empleados)
// ----------------------------------------------------------
print("--- Insertando actividades vinculadas ---");
const act1 = db.actividades.insertOne({
  titulo: "Curso Intensivo de Pádel",
  tipo: "Pádel",
  pistaZona: "Pista Pádel 1",
  localizacion: poliErandio,
  fechaInicio: ISODate('2026-10-15T17:00:00.000+00:00'),
  fechaFin: ISODate('2026-10-15T19:00:00.000+00:00'),
  precio: 15.0,
  aforoMaximo: 8,
  puntos: 50,
  idEmpleado: empUnai,
  participantes: [cliUgaitz, cliIkerne]
}).insertedId;

const act2 = db.actividades.insertOne({
  titulo: "Escuela de Tenis Adultos",
  tipo: "Tenis",
  pistaZona: "Pista Tenis Exterior",
  localizacion: poliZorroza,
  fechaInicio: ISODate('2026-10-16T18:00:00.000+00:00'),
  fechaFin: ISODate('2026-10-16T20:00:00.000+00:00'),
  precio: 18,
  aforoMaximo: 6,
  puntos: 40.0,
  idEmpleado: empUnai,
  participantes: [cliUgaitz]
}).insertedId;

const act3 = db.actividades.insertOne({
  titulo: "Torneo Multideporte",
  tipo: "Multideporte",
  pistaZona: "Pista Polideportiva Cubierta",
  localizacion: poliArtxanda,
  fechaInicio: ISODate('2026-10-20T10:00:00.000+00:00'),
  fechaFin: ISODate('2026-10-20T14:00:00.000+00:00'),
  precio: 10.0,
  aforoMaximo: 20,
  puntos: 30,
  idEmpleado: empUnai,
  participantes: [cliKepa]
}).insertedId;


// ----------------------------------------------------------
// 6. RESERVAS (Dependen de Clientes y Pistas)
// ----------------------------------------------------------
print("--- Insertando reservas vinculadas ---");
db.reservas.insertOne({
  idCliente: cliUgaitz,
  idPista: pistaPadel,
  fechaHoraInicio: ISODate('2026-10-14T16:00:00.000+00:00'),
  fechaHoraFin: ISODate('2026-10-14T17:30:00.000+00:00'),
  importe: 18.75,
  metodoPago: "Bizum",
  estado: "Confirmada",
  fechaReserva: ISODate('2026-10-12T10:15:00.000+00:00')
});

db.reservas.insertOne({
  idCliente: cliIkerne,
  idPista: pistaTenis,
  fechaHoraInicio: ISODate('2026-10-15T19:00:00.000+00:00'),
  fechaHoraFin: ISODate('2026-10-15T20:00:00.000+00:00'),
  importe: 15.0,
  metodoPago: "Tarjeta",
  estado: "Confirmada",
  fechaReserva: ISODate('2026-10-13T09:00:00.000+00:00')
});

db.reservas.insertOne({
  idCliente: cliKepa,
  idPista: pistaMulti,
  fechaHoraInicio: ISODate('2026-10-18T11:00:00.000+00:00'),
  fechaHoraFin: ISODate('2026-10-18T12:00:00.000+00:00'),
  importe: 20.0,
  metodoPago: "Bizum",
  estado: "Pendiente",
  fechaReserva: ISODate('2026-10-13T11:00:00.000+00:00')
});


// ----------------------------------------------------------
// 7. ACTUALIZACIONES CRUZADAS DE ARRAYS
// ----------------------------------------------------------
print("--- Actualizando arrays de relación cruzada ---");
db.polideportivos.updateOne({ _id: poliErandio }, { $push: { actividades: act1 } });
db.polideportivos.updateOne({ _id: poliZorroza }, { $push: { actividades: act2 } });
db.polideportivos.updateOne({ _id: poliArtxanda }, { $push: { actividades: act3 } });

db.usuarios.updateOne({ _id: empUnai }, { $push: { actividadesAsignadas: { $each: [act1, act2, act3] } } });

print("¡PROCESO COMPLETADO! Todos los datos se han insertado y relacionado correctamente.");