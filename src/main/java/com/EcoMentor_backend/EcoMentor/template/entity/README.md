## WHAT DOES ENTITY CONTAIN?

## Purpose:

Entity contains the java representations of each table from the DB of the specified feature (in this case, template) as a class.

Each class that is an entity (contains @Entity) is a representation of a DB table, allowing:
## Contents
- Different data with specific annotation:

  - `@Entity`: Marks the class as a JPA entity
  - `@Table`: Specifies the table details (name, schema, etc.)
  - `@Column`: Configures column properties (name, nullable, unique, etc.)
  - `@Id`: Marks the primary key
  - `@GeneratedValue`: Defines the primary key generation strategy


- Defining Relationships: Entities can reference other entities, representing database relationships:

  - `@OneToOne`
  - `@OneToMany`
  - `@ManyToOne`
  - `@ManyToMany`