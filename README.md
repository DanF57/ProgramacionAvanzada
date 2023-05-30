# ProgramacionAvanzada
Cuarto Ciclo de Computacion

Semana 1
Java Database Connectivity (JDBC)
- API Java para interactuar con BBDD relacionales

Elementos basicos JDBC
- Driver base de datos
- Conexion
- Sentencias --> Consulta(Select) - Actualizacion(insert, delete, update)
- Resultados

```java
Connection con = DriverManager.geConnection(urlDB, "root", "");

Statement stat = con.createStatement();

stat.executeQuery(sql)

stat.executeUpdate(sql)

ResultSet rs = stat.executeQuery(sql)
```
Buscar en maven repository el driver necesario

