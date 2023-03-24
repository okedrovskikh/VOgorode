db.createCollection("garden", {
    autoIndexID : true,
    validator: {
        $jsonSchema: {
            bsonType: "object",
            title: "Garden validation schema",
            required: ["landscapeId", "ownerId", "x1", "y1", "x2", "y2", "square", "works"],
            landscapeId: {
                bsonType: "long",
                dsecription: ""
            },
            ownerId: {
                bsonType: "long",
                description: ""
            },
            x1: {
                bsonType: "double",
                description: ""
            },
            y1: {
                bsonType: "double",
                description: ""
            },
            x2: {
                bsonType: "double",
                description: ""
            },
            y2: {
                bsonType: "double",
                description: ""
            },
            square: {
                bsonType: "double",
                description: ""
            },
            works: {
                bsonType: ["string"],
                description: ""
            }
        }
    }
})
