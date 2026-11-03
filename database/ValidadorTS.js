
// COLECCIÓN: usuarios

db.createCollection("usuarios", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "dni",
        "rol",
        "nombreUsuario",
        "password",
        "nombre",
        "apellidos",
        "email",
        "direccion"
      ],
      properties: {

        // --- CAMPOS COMUNES PARA TODOS LOS USUARIOS ---

        dni: {
          bsonType: "string",
          pattern: "^[XYZxyz]?[0-9]{7,8}[A-Za-z]$",
          description: "Debe ser un DNI (12345678A) o NIE (X1234567L) válido"
        },
        rol: {
          enum: ["Cliente", "Empleado", "Administrador"],
          description: "El rol solo puede ser: Cliente, Empleado o Administrador"
        },
        nombreUsuario: { bsonType: "string" },
        password: { bsonType: "string" },
        nombre: { bsonType: "string" },
        apellidos: { bsonType: "string" },
        email: {
          bsonType: "string",
          pattern: "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
          description: "Formato de email no válido"
        },
        telefono: {
          bsonType: "string",
          pattern: "^(\\+34|0034)?[6789]\\d{8}$",
          description: "Teléfono válido de 9 dígitos (opcional +34)"
        },
        direccion: {
          bsonType: "object",
          required: ["calle", "numero", "localidad", "codigoPostal", "provincia"],
          properties: {
            calle: { bsonType: "string" },
            numero: { bsonType: "string" },
            localidad: { bsonType: "string" },
            codigoPostal: { bsonType: "string", pattern: "^[0-9]{5}$" },
            provincia: { bsonType: "string" }
          }
        }
      },

      // --- REGLAS SEGÚN EL ROL ---
      oneOf: [
        // REGLA CLIENTES
        {
          properties: {
            rol: { enum: ["Cliente"] },
            puntos: { bsonType: ["int", "double"], minimum: 0 },
            logrosObtenidos: {
              bsonType: "array",
              items: { bsonType: "objectId" }
            }
          },
          // NEGAMOS A CLIENTE TENER DATOS DE EMPLEADO
          not: {
            anyOf: [
              { required: ["puesto"] },
              { required: ["actividadesAsignadas"] }
            ]
          }
        },

        // REGLA EMPLEADOS
        {
          properties: {
            rol: { enum: ["Empleado"] },
            puesto: { bsonType: "string" },
            actividadesAsignadas: {
              bsonType: "array",
              items: { bsonType: "objectId" }
            }
          },
          // NEGAMOS A EMPLEADO QUE TENGA DATOS DE CLIENTE
          not: {
            anyOf: [
              { required: ["puntos"] },
              { required: ["logrosObtenidos"] }
            ]
          }
        },

        // REGLA ADMINISTRADORES
        {
          properties: {
            rol: { enum: ["Administrador"] }
          },
          // NEGAMOS QUE ADMINISTRADOR TENGA DATOS DE CLIENTE Y/O EMPLEADO
          not: {
            anyOf: [
              { required: ["puntos"] },
              { required: ["logrosObtenidos"] },
              { required: ["puesto"] },
              { required: ["actividadesAsignadas"] }
            ]
          }
        }
      ]
    }
  }
});



// COLECCIÓN: pistas

db.createCollection("pistas", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "nombre",
        "tipo",
        "localizacion",
        "precioHora",
        "estado"
      ],
      properties: {
        nombre: {
          bsonType: "string",
          description: "El nombre de la pista es obligatorio (Texto)"
        },
        tipo: {
          bsonType: "string",
          description: "El tipo de deporte/actividad es obligatorio (Pádel, Tenis, Fútbol, Baloncesto...)"
        },
        localizacion: {
          bsonType: "objectId",
          description: "Debe ser un ObjectId válido que referencie a la colección polideportivos"
        },
        precioHora: {
          bsonType: ["double", "int"],
          minimum: 0,
          description: "El precio por hora debe ser un número igual o mayor a 0"
        },
        estado: {
          enum: ["Libre", "Ocupada", "Mantenimiento"],
          description: "El estado solo puede ser: Libre, Ocupada o Mantenimiento"
        }
      }
    }
  }

  
});


// COLECCIÓN: logros

db.createCollection("logros", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "nombre",
        "descripcion",
        "iconoUrl"
      ],
      properties: {
        nombre: {
          bsonType: "string",
          description: "El nombre del logro es obligatorio (Texto)"
        },
        descripcion: {
          bsonType: "string",
          description: "La descripción del logro es obligatoria (Texto)"
        },
        iconoUrl: {
          bsonType: "string",
          description: "La URL o ruta de la imagen/icono es obligatoria (Texto)"
        }
      }
    }
  }
});


// COLECCIÓN: polideportivos

db.createCollection("polideportivos", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "nombre",
        "direccion"
      ],
      properties: {
        nombre: {
          bsonType: "string",
          description: "El nombre del polideportivo es obligatorio (Texto)"
        },
        direccion: {
          bsonType: "object",
          required: ["calle", "numero", "localidad", "provincia"],
          properties: {
            calle: { bsonType: "string" },
            numero: { bsonType: "string" },
            localidad: { bsonType: "string" },
            provincia: { bsonType: "string" }
          }
        },
        actividades: {
          bsonType: "array",
          description: "Array de referencias (ObjectId) a la colección actividades",
          items: {
            bsonType: "objectId"
          }
        },
        valoraciones: {
          bsonType: "array",
          description: "Lista de opiniones y valoraciones de los usuarios",
          items: {
            bsonType: "object",
            required: ["idUsuario", "puntuacion", "fecha"],
            properties: {
              idUsuario: {
                bsonType: "objectId",
                description: "FK que apunta al usuario que realiza la valoración"
              },
              puntuacion: {
                bsonType: ["int", "double"],
                minimum: 1,
                maximum: 5,
                description: "Puntuación entre 1 y 5"
              },
              comentario: {
                bsonType: "string",
                description: "Comentario de la valoración"
              },
              fecha: {
                bsonType: "date",
                description: "Fecha de la valoración"
              }
            }
          }
        }
      }
    }
  }
});


// COLECCIÓN: actividades

db.createCollection("actividades", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "titulo",
        "tipo",
        "pistaZona",
        "localizacion",
        "fechaInicio",
        "fechaFin",
        "precio",
        "aforoMaximo",
        "puntos",
        "idEmpleado"
      ],
      properties: {
        titulo: {
          bsonType: "string",
          description: "El título de la actividad es obligatorio (Texto)"
        },
        tipo: {
          bsonType: "string",
          description: "El tipo de actividad es obligatorio (Texto)"
        },
        pistaZona: {
          bsonType: "string",
          description: "Pista o zona donde se imparte es obligatoria (Texto)"
        },
        localizacion: {
          bsonType: "objectId",
          description: "FK que referencie al polideportivo donde se realiza"
        },
        fechaInicio: {
          bsonType: "date",
          description: "Fecha y hora de inicio de la actividad"
        },
        fechaFin: {
          bsonType: "date",
          description: "Fecha y hora de finalización de la actividad"
        },
        precio: {
          bsonType: ["double", "int"],
          minimum: 0,
          description: "Precio de la inscripción (igual o mayor a 0)"
        },
        aforoMaximo: {
          bsonType: "int",
          minimum: 1,
          description: "Aforo máximo permitido (mínimo 1 participante)"
        },
        puntos: {
          bsonType: ["int", "double"],
          minimum: 0,
          description: "Puntos de recompensa otorgados al asistir (mínimo 0)"
        },
        idEmpleado: {
          bsonType: "objectId",
          description: "FK que referencie al empleado asignado (usuario)"
        },
        participantes: {
          bsonType: "array",
          description: "Array de FKs (ObjectId) a los usuarios clientes inscritos",
          items: {
            bsonType: "objectId"
          }
        }
      }
    }
  }
});


// COLECCIÓN: reservas

db.createCollection("reservas", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "idCliente",
        "idPista",
        "fechaHoraInicio",
        "fechaHoraFin",
        "importe",
        "metodoPago",
        "estado",
        "fechaReserva"
      ],
      properties: {
        idCliente: {
          bsonType: "objectId",
          description: "FK que referencie al cliente (usuario) que realiza la reserva"
        },
        idPista: {
          bsonType: "objectId",
          description: "FK que referencie a la pista reservada"
        },
        fechaHoraInicio: {
          bsonType: "date",
          description: "Fecha y hora de inicio de la reserva"
        },
        fechaHoraFin: {
          bsonType: "date",
          description: "Fecha y hora de fin de la reserva"
        },
        importe: {
          bsonType: ["double", "int"],
          minimum: 0,
          description: "Importe total de la reserva (mínimo 0)"
        },
        metodoPago: {
          enum: ["Tarjeta", "Bizum"],
          description: "El método de pago solo puede ser: Tarjeta o Bizum"
        },
        estado: {
          enum: ["Confirmada", "Cancelada", "Pendiente"],
          description: "El estado solo puede ser: Confirmada, Cancelada o Pendiente"
        },
        fechaReserva: {
          bsonType: "date",
          description: "Fecha y hora en la que se creó la reserva"
        }
      }
    }
  }
});

// COLECCIÓN: logs

db.createCollection("logs", {
 validator: {
  $jsonSchema: {
   bsonType: "object",
   required: [
     "fecha",
     "usuario",
     "operacion"
   ],
   properties: {
     fecha: {
       bsonType: "date"
     },
     usuario: {
       bsonType: "string"
     },
     operacion: {
       bsonType: "string"
     },
     error: {
       bsonType: "string"
     }
   }
  }
 }
});